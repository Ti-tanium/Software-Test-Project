package com.squareup.okhttp.internal.http;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class HttpOverHttp20Draft09Test {
@Test public void get() throws Exception {
  MockResponse response=new MockResponse().setBody("ABCDE").setStatus("HTTP/1.1 200 Sweet");
  server.enqueue(response);
  server.play();
  connection=client.open(server.getUrl("/foo"));
  assertContent("ABCDE",connection,Integer.MAX_VALUE);
  assertEquals(200,connection.getResponseCode());
  assertEquals("Sweet",connection.getResponseMessage());
  RecordedRequest request=server.takeRequest();
  assertEquals("GET /foo HTTP/1.1",request.getRequestLine());
  assertContains(request.getHeaders(),":scheme: https");
  assertContains(request.getHeaders(),hostHeader + ": " + hostName+ ":"+ server.getPort());
}

}