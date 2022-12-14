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

import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.ServerConnection;
import io.undertow.testutils.DefaultServer;
import io.undertow.testutils.HttpClientUtils;
import io.undertow.testutils.TestHttpClient;
import io.undertow.util.Headers;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xnio.OptionMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Stuart Douglas
 */
@RunWith(DefaultServer.class)
public class FixedLengthRequestTestCase {

    private static final String MESSAGE = "My HTTP Request!";

    private static volatile String message;

    private static volatile ServerConnection connection;

    @BeforeClass
    public static void setup() {
        final BlockingHandler blockingHandler = new BlockingHandler();
        DefaultServer.setRootHandler(blockingHandler);
        blockingHandler.setRootHandler(new HttpHandler() {
            @Override
            public void handleRequest(final HttpServerExchange exchange) {
                try {
                    if (connection == null) {
                        connection = exchange.getConnection();
                    } else if (!DefaultServer.isAjp()  && !DefaultServer.isProxy() && connection != exchange.getConnection()) {
                        exchange.setResponseCode(500);
                        final OutputStream outputStream = exchange.getOutputStream();
                        outputStream.write("Connection not persistent".getBytes());
                        outputStream.close();
                        return;
                    }
                    final OutputStream outputStream = exchange.getOutputStream();
                    final InputStream inputSream =  exchange.getInputStream();
                    String m = HttpClientUtils.readResponse(inputSream);
                    Assert.assertEquals(message, m);
                    inputSream.close();
                    outputStream.close();
                } catch (IOException e) {
                    exchange.getResponseHeaders().put(Headers.CONNECTION, "close");
                    exchange.setResponseCode(500);
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Test public void testFixedLengthRequest() throws IOException{connection=null;HttpPost post=new HttpPost(DefaultServer.getDefaultServerURL() + "/path");TestHttpClient client=new TestHttpClient();try {generateMessage(1);post.setEntity(new StringEntity(message));HttpResponse result=client.execute(post);Assert.assertEquals(200,result.getStatusLine().getStatusCode());HttpClientUtils.readResponse(result);generateMessage(1000);post.setEntity(new StringEntity(message));result=client.execute(post);Assert.assertEquals(200,result.getStatusLine().getStatusCode());HttpClientUtils.readResponse(result);}  finally {client.getConnectionManager().shutdown();}}

    private static void generateMessage(int repetitions) {
        final StringBuilder builder = new StringBuilder(repetitions * MESSAGE.length());
        for (int i = 0; i < repetitions; ++i) {
            builder.append(MESSAGE);
        }
        message = builder.toString();
    }
}
