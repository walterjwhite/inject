package com.walterjwhite.google.guice.executor.provider.model;

import com.walterjwhite.datastore.query.AbstractQuery;
import lombok.Getter;

@Getter
public class FindServiceProviderTypeByClassName
    extends AbstractQuery<ServiceProviderType, ServiceProviderType> {
  protected final String className;

  public FindServiceProviderTypeByClassName(String className) {
    super(0, 1, ServiceProviderType.class, ServiceProviderType.class, false);
    this.className = className;
  }
}
