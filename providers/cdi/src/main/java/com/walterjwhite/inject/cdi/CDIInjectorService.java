package com.walterjwhite.inject.cdi;

import com.walterjwhite.infrastructure.inject.core.service.AbstractInjectorService;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CDIInjectorService extends AbstractInjectorService {
  // this is specific to running in SE, not required for running in an application server, so just
  // move this to SE/CLI
  protected final SeContainerInitializer seContainerInitializer =
      SeContainerInitializer.newInstance();
  protected volatile SeContainer seContainer;

  @Override
  public void initialize() {
    // enable auto-discovery (requires beans.xml to be present)
    seContainer = seContainerInitializer.initialize();

    // TODO: set this after initialization
    injector = null;
  }
}
