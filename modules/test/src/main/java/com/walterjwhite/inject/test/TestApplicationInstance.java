package com.walterjwhite.inject.test;

import com.walterjwhite.infrastructure.inject.core.ApplicationInstance;
import com.walterjwhite.infrastructure.inject.core.Injector;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.property.api.PropertyManager;
import lombok.Getter;
import org.reflections.Reflections;

@Getter
public class TestApplicationInstance extends ApplicationInstance {
  protected final Class testClass;

  public TestApplicationInstance(
      Reflections reflections,
      PropertyManager propertyManager,
      ServiceManager serviceManager,
      Injector injector,
      Class testClass) {
    super(reflections, propertyManager, serviceManager, injector);

    // TODO: override the property manager with the test one ...
    this.testClass = testClass;
  }
}
