package org.apache.dubbo.common.serialize.serialization;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class CompactedJavaSerializationTest {
@Test public void test_LongWrap() throws Exception {
  assertObject(new Long(12));
}

}