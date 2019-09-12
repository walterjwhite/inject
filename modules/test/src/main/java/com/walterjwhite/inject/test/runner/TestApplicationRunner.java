package com.walterjwhite.inject.test.runner;

import com.walterjwhite.infrastructure.inject.core.Injector;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import com.walterjwhite.infrastructure.inject.core.service.ServiceManager;
import com.walterjwhite.inject.test.TestApplicationInstance;
import com.walterjwhite.inject.test.annotation.UseTestPropertyProvider;
import com.walterjwhite.inject.test.property.PropertyValuePair;
import com.walterjwhite.property.impl.DefaultPropertyManager;
import com.walterjwhite.property.impl.DefaultPropertyNameLookupService;
import com.walterjwhite.property.impl.DefaultSecretService;
import java.lang.reflect.Method;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.extension.*;
import org.reflections.Reflections;

// TODO:
// 1. automatically handles setting up test application stuff (test application instance, test
// application properties (test property source))
// 2. ensure application properly starts
// 3. provides benchmarking facility (outside of scope)
// @See CLIApplicationHelper
@Getter
@RequiredArgsConstructor
public class TestApplicationRunner
    implements Extension,
        BeforeAllCallback,
        AfterAllCallback,
        TestInstancePostProcessor,
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {
  protected final Reflections reflections = Reflections.collect();
  protected final Class testClass;
  protected final TestApplicationInstance testApplicationInstance;
  protected final PropertyValuePair[] propertyValuePairs;

  public void run() throws Exception {

    new TestApplicationInstance(
        reflections,
        new DefaultPropertyManager(
            new DefaultPropertyNameLookupService(),
            reflections,
            new DefaultSecretService() /*TODO: allow this to be overridden*/),
        new ServiceManager(reflections),
        getInjector(),
        testClass);

    ApplicationHelper.setApplicationInstance(testApplicationInstance);

    testApplicationInstance.doRun();
  }

  protected PropertyValuePair[] getTestPropertyProvider()
      throws InstantiationException, IllegalAccessException {
    return ((UseTestPropertyProvider) testClass.getAnnotation(UseTestPropertyProvider.class))
        .value()
        .newInstance()
        .getTestProperties();
  }

  protected Injector getInjector() throws IllegalAccessException, InstantiationException {
    return reflections.getSubTypesOf(Injector.class).iterator().next().newInstance();
  }

  @Override
  public void afterAll(ExtensionContext context) throws Exception {}

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    Object testInstance = context.getRequiredTestInstance();
    Method testMethod = context.getRequiredTestMethod();
    Throwable testException = context.getExecutionException().orElse(null);

    // getTestContextManager(context).afterTestMethod(testInstance, testMethod, testException);
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {}

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    Object testInstance = context.getRequiredTestInstance();
    Method testMethod = context.getRequiredTestMethod();

    // getTestContextManager(context).beforeTestMethod(testInstance, testMethod);
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return false;
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return null;
  }

  @Override
  public void postProcessTestInstance(Object testInstance, ExtensionContext context)
      throws Exception {}
}
