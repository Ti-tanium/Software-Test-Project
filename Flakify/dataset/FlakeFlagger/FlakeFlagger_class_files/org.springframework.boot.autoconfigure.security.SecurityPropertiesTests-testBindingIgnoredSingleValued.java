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

package org.springframework.boot.autoconfigure.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link SecurityProperties}.
 *
 * @author Dave Syer
 */
public class SecurityPropertiesTests {

	private SecurityProperties security = new SecurityProperties();

	private RelaxedDataBinder binder = new RelaxedDataBinder(this.security, "security");

	@Before
	public void init() {
		this.binder.setIgnoreUnknownFields(false);
		this.binder.setConversionService(new DefaultConversionService());
	}

	@Test public void testBindingIgnoredSingleValued(){this.binder.bind(new MutablePropertyValues(Collections.singletonMap("security.ignored","/css/**")));assertFalse(this.binder.getBindingResult().hasErrors());assertEquals(1,this.security.getIgnored().size());}

}
