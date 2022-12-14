/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.PushPromise;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpOverHttp20Draft09Test extends HttpOverSpdyTest {

  @Test public void serverSendsPushPromise_GET() throws Exception{MockResponse response=new MockResponse().setBody("ABCDE").setStatus("HTTP/1.1 200 Sweet").withPush(new PushPromise("GET","/foo/bar",Arrays.asList("foo: bar"),new MockResponse().setBody("bar").setStatus("HTTP/1.1 200 Sweet")));server.enqueue(response);server.play();connection=client.open(server.getUrl("/foo"));assertContent("ABCDE",connection,Integer.MAX_VALUE);assertEquals(200,connection.getResponseCode());assertEquals("Sweet",connection.getResponseMessage());RecordedRequest request=server.takeRequest();assertEquals("GET /foo HTTP/1.1",request.getRequestLine());assertContains(request.getHeaders(),":scheme: https");assertContains(request.getHeaders(),hostHeader + ": " + hostName + ":" + server.getPort());RecordedRequest pushedRequest=server.takeRequest();assertEquals("GET /foo/bar HTTP/1.1",pushedRequest.getRequestLine());assertEquals(Arrays.asList("foo: bar"),pushedRequest.getHeaders());}
}
