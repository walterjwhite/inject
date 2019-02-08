package com.walterjwhite.inject.cdi;

import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.PropertyImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.inject.Singleton;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PropertyBean implements Bean<String> {
  protected final Class<? extends ConfigurableProperty> configurablePropertyClass;
  protected final String name;
  protected final String value;
  protected final Class valueType;
  protected final InjectionTarget<String> injectionTarget;

  @Override
  public Class<?> getBeanClass() {
    return String.class;
  }

  public Set<InjectionPoint> getInjectionPoints() {
    return injectionTarget.getInjectionPoints();
  }

  @Override
  public boolean isNullable() {
    return false;
  }

  @Override
  public String create(CreationalContext<String> creationalContext) {
    return value;
  }

  @Override
  public void destroy(String instance, CreationalContext<String> creationalContext) {}

  @Override
  public Set<Type> getTypes() {
    Set<Type> types = new HashSet<Type>();
    types.add(String.class);
    types.add(valueType);
    return types;
  }

  @Override
  public Set<Annotation> getQualifiers() {
    Set<Annotation> qualifiers = new HashSet<Annotation>();
    qualifiers.add(new PropertyImpl(configurablePropertyClass));
    return qualifiers;
  }

  @Override
  public Class<? extends Annotation> getScope() {
    return Singleton.class;
  }

  @Override
  public String getName() {
    return name;
  }

  public Set<Class<? extends Annotation>> getStereotypes() {
    return Collections.EMPTY_SET;
  }

  @Override
  public boolean isAlternative() {
    return false;
  }
}
