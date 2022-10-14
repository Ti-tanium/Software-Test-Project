/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.bind;

import java.util.Iterator;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link RelaxedNames}.
 *
 * @author Phillip Webb
 * @author Dave Syer
 */
public class RelaxedNamesTests {

	@Test public void fromPeriods() throws Exception{Iterator<String> iterator=new RelaxedNames("spring.value").iterator();assertThat(iterator.next(),equalTo("spring.value"));assertThat(iterator.next(),equalTo("spring_value"));assertThat(iterator.next(),equalTo("springValue"));assertThat(iterator.next(),equalTo("springvalue"));assertThat(iterator.next(),equalTo("SPRING.VALUE"));assertThat(iterator.next(),equalTo("SPRING_VALUE"));assertThat(iterator.next(),equalTo("SPRINGVALUE"));assertThat(iterator.hasNext(),equalTo(false));}

}
