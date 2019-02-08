package com.walterjwhite.infrastructure.inject.core;

import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.property.api.PropertyManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;

@Getter
@RequiredArgsConstructor
public class ApplicationInstance {
  protected final Reflections reflections;
  protected final PropertyManager propertyManager;
  protected final ServiceManager serviceManager;
  protected final Injector injector;

  protected final ApplicationIdentifier applicationIdentifier = new ApplicationIdentifier();

  public void initialize() throws Exception {
    logApplicationIdentifier();

    propertyManager.initialize();
    injector.initialize();
    serviceManager.initialize();
  }

  protected String logApplicationIdentifier() {
    return applicationIdentifier.toString();
  }
}
