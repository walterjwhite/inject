package com.walterjwhite.inject.test;

import com.walterjwhite.inject.test.property.PropertyValuePair;
import com.walterjwhite.inject.test.property.TestPropertyProvider;
import com.walterjwhite.property.api.enumeration.Debug;

public class SampleTestConfiguration implements TestPropertyProvider {
  @Override
  public PropertyValuePair[] getTestProperties() {
    return new PropertyValuePair[] {new PropertyValuePair(Debug.class, Boolean.TRUE)};
  }
}
