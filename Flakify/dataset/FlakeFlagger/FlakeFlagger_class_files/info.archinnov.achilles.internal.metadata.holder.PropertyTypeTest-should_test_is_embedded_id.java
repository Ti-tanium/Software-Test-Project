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
package info.archinnov.achilles.internal.metadata.holder;

import static org.fest.assertions.api.Assertions.assertThat;
import info.archinnov.achilles.internal.metadata.holder.PropertyType;

import org.junit.Test;

public class PropertyTypeTest {


	@Test public void should_test_is_embedded_id() throws Exception{assertThat(PropertyType.COUNTER.isEmbeddedId()).isFalse();assertThat(PropertyType.ID.isEmbeddedId()).isFalse();assertThat(PropertyType.SIMPLE.isEmbeddedId()).isFalse();assertThat(PropertyType.LIST.isEmbeddedId()).isFalse();assertThat(PropertyType.MAP.isEmbeddedId()).isFalse();assertThat(PropertyType.EMBEDDED_ID.isEmbeddedId()).isTrue();}
}
