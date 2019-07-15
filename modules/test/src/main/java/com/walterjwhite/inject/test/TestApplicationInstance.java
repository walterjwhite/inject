package com.walterjwhite.inject.test;

import com.walterjwhite.infrastructure.inject.core.ApplicationInstance;
import com.walterjwhite.infrastructure.inject.core.Injector;
import com.walterjwhite.infrastructure.inject.core.model.ApplicationSession;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.property.api.PropertyManager;
import java.time.LocalDateTime;
import lombok.Getter;
import org.reflections.Reflections;

@Getter
public class TestApplicationInstance extends ApplicationInstance {
  public TestApplicationInstance(
      Reflections reflections,
      PropertyManager propertyManager,
      ServiceManager serviceManager,
      Injector injector,
      Class testClass) {
    super(
        reflections,
        propertyManager,
        serviceManager,
        injector,
        new ApplicationSession(
            new TestApplicationIdentifier(testClass), null, LocalDateTime.now()));
  }

  // TODO: generalize code from other command line module implementations
  public void doRun() throws Exception {
    initialize();
    doRunInternal();
  }

  protected void doRunInternal() throws Exception {}
}
