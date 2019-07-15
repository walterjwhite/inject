package com.walterjwhite.inject.test;

import com.walterjwhite.inject.test.annotation.UseTestPropertyProvider;
import com.walterjwhite.inject.test.runner.TestApplicationRunner;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@UseTestPropertyProvider(SampleTestConfiguration.class)
@ExtendWith(TestApplicationRunner.class)
public class SampleTest {
  @Inject protected SampleService sampleService;

  @Test
  public void doTestSomething() {
    final Optional<String> response = sampleService.sayHello(Optional.of("Fred"));
    Assertions.assertNotNull(response);
    Assertions.assertTrue(response.get().contains("Fred"));
  }
}
