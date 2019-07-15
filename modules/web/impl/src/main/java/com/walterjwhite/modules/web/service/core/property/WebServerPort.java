package com.walterjwhite.modules.web.service.core.property;

import com.walterjwhite.property.api.annotation.DefaultValue;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface WebServerPort extends ConfigurableProperty {
  // TODO: support random ports, if we do that, then we also need to ensure proper routing is in
  // place
  @DefaultValue int Default = 8080;
}
