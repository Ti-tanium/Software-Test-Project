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

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for <code>{@link Condition#Condition()}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class Condition_default_constructor_Test {

  @Test public void should_use_class_simple_name_as_description(){Condition<Object> condition=new Condition<Object>(){@Override public boolean matches(Object value){return false;}};assertEquals(condition.getClass().getSimpleName(),condition.description.value());}
}
