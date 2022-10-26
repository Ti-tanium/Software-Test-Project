/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.context.embedded;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ServletRegistrationBean}.
 *
 * @author Phillip Webb
 */
public class ServletRegistrationBeanTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private final MockServlet servlet = new MockServlet();

	@Mock
	private ServletContext servletContext;

	@Mock
	private ServletRegistration.Dynamic registration;

	@Mock
	private FilterRegistration.Dynamic filterRegistration;

	@Before
	public void setupMocks() {
		MockitoAnnotations.initMocks(this);
		given(this.servletContext.addServlet(anyString(), (Servlet) anyObject()))
				.willReturn(this.registration);
		given(this.servletContext.addFilter(anyString(), (Filter) anyObject()))
				.willReturn(this.filterRegistration);
	}

	@Test public void createMappingMustNotBeNull() throws Exception{this.thrown.expect(IllegalArgumentException.class);this.thrown.expectMessage("UrlMappings must not be null");new ServletRegistrationBean(this.servlet,(String[])null);}

}