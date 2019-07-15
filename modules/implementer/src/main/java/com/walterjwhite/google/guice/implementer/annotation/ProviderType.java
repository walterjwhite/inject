package com.walterjwhite.google.guice.implementer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: this is not presently used, perhaps this may be used for dynamic changes based on policy
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface ProviderType {}
