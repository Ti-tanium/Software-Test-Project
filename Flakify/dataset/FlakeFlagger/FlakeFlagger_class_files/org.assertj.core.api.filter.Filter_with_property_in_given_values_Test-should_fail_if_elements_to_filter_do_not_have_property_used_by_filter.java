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

import static junit.framework.Assert.assertEquals;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.filter.Filters.filter;

import static org.junit.Assert.fail;

import org.assertj.core.test.Player;
import org.assertj.core.util.introspection.IntrospectionError;

import org.junit.Test;


public class Filter_with_property_in_given_values_Test extends AbstractTest_filter {

  @Test
  public void should_fail_if_elements_to_filter_do_not_have_property_used_by_filter() {
    try {
      filter(players).with("nickname").in("dude", "al");
      fail("IntrospectionError expected");
    } catch (IntrospectionError e) {
      assertEquals("No getter for property 'nickname' in org.assertj.core.test.Player", e.getMessage());
    }
  }

}