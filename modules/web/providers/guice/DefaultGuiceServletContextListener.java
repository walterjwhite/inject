package com.walterjwhite.modules.web.service.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import java.util.HashMap;
import java.util.Map;

/**
 * NOTE: this injector will be separate from the command-line injector. We could return the same
 * one, but it probably makes more sense to keep them separate.
 */
public class DefaultGuiceServletContextListener extends GuiceServletContextListener {
  // take an output and convert it to bytes for all web services
  // protected final SerializationOutputStream serializationOutputStream;
  protected final ServletModule servletModule;

  public DefaultGuiceServletContextListener(ServletModule servletModule) {
    this.servletModule = servletModule;
  }

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(servletModule);
  }

  protected Map<String, String> getInitParams() {
    Map<String, String> initParams = new HashMap<String, String>();
    // initParams.put("com.sun.jersey.config.feature.Trace", "true");

    return initParams;
  }
}
