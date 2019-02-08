package com.walterjwhite.inject.cli.service;

import com.walterjwhite.interruptable.Interruptable;
import com.walterjwhite.interruptable.annotation.InterruptableApplication;

/*
 * TODO: invoke all shutdown aware hooks (on shutdown)
 */
public abstract class AbstractCommandLineHandler implements Interruptable {
  protected final long shutdownTimeoutInSeconds;

  protected final Thread mainThread = Thread.currentThread();

  public AbstractCommandLineHandler(final int shutdownTimeoutInSeconds) {
    super();
    this.shutdownTimeoutInSeconds = shutdownTimeoutInSeconds;
  }

  @InterruptableApplication
  public void run(final String... arguments) throws Exception {
    doRun(arguments);
  }

  protected abstract void doRun(final String... arguments) throws Exception;

  public void interrupt() {
    try {
      mainThread.join(shutdownTimeoutInSeconds);
    } catch (InterruptedException e) {
      mainThread.interrupt();
    }
  }
}
