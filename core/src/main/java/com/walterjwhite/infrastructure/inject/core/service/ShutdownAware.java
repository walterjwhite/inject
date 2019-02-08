package com.walterjwhite.infrastructure.inject.core.service;

// TODO: use reflections to find all implementations that implement this interface
// TODO: add a shutdown hook, then call all of these sequentially
// TODO: support dependency management, ie, if this service depends on another one, shutdown this
// first, then the dependent one last
public interface ShutdownAware {
  void onShutdown() throws Exception;
}
