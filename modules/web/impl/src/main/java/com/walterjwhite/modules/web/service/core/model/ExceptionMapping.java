package com.walterjwhite.modules.web.service.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

// @NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(doNotUseGetters = true)
public class ExceptionMapping {
  protected Class<? extends Exception> exceptionClass;
  protected int httpCode;
}
