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

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestHttpRequestExecutor {


    @Test public void testExecutionEntityEnclosingRequestWithExpectContinueSuccess() throws Exception{HttpProcessor httprocessor=Mockito.mock(HttpProcessor.class);HttpClientConnection conn=Mockito.mock(HttpClientConnection.class);HttpRequestExecutor executor=new HttpRequestExecutor();HttpContext context=new BasicHttpContext();HttpEntityEnclosingRequest request=new BasicHttpEntityEnclosingRequest("POST","/");request.addHeader(HTTP.EXPECT_DIRECTIVE,HTTP.EXPECT_CONTINUE);HttpEntity entity=Mockito.mock(HttpEntity.class);request.setEntity(entity);executor.preProcess(request,httprocessor,context);Mockito.verify(httprocessor).process(request,context);Mockito.when(conn.receiveResponseHeader()).thenReturn(new BasicHttpResponse(HttpVersion.HTTP_1_1,100,"Continue"),new BasicHttpResponse(HttpVersion.HTTP_1_1,200,"OK"));Mockito.when(conn.isResponseAvailable(Mockito.anyInt())).thenReturn(true);HttpResponse response=executor.execute(request,conn,context);Mockito.verify(conn).sendRequestHeader(request);Mockito.verify(conn).sendRequestEntity(request);Mockito.verify(conn,Mockito.times(2)).flush();Mockito.verify(conn).isResponseAvailable(2000);Mockito.verify(conn,Mockito.times(2)).receiveResponseHeader();Mockito.verify(conn).receiveResponseEntity(response);executor.postProcess(response,httprocessor,context);Mockito.verify(httprocessor).process(response,context);Assert.assertEquals(Boolean.TRUE,context.getAttribute(ExecutionContext.HTTP_REQ_SENT));}

}
