package com.walterjwhite.inject.cli;

import com.walterjwhite.inject.cli.service.CLIParser;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.annotation.PropertySourceIndex;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.source.AbstractSingularStringPropertySource;
import java.util.*;

@PropertySourceIndex(10)
public class CLIPropertySource extends AbstractSingularStringPropertySource<ConfigurableProperty> {
  public CLIPropertySource(PropertyManager propertyManager) {
    super(propertyManager, ConfigurableProperty.class);

    final CLIParser cliParser = getCLIParser();
    setCommandLineProperties(
        cliParser.parseCommandLineArguments(
            propertyManager.getKeys(),
            CLIApplicationHelper.getCommandLineApplicationInstance().getArguments()));
  }

  protected CLIParser getCLIParser() {
    try {
      return propertyManager
          .getReflections()
          .getSubTypesOf(CLIParser.class)
          .iterator()
          .next()
          .newInstance();
    } catch (IllegalAccessException | InstantiationException e) {
      throw new CLIConfigurationException(
          "Application is mis-configured - CLIParser should have a public, no-arg constructor");
    }
  }

  protected void setCommandLineProperties(
      final Map<Class<? extends ConfigurableProperty>, String> configurablePropertyMap) {
    for (final Class<? extends ConfigurableProperty> configurableProperty :
        configurablePropertyMap.keySet())
      propertyManager.set(configurableProperty, configurablePropertyMap.get(configurableProperty));
  }

  @Override
  protected String doGet(final String propertyKey) {
    return null;
  }
}
