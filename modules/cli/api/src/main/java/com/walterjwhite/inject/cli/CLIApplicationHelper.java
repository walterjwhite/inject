package com.walterjwhite.inject.cli;

import com.walterjwhite.infrastructure.inject.core.Injector;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.inject.cli.module.CommandLineApplicationInstance;
import com.walterjwhite.inject.cli.property.OperatingMode;
import com.walterjwhite.property.impl.DefaultPropertyManager;
import com.walterjwhite.property.impl.DefaultPropertyNameLookupService;
import java.util.Iterator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.reflections.Reflections;

/** @see "pom.xml" - this is configured to launch the application whether it is CDI, Guice, etc. */
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
    COMMAND_LINE_APPLICATION_INSTANCE =
        new CommandLineApplicationInstance(
            reflections,
            new DefaultPropertyManager(new DefaultPropertyNameLookupService(), reflections),
            new ServiceManager(reflections),
            getInjector(reflections),
            getOperatingModeClass(reflections),
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
  private static Class<? extends OperatingMode> getOperatingModeClass(Reflections reflections) {
    final Iterator<Class<? extends OperatingMode>> operatingModeIterator =
        reflections.getSubTypesOf(OperatingMode.class).iterator();

    if (operatingModeIterator.hasNext()) return operatingModeIterator.next();

    throw new Error(
        "Application is improperly configured, there must be one and only one OperatingMode class in the classpath");
  }

  //  public static void setHandlerArguments(final String[] handlerArguments) {
  //    HANDLER_ARGUMENTS = handlerArguments;
  //  }
  //
  //  public static String[] getHandlerArguments() {
  //    return HANDLER_ARGUMENTS;
  //  }

  /** This *MUST* NOT be called within a shutdown hook, otherwise, the thread will deadlock */
  private static void doExit(final int exitCode) {
    System.exit(exitCode);
  }
}
