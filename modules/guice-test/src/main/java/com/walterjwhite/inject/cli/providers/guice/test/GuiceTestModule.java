package com.walterjwhite.inject.cli.providers.guice.test;

import com.google.inject.AbstractModule;
import com.walterjwhite.inject.test.PropertyValuePair;
import com.walterjwhite.inject.test.property.TestClass;
import com.walterjwhite.property.api.enumeration.Debug;
import java.util.Arrays;
import org.reflections.Reflections;

/** Use this inside unit tests to ensure guice components are properly configured. */
// TODO: integrate this into the new design pattern
// this should be a provider of the inject test module ...
public class GuiceTestModule extends AbstractModule {
  protected final Class testClass;
  protected final PropertyValuePair[] propertyValuePairs;

  public GuiceTestModule(final Class testClass, PropertyValuePair... propertyValuePairs) {
    this(testClass, Reflections.collect(), propertyValuePairs);
  }

  public GuiceTestModule(
      final Class testClass, Reflections reflections, PropertyValuePair... propertyValuePairs) {
    //    super(
    //        new TestPropertyManager(new DefaultPropertyNameLookupService(), reflections,
    // testClass),
    //        reflections);

    this.testClass = testClass;
    this.propertyValuePairs = getPropertyValuePairs(testClass, propertyValuePairs);
    loadSpecifiedProperties();
  }

  public static PropertyValuePair[] getPropertyValuePairs(
      final Class testClass, final PropertyValuePair[] propertyValuePairs) {
    PropertyValuePair[] result = Arrays.copyOf(propertyValuePairs, propertyValuePairs.length + 2);
    result[propertyValuePairs.length] = new PropertyValuePair(TestClass.class, testClass, true);
    result[propertyValuePairs.length + 1] = new PropertyValuePair(Debug.class, true, false);

    return result;
  }

  protected void loadSpecifiedProperties() {
    if (propertyValuePairs != null && propertyValuePairs.length > 0) {
      for (PropertyValuePair propertyValuePair : propertyValuePairs) {
        loadSpecifiedProperty(propertyValuePair);
      }
    }
  }

  protected void loadSpecifiedProperty(PropertyValuePair propertyValuePair) {
    if (isSet(propertyValuePair)) {
      //      propertyManager.set(
      //          propertyValuePair.getPropertyClass(), propertyValuePair.getValue().toString());
      // TODO: get this working again
    }
  }

  protected boolean isSet(PropertyValuePair propertyValuePair) {
    // TODO: get this working again
    return propertyValuePair.isOverrideExisting()
    /*|| propertyManager.get(propertyValuePair.getPropertyClass()) == null*/ ;
  }
}
