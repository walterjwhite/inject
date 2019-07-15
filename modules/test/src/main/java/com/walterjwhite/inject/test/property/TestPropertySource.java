package com.walterjwhite.inject.test.property;

import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.annotation.PropertySourceIndex;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.source.AbstractSingularPropertySource;
import java.util.Arrays;
import java.util.Optional;

/**
 * If we provide any values for our test, those should be of the highest priority, ie. loaded last
 * to overwrite any existing values
 */
@PropertySourceIndex(Integer.MAX_VALUE)
public class TestPropertySource extends AbstractSingularPropertySource<ConfigurableProperty> {

  protected TestPropertySource(
      PropertyManager propertyManager, Class<ConfigurableProperty> configurablePropertyClass) {
    super(propertyManager, configurablePropertyClass);
  }

  @Override
  protected String get(final Class<? extends ConfigurableProperty> configurablePropertyClass) {
    final Optional<PropertyValuePair> propertyValuePairOptional =
        getPropertyValuePair(configurablePropertyClass);

    if (!propertyValuePairOptional.isPresent()) {
      return null;
    }

    final String existingValue = propertyManager.get(configurablePropertyClass);
    if (!propertyValuePairOptional.get().isOverrideExisting()) {
      return existingValue;
    }

    final String testValue = (String) propertyValuePairOptional.get().getValue();
    propertyManager.set(configurablePropertyClass, testValue);

    return testValue;
  }

  protected Optional<PropertyValuePair> getPropertyValuePair(
      final Class<? extends ConfigurableProperty> configurablePropertyClass) {
    final PropertyValuePair[] propertyValuePairs = null;

    return Arrays.stream(propertyValuePairs)
        .filter(
            propertyValuePair -> configurablePropertyClass.equals(propertyValuePair.propertyClass))
        .findFirst();
  }
}
