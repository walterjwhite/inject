package com.walterjwhite.infrastructure.inject.core;

import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.property.api.property.ApplicationEnvironment;
import lombok.Getter;

@Getter
public class ApplicationIdentifier {
  protected final ApplicationEnvironment applicationTargetEnvironment =
      ApplicationHelper.getApplicationTargetEnvironment();
  protected final ApplicationEnvironment applicationEnvironment =
      ApplicationHelper.getApplicationEnvironment();

  public final String applicationName = ApplicationHelper.getApplicationName();
  public final String applicationVersion = ApplicationHelper.getImplementationVersion();
  public final String scmVersion = ApplicationHelper.getSCMVersion();

  public String toString() {
    return String.format(
        "%s-%s-%s (%s/%s)",
        applicationName,
        applicationVersion,
        scmVersion,
        applicationTargetEnvironment,
        applicationEnvironment);
  }
}
