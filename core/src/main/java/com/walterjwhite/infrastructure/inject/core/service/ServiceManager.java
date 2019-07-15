package com.walterjwhite.infrastructure.inject.core.service;

import com.walterjwhite.infrastructure.inject.core.Injector;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.logging.annotation.LogStackTrace;
import com.walterjwhite.logging.enumeration.LogLevel;
import com.walterjwhite.property.impl.PropertyHelper;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;

/**
 * Initializes services that are "StartupAware". For tasks, services, and applications that are
 * "ShutdownAware", they are handled automatically via a JVM shutdown hook.
 */
@RequiredArgsConstructor
public class ServiceManager {

  protected final Reflections reflections;

  // @TODO: consider re-instating @ServiceStopTimeout.class
  //  @Property(ServiceStopTimeout.class)
  // protected final int serviceStopTimeout = 30;
  protected transient Injector injector;

  public void initialize() {
    injector = ApplicationHelper.getApplicationInstance().getInjector();
    getStartupAwareServices().forEach(service -> doStartService(service));
  }

  protected Set<Class<? extends StartupAware>> getStartupAwareServices() {
    return reflections.getSubTypesOf(StartupAware.class).stream()
        .filter(serviceClass -> PropertyHelper.isConcrete(serviceClass))
        .collect(Collectors.toSet());
  }

  protected void doStartService(final Class<? extends StartupAware> serviceClass) {
    try {
      final StartupAware startupAware = injector.getInstance(serviceClass);

      startupAware.onStartup();
    } catch (Exception e) {
      handleStartupException(e, serviceClass);
    }
  }

  @LogStackTrace(level = LogLevel.WARN)
  protected void handleStartupException(
      Exception e, final Class<? extends StartupAware> serviceClass) {}
}
