package com.walterjwhite.modules.web.service.core.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface WebServerMaximumThreads extends ConfigurableProperty {
  @DefaultValue int Default = Runtime.getRuntime().availableProcessors() * 2;
}
