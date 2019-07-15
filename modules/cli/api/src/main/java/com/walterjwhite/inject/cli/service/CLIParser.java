package com.walterjwhite.inject.cli.service;

import com.walterjwhite.property.api.property.ConfigurableProperty;
import java.util.Map;

public interface CLIParser {
  Map<Class<? extends ConfigurableProperty>, String> parseCommandLineArguments(
      final Iterable<Class<? extends ConfigurableProperty>> configurableProperties,
      final String[] arguments);
}
