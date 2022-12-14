/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
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

package io.undertow.server.handlers;

import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.testutils.DefaultServer;
import io.undertow.testutils.HttpClientUtils;
import io.undertow.testutils.TestHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Deque;
import java.util.Map;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.rewrite;


/**
 * Tests the redirect handler
 *
 * @author Stuart Douglas
 */
@RunWith(DefaultServer.class)
public class SetAttributeTestCase {

    @Test public void testSettingHeader() throws IOException{DefaultServer.setRootHandler(Handlers.setAttribute(ResponseCodeHandler.HANDLE_200,"%{o,Foo}","%U-%{q,p1}",SetAttributeHandler.class.getClassLoader()));TestHttpClient client=new TestHttpClient();try {HttpGet get=new HttpGet(DefaultServer.getDefaultServerURL() + "/path/a");HttpResponse result=client.execute(get);Assert.assertEquals(200,result.getStatusLine().getStatusCode());HttpClientUtils.readResponse(result);Assert.assertEquals("/path/a-",result.getHeaders("foo")[0].getValue());get=new HttpGet(DefaultServer.getDefaultServerURL() + "/path/a?p1=someQp");result=client.execute(get);Assert.assertEquals(200,result.getStatusLine().getStatusCode());HttpClientUtils.readResponse(result);Assert.assertEquals("/path/a-someQp",result.getHeaders("foo")[0].getValue());get=new HttpGet(DefaultServer.getDefaultServerURL() + "/path/a?p1=someQp&p1=value2");result=client.execute(get);Assert.assertEquals(200,result.getStatusLine().getStatusCode());HttpClientUtils.readResponse(result);Assert.assertEquals("/path/a-[someQp, value2]",result.getHeaders("foo")[0].getValue());}  finally {client.getConnectionManager().shutdown();}}


    private class InfoHandler implements HttpHandler {
        @Override
        public void handleRequest(HttpServerExchange exchange) throws Exception {
            final StringBuilder sb = new StringBuilder("URI: " + exchange.getRequestURI()
                    + " relative: " + exchange.getRelativePath()
                    + " QS:" + exchange.getQueryString());
            for (Map.Entry<String, Deque<String>> param : exchange.getQueryParameters().entrySet()) {
                sb.append(" " + param.getKey() + ": " + param.getValue().getFirst());
            }
            exchange.getResponseSender().send(sb.toString());
        }
    }
}
