/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http.protocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.junit.Test;

public class TestHttpRequestHandlerRegistry {

    private static class DummyHttpRequestHandler implements HttpRequestHandler {

        public void handle(
                final HttpRequest request,
                final HttpResponse response,
                final HttpContext context) throws HttpException, IOException {
        }

    }

    @Test public void testWildCardMatchingWithQuery() throws Exception{HttpRequestHandler h1=new DummyHttpRequestHandler();HttpRequestHandler h2=new DummyHttpRequestHandler();HttpRequestHandler def=new DummyHttpRequestHandler();HttpRequestHandlerRegistry registry=new HttpRequestHandlerRegistry();registry.register("*",def);registry.register("*.view",h1);registry.register("*.form",h2);HttpRequestHandler h;h=registry.lookup("/that.view?param=value");Assert.assertNotNull(h);Assert.assertTrue(h1 == h);h=registry.lookup("/that.form?whatever");Assert.assertNotNull(h);Assert.assertTrue(h2 == h);h=registry.lookup("/whatever");Assert.assertNotNull(h);Assert.assertTrue(def == h);}

}
