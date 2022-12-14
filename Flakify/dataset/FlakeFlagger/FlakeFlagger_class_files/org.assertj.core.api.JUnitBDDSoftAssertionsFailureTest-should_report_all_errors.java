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

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runners.model.MultipleFailureException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

public class JUnitBDDSoftAssertionsFailureTest {

  // we cannot make it a rule here, because we need to test the failure without this test failing!
  // @Rule
  public final JUnitBDDSoftAssertions softly = new JUnitBDDSoftAssertions();

  @Test public void should_report_all_errors() throws Throwable{try {softly.then(1).isEqualTo(1);softly.then(1).isEqualTo(2);softly.then(Lists.newArrayList(1,2)).containsOnly(1,3);MultipleFailureException.assertEmpty(softly.getErrors());fail("Should not reach here");} catch (MultipleFailureException e){List<Throwable> failures=e.getFailures();assertThat(failures).hasSize(2).extracting("message").contains("expected:<[2]> but was:<[1]>","\n" + "Expecting:\n" + "  <[1, 2]>\n" + "to contain only:\n" + "  <[1, 3]>\n" + "elements not found:\n" + "  <[3]>\n" + "and elements not expected:\n" + "  <[2]>\n");}}
}
