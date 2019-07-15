package com.walterjwhite.modules.web.service.core.model;

import java.util.Objects;
import javax.servlet.Servlet;

public class ServletMapping {
  protected final String urlPattern;
  protected final Class<? extends Servlet> servletClass;

  public ServletMapping(String urlPattern, Class<? extends Servlet> servletClass) {
    this.urlPattern = urlPattern;
    this.servletClass = servletClass;
  }

  public String getUrlPattern() {
    return urlPattern;
  }

  public Class<? extends Servlet> getServletClass() {
    return servletClass;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ServletMapping that = (ServletMapping) o;
    return Objects.equals(urlPattern, that.urlPattern)
        && Objects.equals(servletClass, that.servletClass);
  }

  @Override
  public int hashCode() {

    return Objects.hash(urlPattern, servletClass);
  }
}
