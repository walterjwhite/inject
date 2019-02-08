package com.walterjwhite.inject.test;

import com.walterjwhite.property.api.property.ConfigurableProperty;

public class PropertyValuePair {
  protected final Class<? extends ConfigurableProperty> propertyClass;
  protected final Object value;
  protected final boolean overrideExisting;

  public PropertyValuePair(
      Class<? extends ConfigurableProperty> propertyClass,
      Object value,
      final boolean overrideExisting) {
    super();
    this.propertyClass = propertyClass;
    this.value = value;
    this.overrideExisting = overrideExisting;
  }

  public PropertyValuePair(Class<? extends ConfigurableProperty> propertyClass, Object value) {
    this(propertyClass, value, false);
  }

  public PropertyValuePair(Class<? extends ConfigurableProperty> propertyClass) {
    this(propertyClass, null);
  }

  public Class<? extends ConfigurableProperty> getPropertyClass() {
    return propertyClass;
  }

  public Object getValue() {
    return value;
  }

  public boolean isOverrideExisting() {
    return overrideExisting;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PropertyValuePair that = (PropertyValuePair) o;

    return propertyClass != null
        ? propertyClass.equals(that.propertyClass)
        : that.propertyClass == null;
  }

  @Override
  public int hashCode() {
    return propertyClass != null ? propertyClass.hashCode() : 0;
  }
}
