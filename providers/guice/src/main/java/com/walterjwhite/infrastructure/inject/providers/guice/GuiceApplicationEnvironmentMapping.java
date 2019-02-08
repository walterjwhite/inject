package com.walterjwhite.infrastructure.inject.providers.guice;

import com.google.inject.Stage;
import com.walterjwhite.property.api.property.ApplicationEnvironment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GuiceApplicationEnvironmentMapping {
  Development(Stage.DEVELOPMENT, ApplicationEnvironment.Development),
  Testing(Stage.DEVELOPMENT, ApplicationEnvironment.Testing),
  Production(Stage.PRODUCTION, ApplicationEnvironment.Production);

  private final Stage stage;
  private final ApplicationEnvironment applicationEnvironment;

  public static GuiceApplicationEnvironmentMapping getFromApplicationEnvironment(
      ApplicationEnvironment applicationEnvironment) {
    for (final GuiceApplicationEnvironmentMapping guiceApplicationEnvironmentMapping : values()) {
      if (guiceApplicationEnvironmentMapping
          .getApplicationEnvironment()
          .equals(applicationEnvironment)) return guiceApplicationEnvironmentMapping;
    }

    throw new IllegalArgumentException(applicationEnvironment + " is not currently mapped.");
  }
}
