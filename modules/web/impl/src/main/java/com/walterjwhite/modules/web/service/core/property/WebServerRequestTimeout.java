package com.walterjwhite.modules.web.service.core.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface WebServerRequestTimeout extends ConfigurableProperty {
  @DefaultValue int Default = 30000; // ms
}
