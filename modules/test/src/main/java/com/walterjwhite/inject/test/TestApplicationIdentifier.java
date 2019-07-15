package com.walterjwhite.inject.test;

import com.walterjwhite.infrastructure.inject.core.model.ApplicationIdentifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TestApplicationIdentifier extends ApplicationIdentifier {
  protected final Class testClass;

  public String toString() {
    return super.toString() + " - " + testClass.getName();
  }
}
