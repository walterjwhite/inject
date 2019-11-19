package com.walterjwhite.infrastructure.inject.providers.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import javax.enterprise.util.AnnotationLiteral;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GuiceInjector implements com.walterjwhite.infrastructure.inject.core.Injector {
  protected transient volatile GuiceApplicationPropertyRegistrationModule
      guiceApplicationPropertyRegistrationModule;
  protected transient volatile Injector injector;

  public void initialize()
      throws InstantiationException, IllegalAccessException, NoSuchMethodException,
          InvocationTargetException {
    createInjector();
  }

  public void createInjector()
      throws InstantiationException, IllegalAccessException, NoSuchMethodException,
          InvocationTargetException {
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
      throws IllegalAccessException, InstantiationException, NoSuchMethodException,
          InvocationTargetException {
    final Iterator<Class<? extends GuiceApplicationModule>> guiceApplicationModuleIterator =
        ApplicationHelper.getApplicationInstance()
            .getReflections()
            .getSubTypesOf(GuiceApplicationModule.class)
            .iterator();

    if (guiceApplicationModuleIterator.hasNext())
      return guiceApplicationModuleIterator.next().getDeclaredConstructor().newInstance();

    return null;
  }

  @Override
  public <T> T getInstance(Class<T> instanceClass, final AnnotationLiteral... annotationLiterals) {
    return injector.getInstance(instanceClass);
  }
}
