package org.apache.dubbo.common.serialize.serialization;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class JavaSerializationTest {
@Test public void test_StringSet() throws Exception {
  Set<String> args=new HashSet<String>();
  args.add("1");
  assertObject(args);
}

}