package com.walterjwhite.inject.cli;

import com.walterjwhite.infrastructure.inject.core.Injector;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.inject.cli.module.CommandLineApplicationInstance;
import com.walterjwhite.inject.cli.property.OperatingMode;
import com.walterjwhite.inject.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.SecretService;
import com.walterjwhite.property.impl.DefaultPropertyManager;
import com.walterjwhite.property.impl.DefaultPropertyNameLookupService;
import com.walterjwhite.property.impl.PropertyHelper;
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
      doExit(1);
    }
  }

  private static Injector getInjector(Reflections reflections)
      throws IllegalAccessException, InstantiationException {
    return reflections.getSubTypesOf(Injector.class).iterator().next().newInstance();
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

    throw new Error(
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
    final Iterator<Class<? extends AbstractCommandLineHandler>> commandLineHandlerClassIterator =
        reflections.getSubTypesOf(AbstractCommandLineHandler.class).iterator();

    if (commandLineHandlerClassIterator.hasNext()) {
      final Class<? extends AbstractCommandLineHandler> commandLineHandlerClass =
          commandLineHandlerClassIterator.next();
      if (PropertyHelper.isConcrete(commandLineHandlerClass))
        return Optional.of(commandLineHandlerClass);
    }

    return Optional.empty();
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
      throws IllegalAccessException, InstantiationException {
    // use the first available option
    final Iterator<Class<? extends SecretService>> secretServiceIterator =
        reflections.getSubTypesOf(SecretService.class).iterator();

    if (secretServiceIterator.hasNext()) {
      final Class<? extends SecretService> secretServiceClass = secretServiceIterator.next();
      if (PropertyHelper.isConcrete(secretServiceClass)) {
        return secretServiceClass.newInstance();
      }
    }

    throw new Error("Application does not have a secret service configured.");
  }

  /** This *MUST* NOT be called within a shutdown hook, otherwise, the thread will deadlock */
  private static void doExit(final int exitCode) {
    System.exit(exitCode);
  }
}
