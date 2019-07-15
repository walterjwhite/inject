package com.walterjwhite.modules.web.service.core.model;

import java.util.HashSet;
import java.util.Set;

public class ServletMappingConfiguration {
  protected final Set<ServletMapping> servletMappings = new HashSet<>();

  public ServletMappingConfiguration() {}

  public Set<ServletMapping> getServletMappings() {
    return servletMappings;
  }
}
