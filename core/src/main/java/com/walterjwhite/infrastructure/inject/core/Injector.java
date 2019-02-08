package com.walterjwhite.infrastructure.inject.core;

import javax.enterprise.util.AnnotationLiteral;

public interface Injector {
  <T> T getInstance(Class<T> instanceClass, AnnotationLiteral... annotationLiterals);

  void initialize() throws Exception;
}
