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

package io.undertow.server.ssl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.CanonicalPathHandler;
import io.undertow.server.handlers.NameVirtualHostHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.error.SimpleErrorPageHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.server.handlers.resource.ResourceHandler;
import io.undertow.server.handlers.file.FileHandlerTestCase;
import io.undertow.server.protocol.http.HttpServerConnection;
import io.undertow.testutils.AjpIgnore;
import io.undertow.testutils.DefaultServer;
import io.undertow.testutils.HttpClientUtils;
import io.undertow.testutils.TestHttpClient;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stuart Douglas
 */
@AjpIgnore
@RunWith(DefaultServer.class)
public class ComplexSSLTestCase {

    private static final String MESSAGE = "My HTTP Request!";

    private static volatile String message;

    @Test public void testSslLotsOfData() throws IOException,GeneralSecurityException,URISyntaxException{DefaultServer.setRootHandler(new HttpHandler(){@Override public void handleRequest(HttpServerExchange exchange) throws Exception{if (exchange.isInIoThread()){exchange.dispatch(this);return;}exchange.startBlocking();ByteArrayOutputStream out=new ByteArrayOutputStream();HttpServerConnection connection=(HttpServerConnection)exchange.getConnection();byte[] buf=new byte[100];int res=0;while ((res=exchange.getInputStream().read(buf)) > 0){out.write(buf,0,res);}exchange.getOutputStream().write(out.toByteArray());}});DefaultServer.startSSLServer();TestHttpClient client=new TestHttpClient();client.setSSLContext(DefaultServer.getClientSSLContext());try {generateMessage(1000000);HttpPost post=new HttpPost(DefaultServer.getDefaultServerSSLAddress());post.setEntity(new StringEntity(message));HttpResponse resultList=client.execute(post);Assert.assertEquals(200,resultList.getStatusLine().getStatusCode());String response=HttpClientUtils.readResponse(resultList);Assert.assertEquals(message,response);generateMessage(100000);post=new HttpPost(DefaultServer.getDefaultServerSSLAddress());post.setEntity(new StringEntity(message));resultList=client.execute(post);Assert.assertEquals(200,resultList.getStatusLine().getStatusCode());response=HttpClientUtils.readResponse(resultList);Assert.assertEquals(message,response);}  finally {client.getConnectionManager().shutdown();DefaultServer.stopSSLServer();}}

    private static void generateMessage(int repetitions) {
        final StringBuilder builder = new StringBuilder(repetitions * MESSAGE.length());
        for (int i = 0; i < repetitions; ++i) {
            builder.append(MESSAGE);
        }
        message = builder.toString();
    }
}
