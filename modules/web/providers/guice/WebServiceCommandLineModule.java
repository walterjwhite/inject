package com.walterjwhite.modules.web.service.core;

import com.walterjwhite.google.guice.cli.AbstractCommandLineModule;
import com.walterjwhite.modules.web.service.core.enumeration.WebServiceOperatingMode;
import com.walterjwhite.property.api.PropertyManager;
import org.reflections.Reflections;

public class WebServiceCommandLineModule extends AbstractCommandLineModule {
  // protected final Set<Class<? extends ServletModule>> servletModuleClases;

  public WebServiceCommandLineModule(PropertyManager propertyManager, Reflections reflections) {
    super(propertyManager, reflections, WebServiceOperatingMode.class);

    // servletModuleClases = reflections.getSubTypesOf(ServletModule.class);
  }

  @Override
  protected void doCliConfigure() {
    /*
    for(final Class<? extends ServletModule> servletModuleClass:servletModuleClases) {
      try {
        install(servletModuleClass.getDeclaredConstructor().newInstance());
      } catch (InstantiationException|IllegalAccessException e) {
      throw(new RuntimeException("Configuration error", e));
      }
    }
    */
  }
}
