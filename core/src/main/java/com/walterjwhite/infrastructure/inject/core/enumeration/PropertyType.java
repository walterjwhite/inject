package com.walterjwhite.infrastructure.inject.core.enumeration;

// this is in-place for CDI since it seems to need the specific type for a value whereas Guice automatically adapts the value from a String to a primitive type
// unused currently
@Deprecated
public enum PropertyType {
  String {
    public Object getValue(final String value) {
      return value;
    }

    public boolean supports(final Class propertyClass) {
      return String.class.equals(propertyClass);
    }
  },
  Integer {
    public Object getValue(final String value) {
      return java.lang.Integer.valueOf(value);
    }

    public boolean supports(final Class propertyClass) {
      return Integer.class.equals(propertyClass) || int.class.equals(propertyClass);
    }
  },
  Double {
    public Object getValue(final String value) {
      return java.lang.Double.valueOf(value);
    }

    public boolean supports(final Class propertyClass) {
      return Double.class.equals(propertyClass) || double.class.equals(propertyClass);
    }
  },
  Float {
    public Object getValue(final String value) {
      return java.lang.Float.valueOf(value);
    }

    public boolean supports(final Class propertyClass) {
      return Float.class.equals(propertyClass) || float.class.equals(propertyClass);
    }
  },
  Boolean {
    public Object getValue(final String value) {
      return java.lang.Boolean.valueOf(value);
    }

    public boolean supports(final Class propertyClass) {
      return Boolean.class.equals(propertyClass) || boolean.class.equals(propertyClass);
    }
  };

  public abstract Object getValue(final String value);

  public abstract boolean supports(final Class propertyClass);

  public static Object getValue(final String value, final Class propertyClass) {
    for (final PropertyType propertyType : values()) {
      if (propertyType.supports(propertyClass)) {
        return propertyType.getValue(value);
      }
    }

    throw new IllegalArgumentException("Unsupported:" + propertyClass);
  }
}
