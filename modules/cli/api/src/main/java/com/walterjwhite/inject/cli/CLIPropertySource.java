package com.walterjwhite.inject.cli;

import com.walterjwhite.inject.cli.util.CLIUtil;
import com.walterjwhite.property.api.PropertyManager;
import com.walterjwhite.property.api.annotation.PropertySourceIndex;
import com.walterjwhite.property.api.property.ConfigurableProperty;
import com.walterjwhite.property.impl.source.AbstractSingularStringPropertySource;
import java.util.*;

@PropertySourceIndex(10)
public class CLIPropertySource extends AbstractSingularStringPropertySource<ConfigurableProperty> {
  protected final String[] arguments;

  public CLIPropertySource(PropertyManager propertyManager) {
    super(propertyManager, ConfigurableProperty.class);

    this.arguments = CLIApplicationHelper.getCommandLineApplicationInstance().getArguments();

    // TODO: refactor this
    final Map<Class<? extends ConfigurableProperty>, String> configurablePropertyMap =
        CLIUtil.getCommandLineProperties(propertyManager.getKeys(), arguments);
    setCommandLineProperties(configurablePropertyMap);

    // TODO: this may be useful, but maybe we should re-think it
    // CLIApplicationHelper.setHandlerArguments(getUnusedArguments(configurablePropertyMap));
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

  // TODO: consider re-adding for passing to handlers, no arguments were used yet
  protected String[] getUnusedArguments(
      final Map<Class<? extends ConfigurableProperty>, String> configurablePropertyMap) {
    final List<String> allArguments = new ArrayList<>();
    final Set<Integer> indexesToAvoid = new HashSet<>();

    for (final Class<? extends ConfigurableProperty> configurableProperty :
        configurablePropertyMap.keySet()) {
      final String propertyName = "-" + configurableProperty.getSimpleName();

      final int argumentIndex = contains(propertyName);
      if (argumentIndex >= 0) {
        indexesToAvoid.add(argumentIndex);
        indexesToAvoid.add(argumentIndex + 1);
      }
    }

    for (int i = 0; i < arguments.length; i++) {
      if (indexesToAvoid.contains(Integer.valueOf(i))) {
        continue;
      }

      allArguments.add(arguments[i]);
    }

    return allArguments.toArray(new String[allArguments.size()]);
  }

  protected int contains(final String key) {
    for (int i = 0; i < arguments.length; i++) if (key.equals(arguments[i])) return i;

    return -1;
  }
}
