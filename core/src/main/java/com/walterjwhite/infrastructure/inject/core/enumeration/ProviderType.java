package com.walterjwhite.infrastructure.inject.core.enumeration;

import com.walterjwhite.infrastructure.inject.core.ProviderConstructionException;
import com.walterjwhite.infrastructure.inject.core.helper.ApplicationHelper;
import java.lang.reflect.InvocationTargetException;

/** Gets an instance of a given target class, either by reflection, or injection */
public enum ProviderType {
  Self {
    protected Class[] getConstructorArguments(final Object... arguments) {
      final Class[] argumentTypes = new Class[arguments.length];
      for (int i = 0; i < arguments.length; i++) {
        argumentTypes[i] = arguments[i].getClass();
      }

      return argumentTypes;
    }

    public <Type> Type get(final Class<Type> targetClass, final Object... arguments) {
      // return argumentTypes; get(Class<? extends Type> targetClass, final Object... arguments){
      final Class[] constructorArguments = getConstructorArguments(arguments);

      try {
        return targetClass.getConstructor(constructorArguments).newInstance(arguments);

      } catch (IllegalArgumentException
          | InstantiationException
          | IllegalAccessException
          | InvocationTargetException
          | NoSuchMethodException e) {
        throw new ProviderConstructionException(targetClass, e); //
        // + ArrayUtils.toString(arguments) + " / " + ArrayUtils.toString(constructorArguments));
      }
    }
  },
  Injector {
    @Override
    public <Type> Type get(Class<Type> targetClass, Object... arguments) {
      // TODO: use the arguments to further refine the instance we want
      return ApplicationHelper.getApplicationInstance().getInjector().getInstance(targetClass);
    }
  };

  public abstract <Type> Type get(final Class<Type> targetClass, final Object... arguments);
}
