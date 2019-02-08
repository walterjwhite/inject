package com.walterjwhite.inject.cli.service;

import com.walterjwhite.infrastructure.inject.core.service.ShutdownAware;
import com.walterjwhite.infrastructure.inject.core.service.StartupAware;

// TODO: how should this be implemented?
public abstract class AbstractDaemonCommandLineHandler extends AbstractCommandLineHandler
    implements StartupAware, ShutdownAware {
  public AbstractDaemonCommandLineHandler(final int shutdownTimeoutInSeconds) {
    super(shutdownTimeoutInSeconds);
  }

  @Override
  public void onStartup() throws Exception {}

  @Override
  public void onShutdown() throws Exception {}

  @Override
  protected void doRun(String... arguments) throws Exception {
    Thread.currentThread().join();
  }
}
