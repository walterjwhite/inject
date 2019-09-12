package com.walterjwhite.inject.cdi;

import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.PropertyHelper;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;

// @see: https://stackoverflow.com/questions/4087766/how-to-inject-string-constants-easily-with-weld
// TODO: bind Properties to Properties - does this provide value?
// TODO: bind the actual property type, otherwise, CDI will not work at injection time
@ApplicationScoped
public class PropertyTypeExtension implements Extension {
  protected final PropertyManager propertyManager =
      ApplicationHelper.getApplicationInstance().getPropertyManager();

  public void afterBeanDiscovery(
      @Observes AfterBeanDiscovery afterBeanDiscovery, BeanManager beanManager) {
    // load is handled in the application helper / application instance
    // propertyManager.load();
    bindToProperties(afterBeanDiscovery, beanManager);
  }

  protected void bindToProperties(AfterBeanDiscovery afterBeanDiscovery, BeanManager beanManager) {
    propertyManager.getKeys().forEach(p -> bindToProperty(afterBeanDiscovery, beanManager, p));
  }

  protected void bindToProperty(
      AfterBeanDiscovery afterBeanDiscovery,
      BeanManager beanManager,
      final Class<? extends ConfigurableProperty> configurablePropertyClass) {
    final String name = propertyManager.lookup(configurablePropertyClass);
    final String value = propertyManager.get(configurablePropertyClass);
    final Class propertyValueType = propertyManager.type(configurablePropertyClass);

    PropertyHelper.validatePropertyConfiguration(
        configurablePropertyClass, propertyValueType, value);

    AnnotatedType<String> annotatedType = beanManager.createAnnotatedType(propertyValueType);

    Bean<String> si =
        new PropertyBean(
            configurablePropertyClass,
            name,
            value,
            propertyValueType,
            beanManager.createInjectionTarget(annotatedType));
    afterBeanDiscovery.addBean(si);
  }
}
