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
package org.assertj.core.util.introspection;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.internal.PropertySupport;
import org.assertj.core.test.Person;
import org.junit.Before;
import org.junit.Test;

public class PropertySupport_publicGetterExistsFor_Test {

  private PropertySupport propertySupport  = PropertySupport.instance();
  private Person bruceWayne;
  private Person joker;
  private SuperHero batman;

  @Before
  public void setUp() {
    bruceWayne = new Person("Bruce Wayne");
    joker = new Person("Joker");
    batman = new SuperHero("Batman", bruceWayne, joker);
  }
  @Test public void should_return_true_if_public_getter_exists_for_field(){assertThat(propertySupport.publicGetterExistsFor("archenemy",batman)).as("check archenemy").isTrue();assertThat(propertySupport.publicGetterExistsFor("name",batman)).as("check name").isTrue();}
  
}
