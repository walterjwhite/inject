package com.walterjwhite.infrastructure.inject.core;

import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.infrastructure.inject.core.model.ApplicationIdentifier;
import com.walterjwhite.infrastructure.inject.core.model.ApplicationSession;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.property.api.PropertyManager;
import java.time.LocalDateTime;
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

  protected final ApplicationSession applicationSession;

  public ApplicationInstance(
      Reflections reflections,
      PropertyManager propertyManager,
      ServiceManager serviceManager,
      Injector injector) {
    this(
        reflections,
        propertyManager,
        serviceManager,
        injector,
        new ApplicationSession(
            new ApplicationIdentifier(
                ApplicationHelper.getApplicationTargetEnvironment(),
                ApplicationHelper.getApplicationEnvironment(),
                ApplicationHelper.getApplicationName(),
                ApplicationHelper.getImplementationVersion(),
                ApplicationHelper.getSCMVersion()),
            ApplicationHelper.getNodeId(),
            LocalDateTime.now()));
  }

  public void initialize() throws Exception {
    logApplicationIdentifier();

    propertyManager.initialize();
    injector.initialize();
    serviceManager.initialize();
  }

  protected String logApplicationIdentifier() {
    return applicationSession.toString();
  }
}
