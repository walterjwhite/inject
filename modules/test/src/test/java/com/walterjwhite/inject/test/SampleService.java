package com.walterjwhite.inject.test;

import java.util.Optional;

public class SampleService {
  public Optional<String> sayHello(final Optional<String> input) {
    if (!input.isPresent()) return Optional.empty();

    return Optional.of("Hello " + input.get());
  }
}
