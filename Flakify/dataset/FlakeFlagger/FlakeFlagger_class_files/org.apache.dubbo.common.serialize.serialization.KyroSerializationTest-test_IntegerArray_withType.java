package org.apache.dubbo.common.serialize.serialization;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class KyroSerializationTest {
@Test public void test_IntegerArray_withType() throws Exception {
  assertObjectArrayWithType(new Integer[]{234,0,-1},Integer[].class);
}

}