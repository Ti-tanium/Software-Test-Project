package org.apache.remoting.transport.mina;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class MinaClientToServerTest {
@Test public void testFuture() throws Exception {
  ResponseFuture future=client.request(new World("world"));
  Hello result=(Hello)future.get();
  Assert.assertEquals("hello,world",result.getName());
}

}