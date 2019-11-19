package com.walterjwhite.infrastructure.inject.providers.guice;

import com.google.inject.AbstractModule;
import com.walterjwhite.infrastructure.inject.core.ApplicationInstance;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.PropertyHelper;
import com.walterjwhite.property.impl.PropertyImpl;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;

/** This registers all properties as Guice "beans" */
@RequiredArgsConstructor
public class GuiceApplicationPropertyRegistrationModule extends AbstractModule {
  protected final GuiceApplicationModule guiceApplicationModule;
  protected transient PropertyManager propertyManager;

  protected void configure() {
    propertyManager = ApplicationHelper.getApplicationInstance().getPropertyManager();

    bindCommon();
    bindProperties();
  }

  protected void bindCommon() {
    if (guiceApplicationModule != null) install(guiceApplicationModule);

    bind(Reflections.class).toInstance(ApplicationHelper.getApplicationInstance().getReflections());
    bind(ApplicationInstance.class).toInstance(ApplicationHelper.getApplicationInstance());
    bind(PropertyManager.class)
        .toInstance(ApplicationHelper.getApplicationInstance().getPropertyManager());
    bind(ServiceManager.class)
        .toInstance(ApplicationHelper.getApplicationInstance().getServiceManager());
  }

  protected void bindProperties() {
    propertyManager.getKeys().forEach(configurableProperty -> bindToProperty(configurableProperty));
  }

  public void bindToProperty(Class<? extends ConfigurableProperty> configurablePropertyClass) {
    // TODO: support optional values here
    //    if(configurablePropertyClass.isAnnotationPresent(Optional.class)){
    //      final Optional<String> optionalValue = propertyManager.get(configurablePropertyClass);
    //      bindConstant().annotatedWith(new PropertyImpl(configurablePropertyClass)).to();
    //      return;
    //    }

    final String value = propertyManager.get(configurablePropertyClass);

    if (PropertyHelper.isOptional(configurablePropertyClass)) {
      // OptionalBinder.newOptionalBinder(binder(),
      // configurablePropertyClass).setDefault().toInstance(value));
      // OptionalBinder.newOptionalBinder(binder().)
      bindConstant()
          .annotatedWith(new PropertyImpl(configurablePropertyClass))
          .to(Optional.ofNullable(value));
    } else {

      if (value != null)
        bindConstant().annotatedWith(new PropertyImpl(configurablePropertyClass)).to(value);
    }
  }
}
