/*
 * Copyright (C) 2012 Google Inc.
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
package com.squareup.okhttp.mockwebserver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.TestCase;

public class CustomDispatcherTest extends TestCase {

    private MockWebServer mockWebServer = new MockWebServer();

    public void testSimpleDispatch() throws Exception{mockWebServer.play();final List<RecordedRequest> requestsMade=new ArrayList<RecordedRequest>();final Dispatcher dispatcher=new Dispatcher(){@Override public MockResponse dispatch(RecordedRequest request) throws InterruptedException{requestsMade.add(request);return new MockResponse();}};assertEquals(0,requestsMade.size());mockWebServer.setDispatcher(dispatcher);final URL url=mockWebServer.getUrl("/");final HttpURLConnection conn=(HttpURLConnection)url.openConnection();conn.getResponseCode();assertEquals(1,requestsMade.size());}

    private Thread buildRequestThread(final String path, final AtomicInteger responseCode) {
        return new Thread(new Runnable() {
            @Override public void run() {
                final URL url = mockWebServer.getUrl(path);
                final HttpURLConnection conn;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    responseCode.set(conn.getResponseCode()); // Force the connection to hit the "server".
                } catch (IOException e) {
                }
            }
        });
    }

}
