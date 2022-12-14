package com.squareup.okhttp.internal.http;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class HttpOverHttp20Draft09Test {
@Test public void redirect() throws Exception {
  server.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_MOVED_TEMP).addHeader("Location: /foo").setBody("This page has moved!"));
  server.enqueue(new MockResponse().setBody("This is the new location!"));
  server.play();
  connection=client.open(server.getUrl("/"));
  assertContent("This is the new location!",connection,Integer.MAX_VALUE);
  RecordedRequest request1=server.takeRequest();
  assertEquals("/",request1.getPath());
  RecordedRequest request2=server.takeRequest();
  assertEquals("/foo",request2.getPath());
}

}