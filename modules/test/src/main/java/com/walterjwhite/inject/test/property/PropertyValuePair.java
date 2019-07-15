package com.walterjwhite.inject.test.property;

import com.walterjwhite.property.api.property.ConfigurableProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class PropertyValuePair {
  protected final Class<? extends ConfigurableProperty> propertyClass;
  @EqualsAndHashCode.Exclude protected final Object value;
  @EqualsAndHashCode.Exclude protected final boolean overrideExisting;

  public PropertyValuePair(Class<? extends ConfigurableProperty> propertyClass, Object value) {
    this(propertyClass, value, false);
  }

  public PropertyValuePair(Class<? extends ConfigurableProperty> propertyClass) {
    this(propertyClass, null);
  }
}
