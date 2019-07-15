package com.walterjwhite.modules.web.service.core.handler;

import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.walterjwhite.google.guice.cli.util.GuiceCommandLineHelper;
import com.walterjwhite.modules.web.service.core.DefaultGuiceServletContextListener;
import com.walterjwhite.modules.web.service.core.annotation.ApplicationServletModule;
import com.walterjwhite.modules.web.service.core.property.WebServerPort;
import com.walterjwhite.property.impl.annotation.Property;

public abstract class AbstractServletHandler {

  protected final GuiceServletContextListener guiceServletContextListener;
  //  protected final ServletMappingConfiguration servletMappingConfiguration;
  protected final int port;

  public AbstractServletHandler(@Property(WebServerPort.class) int port // ,
      /*GuiceServletContextListener guiceServletContextListener,*/
      /*ServletMappingConfiguration servletMappingConfiguration*/ ) {
    // todo: configure this with reflections

    // serialization, servlet mappings, etc.
    // this.guiceServletContextListener = guiceServletContextListener;
    try {
      this.guiceServletContextListener =
          new DefaultGuiceServletContextListener(
              (ServletModule)
                  GuiceCommandLineHelper.REFLECTIONS_INSTANCE
                      .getTypesAnnotatedWith(ApplicationServletModule.class)
                      .iterator()
                      .next()
                      .newInstance());
    } catch (InstantiationException | IllegalAccessException e) {
      throw new IllegalStateException("Misconfigured", e);
    }

    // this.servletMappingConfiguration = servletMappingConfiguration;
    this.port = port;
  }

  public abstract void run() throws Exception;

  public abstract void shutdown() throws Exception;
}
