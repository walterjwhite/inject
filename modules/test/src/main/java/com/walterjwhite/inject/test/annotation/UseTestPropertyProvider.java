package com.walterjwhite.inject.test.annotation;

import com.walterjwhite.inject.test.property.TestPropertyProvider;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface UseTestPropertyProvider {
  Class<? extends TestPropertyProvider> value();
}
