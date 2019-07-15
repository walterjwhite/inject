package com.walterjwhite.modules.web.service.core;

import com.walterjwhite.google.guice.GuiceHelper;
import com.walterjwhite.google.guice.cli.property.CommandLineHandlerShutdownTimeout;
import com.walterjwhite.google.guice.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.google.guice.cli.util.GuiceCommandLineHelper;
import com.walterjwhite.modules.web.service.core.handler.AbstractServletHandler;
import com.walterjwhite.property.impl.annotation.Property;
import javax.inject.Inject;

public class ServerCommandLineHandler extends AbstractCommandLineHandler {
  protected final AbstractServletHandler servletHandler;

  @Inject
  public ServerCommandLineHandler(
      @Property(CommandLineHandlerShutdownTimeout.class) int shutdownTimeoutInSeconds) {
    super(shutdownTimeoutInSeconds);
    servletHandler =
        GuiceHelper.getGuiceApplicationInjector()
            .getInstance(
                GuiceCommandLineHelper.REFLECTIONS_INSTANCE
                    .getSubTypesOf(AbstractServletHandler.class)
                    .iterator()
                    .next());
  }

  @Override
  public void run(String... arguments) throws Exception {
    servletHandler.run();
  }

  @Override
  protected void onShutdown() throws Exception {
    servletHandler.shutdown();

    super.onShutdown();
  }
}
