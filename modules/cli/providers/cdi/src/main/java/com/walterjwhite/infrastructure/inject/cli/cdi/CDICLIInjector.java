package com.walterjwhite.infrastructure.inject.cli.cdi;

import com.walterjwhite.inject.cdi.CDIInjector;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CDICLIInjector extends CDIInjector {
  protected transient volatile SeContainerInitializer seContainerInitializer;
  protected transient volatile SeContainer seContainer;

  @Override
  public void initialize() {
    // with a shaded jar, it is important to ensure packages are properly filtered, otherwise, CDI
    // will try to make beans out of everything ...
    seContainerInitializer = SeContainerInitializer.newInstance();
    //    seContainerInitializer.disableDiscovery();
    //    seContainerInitializer.addPackages(getClass().getPackage().)
    seContainer = seContainerInitializer.initialize();

    // set properties from PropertyManager?
    // seContainerInitializer.setProperties()

    // commented this out to re-use existing CDI code
    // beanManager = seContainer.getBeanManager();
    super.initialize();
  }
}
