package com.walterjwhite.infrastructure.inject.providers.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import java.util.NoSuchElementException;
import javax.enterprise.util.AnnotationLiteral;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GuiceInjector implements com.walterjwhite.infrastructure.inject.core.Injector {
  protected transient volatile GuiceApplicationPropertyRegistrationModule
      guiceApplicationPropertyRegistrationModule;
  protected transient volatile Injector injector;

  public void initialize() throws InstantiationException, IllegalAccessException {
    createInjector();
  }

  public void createInjector() throws InstantiationException, IllegalAccessException {
    guiceApplicationPropertyRegistrationModule =
        new GuiceApplicationPropertyRegistrationModule(getGuiceApplicationModule());
    injector = Guice.createInjector(getStage(), guiceApplicationPropertyRegistrationModule);
  }

  protected Stage getStage() {
    return GuiceApplicationEnvironmentMapping.getFromApplicationEnvironment(
            ApplicationHelper.getApplicationInstance()
                .getApplicationSession()
                .getApplicationIdentifier()
                .getApplicationEnvironment())
        .getStage();
  }

  protected GuiceApplicationModule getGuiceApplicationModule()
      throws IllegalAccessException, InstantiationException {
    try {
      return getGuiceApplicationModuleClass().newInstance();
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  protected Class<? extends GuiceApplicationModule> getGuiceApplicationModuleClass() {
    return ApplicationHelper.getApplicationInstance()
        .getReflections()
        .getSubTypesOf(GuiceApplicationModule.class)
        .iterator()
        .next();
  }

  @Override
  public <T> T getInstance(Class<T> instanceClass, final AnnotationLiteral... annotationLiterals) {
    return injector.getInstance(instanceClass);
  }
}
