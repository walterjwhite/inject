package com.walterjwhite.infrastructure.inject.core;

import lombok.Getter;

@Getter
public class ProviderConstructionException extends RuntimeException {
  protected final Class targetClass;

  public ProviderConstructionException(Class targetClass, Throwable throwable) {
    super("Unable to create an instance of: ", throwable);
    this.targetClass = targetClass;
  }
}
