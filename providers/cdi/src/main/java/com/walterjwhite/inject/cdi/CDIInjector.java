package com.walterjwhite.inject.cdi;

import com.walterjwhite.infrastructure.inject.core.Injector;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CDIInjector implements Injector {
  protected transient volatile SeContainerInitializer seContainerInitializer;
  protected transient volatile SeContainer seContainer;
  protected transient volatile BeanManager beanManager;

  @Override
  public <T> T getInstance(Class<T> instanceClass, AnnotationLiteral... annotationLiterals) {
    final Bean<T> bean =
        (Bean<T>) beanManager.getBeans(instanceClass, annotationLiterals).iterator().next();
    final CreationalContext<T> creationalContext = beanManager.createCreationalContext(bean);
    return (T) beanManager.getReference(bean, instanceClass, creationalContext);
  }

  @Override
  public void initialize() throws Exception {
    // with a shaded jar, it is important to ensure packages are properly filtered, otherwise, CDI will try to make beans out of everything ...
    seContainerInitializer = SeContainerInitializer.newInstance();
    //    seContainerInitializer.disableDiscovery();
    //    seContainerInitializer.addPackages(getClass().getPackage().)
    seContainer = seContainerInitializer.initialize();

    // set properties from PropertyManager?
    // seContainerInitializer.setProperties()
    beanManager = seContainer.getBeanManager();
  }
}
