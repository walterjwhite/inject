package com.walterjwhite.google.guice.implementer;

import com.walterjwhite.infrastructure.inject.core.Injector;
import javax.inject.Inject;
import org.reflections.Reflections;

public abstract class AbstractSelector<Reference, ProviderType, ServiceType> {
  protected final Reflections reflections;
  protected final Class<? extends ServiceType> serviceTypeClass;
  protected final Injector injector = null;

  @Inject
  public AbstractSelector(Reflections reflections, Class<? extends ServiceType> serviceTypeClass) {
    this.reflections = reflections;
    this.serviceTypeClass = serviceTypeClass;
  }

  protected abstract ProviderType get(Reference reference);

  public ServiceType getInstance(Reference reference) {
    for (final Class<? extends ServiceType> serviceType :
        reflections.getSubTypesOf(serviceTypeClass)) {
      if (match(serviceType, reference)) {
        return injector.getInstance(serviceType);
      }
    }

    throw new IllegalArgumentException(get(reference) + " is not currently supported.");
  }

  protected abstract boolean match(
      final Class<? extends ServiceType> serviceType, Reference reference);
}
