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

import java.io.InputStream;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.ProtocolException;
import org.apache.http.UnsupportedHttpVersionException;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestHttpService {

    @Test public void testProtocolException() throws Exception{HttpProcessor httprocessor=Mockito.mock(HttpProcessor.class);ConnectionReuseStrategy connReuseStrategy=Mockito.mock(ConnectionReuseStrategy.class);HttpResponseFactory responseFactory=Mockito.mock(HttpResponseFactory.class);HttpRequestHandlerResolver handlerResolver=Mockito.mock(HttpRequestHandlerResolver.class);HttpRequestHandler requestHandler=Mockito.mock(HttpRequestHandler.class);HttpParams params=new SyncBasicHttpParams();HttpService httpservice=new HttpService(httprocessor,connReuseStrategy,responseFactory,handlerResolver,params);HttpContext context=new BasicHttpContext();HttpServerConnection conn=Mockito.mock(HttpServerConnection.class);HttpRequest request=new BasicHttpRequest("whatever","/");Mockito.when(conn.receiveRequestHeader()).thenReturn(request);HttpResponse response=new BasicHttpResponse(HttpVersion.HTTP_1_1,200,"OK");Mockito.when(responseFactory.newHttpResponse(HttpVersion.HTTP_1_1,200,context)).thenReturn(response);HttpResponse error=new BasicHttpResponse(HttpVersion.HTTP_1_0,500,"Oppsie");Mockito.when(responseFactory.newHttpResponse(HttpVersion.HTTP_1_0,500,context)).thenReturn(error);Mockito.when(handlerResolver.lookup("/")).thenReturn(requestHandler);Mockito.doThrow(new ProtocolException("oh, this world is wrong")).when(requestHandler).handle(request,response,context);Mockito.when(connReuseStrategy.keepAlive(error,context)).thenReturn(false);httpservice.handleRequest(conn,context);Assert.assertSame(conn,context.getAttribute(ExecutionContext.HTTP_CONNECTION));Assert.assertSame(request,context.getAttribute(ExecutionContext.HTTP_REQUEST));Assert.assertSame(error,context.getAttribute(ExecutionContext.HTTP_RESPONSE));Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,error.getStatusLine().getStatusCode());Mockito.verify(conn).sendResponseHeader(error);Mockito.verify(httprocessor).process(error,context);Mockito.verify(conn).sendResponseHeader(error);Mockito.verify(conn).sendResponseEntity(error);Mockito.verify(conn).flush();Mockito.verify(conn).close();}

}
