package com.walterjwhite.infrastructure.inject.core.helper;

import com.jcabi.manifests.Manifests;
import com.walterjwhite.infrastructure.inject.core.ApplicationInstance;
import com.walterjwhite.property.api.enumeration.ApplicationSCMVersion;
import com.walterjwhite.property.api.property.ApplicationEnvironment;
import com.walterjwhite.property.api.property.ApplicationManifestProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// TODO: rather than have to write separate getters for each attribute of interest in the manifest,
// we can use reflection to get them
// TODO: if we do that, it would be nice to tie that to the application identifier ...
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationHelper {

  public static ApplicationInstance APPLICATION_INSTANCE;

  public static void setApplicationInstance(ApplicationInstance applicationInstance) {
    APPLICATION_INSTANCE = applicationInstance;
  }

  public static ApplicationInstance getApplicationInstance() {
    return APPLICATION_INSTANCE;
  }

  public static String getApplicationName() {
    return ApplicationHelper.class.getPackage().getImplementationTitle();
  }

  public static String getImplementationVersion() {
    return ApplicationInstance.class.getPackage().getImplementationVersion();
  }

  public static String getSCMVersion() {
    return getManifestProperty(ApplicationSCMVersion.class);
  }

  // the environment the manifest indicates
  public static ApplicationEnvironment getApplicationTargetEnvironment() {
    return ApplicationEnvironment.valueOf(getManifestProperty(ApplicationEnvironment.class));
  }

  // TODO: the environment the JVM is reporting (env variable)
  // TODO: restrict this to just being picked up from the environment?
  public static ApplicationEnvironment getApplicationEnvironment() {
    return getApplicationTargetEnvironment();
  }

  public static String getManifestProperty(
      final Class<? extends ApplicationManifestProperty> applicationManifestProperty) {
    return Manifests.read(applicationManifestProperty.getSimpleName());
  }
}
