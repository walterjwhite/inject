package com.walterjwhite.inject.cli.property;

import com.walterjwhite.inject.cli.service.AbstractCommandLineHandler;
import com.walterjwhite.property.api.property.ConfigurableProperty;

public interface OperatingMode extends ConfigurableProperty {
  Class<? extends AbstractCommandLineHandler> getInitiatorClass();
}
