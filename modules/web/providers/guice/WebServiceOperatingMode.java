package com.walterjwhite.modules.web.service.core.enumeration;

import com.walterjwhite.modules.web.service.core.ServerCommandLineHandler;
import com.walterjwhite.property.api.annotation.DefaultValue;

public enum WebServiceOperatingMode implements OperatingMode {
  @DefaultValue
  Default(ServerCommandLineHandler.class);

  private final Class<? extends AbstractCommandLineHandler> initiatorClass;

  WebServiceOperatingMode(Class<? extends AbstractCommandLineHandler> initiatorClass) {
    this.initiatorClass = initiatorClass;
  }

  @Override
  public Class<? extends AbstractCommandLineHandler> getInitiatorClass() {
    return initiatorClass;
  }
}
