/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.core.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Fail.shouldHaveThrown;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.Test;

public class Assertions_assertThat_with_Throwable_Test {

  @Test public void should_fail_if_no_throwable_was_thrown(){try {assertThatThrownBy(notRaisingException()).hasMessage("yo");} catch (AssertionError e){assertThat(e).hasMessage("Expecting code to raise a throwable.");return;}shouldHaveThrown(AssertionError.class);}

  private ThrowingCallable notRaisingException() {
    return new ThrowingCallable() {
      @Override
      public void call() throws Throwable {
      }
    };
  }

  private ThrowingCallable raisingException(final String reason) {
    return new ThrowingCallable() {
      @Override
      public void call() throws Throwable {
        throw new Exception(reason);
      }
    };
  }
}
