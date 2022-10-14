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
package org.apache.dubbo.common.utils;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UrlUtilsTest {

    String localAddress = "127.0.0.1";

    @Test public void testRevertRegister(){String key="perf/dubbo.test.api.HelloService:1.0.0";Map<String, Map<String, String>> register=new HashMap<String, Map<String, String>>();Map<String, String> service=new HashMap<String, String>();service.put("dubbo://127.0.0.1:20880/com.xxx.XxxService",null);register.put(key,service);Map<String, Map<String, String>> newRegister=UrlUtils.revertRegister(register);Map<String, Map<String, String>> expectedRegister=new HashMap<String, Map<String, String>>();service.put("dubbo://127.0.0.1:20880/com.xxx.XxxService","group=perf&version=1.0.0");expectedRegister.put("dubbo.test.api.HelloService",service);assertEquals(expectedRegister,newRegister);}
}