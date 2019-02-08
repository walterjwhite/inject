package com.walterjwhite.inject.test;

import com.walterjwhite.property.api.PropertyNameLookupService;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.DefaultPropertyManager;
import org.reflections.Reflections;

// todo - re-implement this with the split of property source and property target
public class TestPropertyManager extends DefaultPropertyManager {
  protected boolean isUseTestClass;
  protected final Class testClass;

  public TestPropertyManager(
      PropertyNameLookupService propertyNameLookupService,
      Reflections reflections,
      final Class testClass) {
    super(propertyNameLookupService, reflections);
    this.testClass = testClass;
  }

  public void load() {
    // if (isLoaded()) return;

    //    doLoadDefaultPath();
    //    doLoadTestPath();
  }

  //  protected void doLoadDefaultPath() {
  //    super.load();
  //  }
  //
  //  protected void doLoadTestPath() {
  //    // load from test property path
  //    loaded = false;
  //    isUseTestClass = true;
  //    super.load();
  //
  //    // once everything is loaded, ensure we use the default lookup path since that is where the
  //    // value will be stored under
  //    isUseTestClass = false;
  //  }

  public String lookup(final Class<? extends ConfigurableProperty> configurableProperty) {
    if (isUseTestClass)
      return testClass.getName() + "." + propertyNameLookupService.lookup(configurableProperty);

    return propertyNameLookupService.lookup(configurableProperty);
  }
}
