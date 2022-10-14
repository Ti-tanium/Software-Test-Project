/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import ninja.NinjaTest;

import org.apache.http.HttpResponse;
import org.apache.http.cookie.Cookie;
import org.junit.Test;

import com.google.common.collect.Maps;

public class ApplicationControllerTest extends NinjaTest {

    @Test public void testPostFormParsingWorks(){Map<String, String> headers=Maps.newHashMap();Map<String, String> formParameters=Maps.newHashMap();formParameters.put("description","test3");formParameters.put("email","test2@email.com");formParameters.put("name","test1");String response=ninjaTestBrowser.makePostRequestWithFormParameters(getServerAddress() + "/contactForm",headers,formParameters);assertTrue(response.contains("test3"));assertTrue(response.contains("test2@email.com"));assertTrue(response.contains("test1"));}
}
