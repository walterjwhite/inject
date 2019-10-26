package com.walterjwhite.google.guice.executor.provider.model.provider;

import com.walterjwhite.datastore.api.repository.Repository;
import com.walterjwhite.google.guice.executor.provider.model.ProviderConfigurationError;
import com.walterjwhite.google.guice.executor.provider.model.ServiceProviderType;
import com.walterjwhite.infrastructure.inject.core.Injector;
import javax.inject.Provider;

// import com.walterjwhite.google.guice.executor.provider.model.ServiceProviderTypeEntityRepository;
public abstract class AbstractProvider<ProviderType> implements Provider<ProviderType> {
  protected final Class<? extends ProviderType> serviceInterfaceClass;
  protected final Repository repository;
  protected final ServiceProviderType serviceProviderType;
  protected final Injector injector = null; // Application.getInjector();

  public AbstractProvider(
      Class<? extends ProviderType> serviceInterfaceClass, Repository repository) {
    super();
    this.serviceInterfaceClass = serviceInterfaceClass;
    this.repository = repository;

    this.serviceProviderType = null;
  }

  @Override
  public ProviderType get() {
    try {
      return (ProviderType)
          injector.getInstance(Class.forName(serviceProviderType.getServiceInterfaceClassname()));
    } catch (ClassNotFoundException e) {
      throw new ProviderConfigurationError("Error getting instance", e);
    }
  }
}
