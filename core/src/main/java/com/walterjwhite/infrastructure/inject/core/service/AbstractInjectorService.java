package com.walterjwhite.infrastructure.inject.core.service;

import com.walterjwhite.infrastructure.inject.core.Injector;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class AbstractInjectorService {
  protected Injector injector;

  public void initialize() {}
}
