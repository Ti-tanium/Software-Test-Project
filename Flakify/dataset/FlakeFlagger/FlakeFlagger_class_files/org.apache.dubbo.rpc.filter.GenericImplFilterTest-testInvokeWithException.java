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
package org.apache.dubbo.rpc.filter;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.RpcResult;
import org.apache.dubbo.rpc.service.GenericException;
import org.apache.dubbo.rpc.service.GenericService;
import org.apache.dubbo.rpc.support.DemoService;
import org.apache.dubbo.rpc.support.Person;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class GenericImplFilterTest {

    private GenericImplFilter genericImplFilter = new GenericImplFilter();

    @Test public void testInvokeWithException() throws Exception{RpcInvocation invocation=new RpcInvocation("getPerson",new Class[]{Person.class},new Object[]{new Person("dubbo",10)});URL url=URL.valueOf("test://test:11/com.alibaba.dubbo.rpc.support.DemoService?" + "accesslog=true&group=dubbo&version=1.1&generic=true");Invoker invoker=Mockito.mock(Invoker.class);when(invoker.invoke(any(Invocation.class))).thenReturn(new RpcResult(new GenericException(new RuntimeException("failed"))));when(invoker.getUrl()).thenReturn(url);when(invoker.getInterface()).thenReturn(DemoService.class);Result result=genericImplFilter.invoke(invoker,invocation);Assert.assertEquals(RuntimeException.class,result.getException().getClass());}
}
