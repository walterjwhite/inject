package com.walterjwhite.inject.cli;

import com.walterjwhite.infrastructure.inject.core.Injector;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.inject.cli.module.CommandLineApplicationInstance;
import com.walterjwhite.inject.cli.property.OperatingMode;
import com.walterjwhite.inject.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.logging.annotation.LogStackTrace;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.SecretService;
import com.walterjwhite.property.impl.DefaultPropertyManager;
import com.walterjwhite.property.impl.DefaultPropertyNameLookupService;
import com.walterjwhite.property.impl.PropertyHelper;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.reflections.Reflections;

/** This is configured to launch the application whether it is CDI, Guice, etc. - via pom.xml */
// TODO: add exception mapping -> exit code
// TODO: consider re-adding support for handler arguments (still unused anywhere)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CLIApplicationHelper {
  public static CommandLineApplicationInstance COMMAND_LINE_APPLICATION_INSTANCE;

  public static CommandLineApplicationInstance getCommandLineApplicationInstance() {
    return COMMAND_LINE_APPLICATION_INSTANCE;
  }

  public static void main(final String[] arguments) throws Exception {
    final Reflections reflections = Reflections.collect();

    final PropertyManager propertyManager =
        new DefaultPropertyManager(
            new DefaultPropertyNameLookupService(), reflections, getSecretService(reflections));

    COMMAND_LINE_APPLICATION_INSTANCE =
        new CommandLineApplicationInstance(
            reflections,
            propertyManager,
            new ServiceManager(reflections),
            getInjector(reflections),
            arguments);

    ApplicationHelper.setApplicationInstance(COMMAND_LINE_APPLICATION_INSTANCE);

    // initialize guice / CDI

    try {
      COMMAND_LINE_APPLICATION_INSTANCE.doRun();
      doExit(0);
    } catch (Throwable t) {
      onException(t);
      doExit(1);
    }
  }

  @LogStackTrace
  private static void onException(final Throwable t) {}

  private static Injector getInjector(Reflections reflections)
      throws IllegalAccessException, InstantiationException, NoSuchMethodException,
          InvocationTargetException {
    return reflections
        .getSubTypesOf(Injector.class)
        .iterator()
        .next()
        .getDeclaredConstructor()
        .newInstance();
  }

  // there should only ever be one
  public static Class<? extends AbstractCommandLineHandler> getCommandLineHandlerClass(
      final Reflections reflections, final PropertyManager propertyManager) {
    final Optional<Class<? extends AbstractCommandLineHandler>> operatingModeInitiatorClass =
        getOperatingModeInitiator(reflections, propertyManager);
    if (operatingModeInitiatorClass.isPresent()) return operatingModeInitiatorClass.get();

    final Optional<Class<? extends AbstractCommandLineHandler>> commandLineHandlerClass =
        getCommandLineHandlerClass(reflections);
    if (commandLineHandlerClass.isPresent()) return commandLineHandlerClass.get();

    throw new CLIConfigurationException(
        "Application is improperly configured, there must be one and only one OperatingMode class in the classpath");
  }

  private static Optional<Class<? extends AbstractCommandLineHandler>> getOperatingModeInitiator(
      final Reflections reflections, final PropertyManager propertyManager) {
    final Iterator<Class<? extends OperatingMode>> operatingModeIterator =
        reflections.getSubTypesOf(OperatingMode.class).iterator();

    if (operatingModeIterator.hasNext()) {
      final Class<? extends OperatingMode> operatingModeClass = operatingModeIterator.next();
      final OperatingMode operatingMode =
          getOperatingMode(operatingModeClass, propertyManager.get(operatingModeClass));
      return Optional.of(operatingMode.getInitiatorClass());
    }

    return Optional.empty();
  }

  // Selects first non-null command-line handler class
  private static Optional<Class<? extends AbstractCommandLineHandler>> getCommandLineHandlerClass(
      final Reflections reflections) {
    // use the first available option
    return reflections.getSubTypesOf(AbstractCommandLineHandler.class).stream()
        .filter(commandLineHandlerClass -> PropertyHelper.isConcrete(commandLineHandlerClass))
        .findFirst();
  }

  private static OperatingMode getOperatingMode(
      final Class<? extends OperatingMode> operatingModeClass, final String value) {
    return (OperatingMode) Enum.valueOf((Class<? extends Enum>) operatingModeClass, value);
  }

  //  public static void setHandlerArguments(final String[] handlerArguments) {
  //    HANDLER_ARGUMENTS = handlerArguments;
  //  }
  //
  //  public static String[] getHandlerArguments() {
  //    return HANDLER_ARGUMENTS;
  //  }

  private static SecretService getSecretService(final Reflections reflections)
      throws IllegalAccessException, InstantiationException, NoSuchMethodException,
          InvocationTargetException {
    // use the first available option
    final Iterator<Class<? extends SecretService>> secretServiceIterator =
        reflections.getSubTypesOf(SecretService.class).iterator();

    if (secretServiceIterator.hasNext()) {
      final Class<? extends SecretService> secretServiceClass = secretServiceIterator.next();
      if (PropertyHelper.isConcrete(secretServiceClass)) {
        return secretServiceClass.getDeclaredConstructor().newInstance();
      }
    }

    throw new CLIConfigurationException("Application does not have a secret service configured.");
  }

  /** This *MUST* NOT be called within a shutdown hook, otherwise, the thread will deadlock */
  private static void doExit(final int exitCode) {
    System.exit(exitCode);
  }
}
