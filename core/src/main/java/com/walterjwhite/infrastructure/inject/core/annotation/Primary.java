package com.walterjwhite.infrastructure.inject.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

// this annotation is under review
// this is meant to allow the use of a primary and secondary Persistence Unit
@Deprecated
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface Primary {
  String IDENTIFIER = "Primary";
}
