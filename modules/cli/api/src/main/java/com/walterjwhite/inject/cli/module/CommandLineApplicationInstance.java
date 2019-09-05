package com.walterjwhite.inject.cli.module;

import com.walterjwhite.infrastructure.inject.core.ApplicationInstance;
import com.walterjwhite.infrastructure.inject.core.Injector;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.inject.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.property.api.PropertyManager;
import lombok.Getter;
import org.reflections.Reflections;

// TODO: re-add support for handler arguments (after the arguments are processed from the
// command-line, the remaining, unprocessed arguments are to be passed to the handler)
// removed because it isn't used currently and may impact dependencies
@Getter
public class CommandLineApplicationInstance extends ApplicationInstance {
  //  protected final Class<? extends OperatingMode> operatingModeClass;
  protected final Class<? extends AbstractCommandLineHandler> commandLineHandlerClass;
  protected final String[] arguments;
  // protected final String[] handlerArguments;

  public CommandLineApplicationInstance(
      Reflections reflections,
      PropertyManager propertyManager,
      ServiceManager serviceManager,
      Injector injector,
      Class<? extends AbstractCommandLineHandler> commandLineHandlerClass,
      String[] arguments) {
    super(reflections, propertyManager, serviceManager, injector);
    this.commandLineHandlerClass = commandLineHandlerClass;
    this.arguments = arguments;
  }

  // TODO: generalize code from other command line module implementations
  public void doRun() throws Exception {
    initialize();
    doRunInternal();
  }

  protected void doRunInternal() throws Exception {
    final AbstractCommandLineHandler abstractCommandLineHandler =
        getInjector().getInstance(getCommandLineHandlerClass());

    abstractCommandLineHandler.run(/*CLIApplicationHelper.getHandlerArguments()*/ arguments);
  }
}
