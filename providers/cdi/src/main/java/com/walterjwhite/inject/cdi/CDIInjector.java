package com.walterjwhite.inject.cdi;

import com.walterjwhite.infrastructure.inject.core.Injector;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CDIInjector implements Injector {
  protected transient volatile BeanManager beanManager;

  @Override
  public <T> T getInstance(Class<T> instanceClass, AnnotationLiteral... annotationLiterals) {
    final Bean<T> bean =
        (Bean<T>) beanManager.getBeans(instanceClass, annotationLiterals).iterator().next();
    final CreationalContext<T> creationalContext = beanManager.createCreationalContext(bean);
    return (T) beanManager.getReference(bean, instanceClass, creationalContext);
  }

  @Override
  public void initialize() {
    beanManager = CDI.current().getBeanManager();
  }
}
