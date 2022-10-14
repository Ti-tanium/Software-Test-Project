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
package org.assertj.core.api.filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.filter.Filters.filter;

import static org.junit.Assert.fail;

import org.assertj.core.test.Player;
import org.junit.Test;


public class Filter_being_condition_Test extends AbstractTest_filter {

  private PotentialMvpCondition potentialMvp = new PotentialMvpCondition();

  @Test public void should_fail_if_filter_condition_is_null(){try {filter(players).being(null);fail("NullPointerException expected");} catch (NullPointerException e){assertThat(e).hasMessage("The filter condition should not be null");}}

}