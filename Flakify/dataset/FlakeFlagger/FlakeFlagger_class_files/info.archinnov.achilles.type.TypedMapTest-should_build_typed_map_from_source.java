/*
 * Copyright (C) 2012-2014 DuyHai DOAN
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package info.archinnov.achilles.type;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TypedMapTest {

	@Test public void should_build_typed_map_from_source() throws Exception{Map<String, Object> source=new HashMap<>();source.put("string","value");source.put("int",10);TypedMap typedMap=TypedMap.fromMap(source);assertThat(typedMap.<String>getTyped("string")).isInstanceOf(String.class);assertThat(typedMap.<Integer>getTyped("int")).isInstanceOf(Integer.class);}
}
