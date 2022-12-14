/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013 Red Hat, Inc., and individual contributors
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

import io.undertow.conduits.ChunkedStreamSourceConduit;
import io.undertow.server.HttpHandler;
import io.undertow.server.protocol.http.HttpServerConnection;
import io.undertow.server.HttpServerExchange;
import io.undertow.testutils.AjpIgnore;
import io.undertow.testutils.DefaultServer;
import io.undertow.testutils.HttpClientUtils;
import io.undertow.util.HeaderMap;
import io.undertow.util.HeaderValues;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Stuart Douglas
 */
@RunWith(DefaultServer.class)
@AjpIgnore
public class ChunkedRequestTrailersTestCase {

    private static volatile HttpServerConnection connection;

    @BeforeClass
    public static void setup() {
        final BlockingHandler blockingHandler = new BlockingHandler();
        DefaultServer.setRootHandler(blockingHandler);
        blockingHandler.setRootHandler(new HttpHandler() {
            @Override
            public void handleRequest(final HttpServerExchange exchange) {
                try {
                    if (connection == null) {
                        connection = (HttpServerConnection) exchange.getConnection();
                    } else if (!DefaultServer.isProxy() && connection != exchange.getConnection()) {
                        exchange.setResponseCode(500);
                        final OutputStream outputStream = exchange.getOutputStream();
                        outputStream.write("Connection not persistent".getBytes());
                        outputStream.close();
                        return;
                    }
                    final OutputStream outputStream = exchange.getOutputStream();
                    final InputStream inputSream = exchange.getInputStream();
                    String m = HttpClientUtils.readResponse(inputSream);
                    Assert.assertEquals("abcdefghi", m);

                    HeaderMap headers = exchange.getAttachment(ChunkedStreamSourceConduit.TRAILERS);
                    for (HeaderValues header : headers) {
                        for (String val : header) {
                            outputStream.write(header.getHeaderName().toString().getBytes());
                            outputStream.write(": ".getBytes());
                            outputStream.write(val.getBytes());
                            outputStream.write("\r\n".getBytes());
                        }
                    }

                    inputSream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
	 * We send our request manually, as apache HTTP client does not support this.
	 * @throws IOException
	 */@Test public void testChunkedRequestsWithTrailers() throws IOException{connection=null;String request="POST / HTTP/1.1\r\nTrailer:foo, bar\r\nTransfer-Encoding: chunked\r\n\r\n9\r\nabcdefghi\r\n0\r\nfoo: fooVal\r\n bar: barVal\r\n\r\n";String response1="HTTP/1.1 200 OK\r\nConnection: keep-alive\r\nContent-Length: 26\r\n\r\nfoo: fooVal\r\nbar: barVal\r\n";String response2="HTTP/1.1 200 OK\r\nConnection: keep-alive\r\nContent-Length: 26\r\n\r\nfoo: fooVal\r\nbar: barVal\r\n";Socket s=new Socket(DefaultServer.getDefaultServerAddress().getAddress(),DefaultServer.getDefaultServerAddress().getPort());try {s.getOutputStream().write(request.getBytes());StringBuilder sb=new StringBuilder();int read=0;byte[] buf=new byte[100];while (read < response1.length()){int r=s.getInputStream().read(buf);if (r <= 0)break;if (r > 0){read+=r;sb.append(new String(buf,0,r));}}try {Assert.assertEquals(response1,sb.toString());} catch (AssertionError e){Assert.assertEquals(response2,sb.toString());}s.getOutputStream().write(request.getBytes());sb=new StringBuilder();read=0;buf=new byte[100];while (read < response1.length()){int r=s.getInputStream().read(buf);if (r <= 0)break;if (r > 0){read+=r;sb.append(new String(buf,0,r));}}try {Assert.assertEquals(response1,sb.toString());} catch (AssertionError e){Assert.assertEquals(response2,sb.toString());}}  finally {s.close();}}

}
