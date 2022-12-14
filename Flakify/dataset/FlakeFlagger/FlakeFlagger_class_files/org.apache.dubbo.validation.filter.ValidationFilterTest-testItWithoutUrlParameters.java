/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.validation.filter;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.validation.Validation;
import org.apache.dubbo.validation.Validator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class ValidationFilterTest {
    private Invoker<?> invoker = mock(Invoker.class);
    private Validation validation = mock(Validation.class);
    private Validator validator = mock(Validator.class);
    private RpcInvocation invocation = mock(RpcInvocation.class);

    private ValidationFilter validationFilter;

    @Before
    public void setUp() throws Exception {
        this.validationFilter = new ValidationFilter();
    }

    @Test public void testItWithoutUrlParameters() throws Exception{URL url=URL.valueOf("test://test:11/test");given(validation.getValidator(url)).willReturn(validator);given(invoker.invoke(invocation)).willReturn(new RpcResult("success"));given(invoker.getUrl()).willReturn(url);given(invocation.getMethodName()).willReturn("echo1");given(invocation.getParameterTypes()).willReturn(new Class<?>[]{String.class});given(invocation.getArguments()).willReturn(new Object[]{"arg1"});validationFilter.setValidation(validation);Result result=validationFilter.invoke(invoker,invocation);assertThat(String.valueOf(result.getValue()),is("success"));}
}