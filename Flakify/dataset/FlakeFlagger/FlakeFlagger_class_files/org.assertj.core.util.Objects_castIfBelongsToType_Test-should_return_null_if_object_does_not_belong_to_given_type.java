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
package org.assertj.core.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for {@link Objects#castIfBelongsToType(Object, Class)}.
 * 
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author Joel Costigliola
 */
public class Objects_castIfBelongsToType_Test {

  @Test public void should_return_null_if_object_does_not_belong_to_given_type(){Object o=4;String casted=Objects.castIfBelongsToType(o,String.class);assertNull(casted);}
}
