package com.squareup.okhttp.internal.http;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class HttpOverSpdy3Test {
@Test public void spdyConnectionReuse() throws Exception {
  server.enqueue(new MockResponse().setBody("ABCDEF"));
  server.enqueue(new MockResponse().setBody("GHIJKL"));
  server.play();
  HttpURLConnection connection1=client.open(server.getUrl("/r1"));
  HttpURLConnection connection2=client.open(server.getUrl("/r2"));
  assertEquals("ABC",readAscii(connection1.getInputStream(),3));
  assertEquals("GHI",readAscii(connection2.getInputStream(),3));
  assertEquals("DEF",readAscii(connection1.getInputStream(),3));
  assertEquals("JKL",readAscii(connection2.getInputStream(),3));
  assertEquals(0,server.takeRequest().getSequenceNumber());
  assertEquals(1,server.takeRequest().getSequenceNumber());
}

}