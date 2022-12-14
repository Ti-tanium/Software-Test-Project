package org.apache.dubbo.common.serialize.serialization;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class NativeJavaSerializationTest {
@Test public void test_floatArray_withType() throws Exception {
  float[] data=new float[]{37F,-3.14F,123456.7F};
  ObjectOutput objectOutput=serialization.serialize(url,byteArrayOutputStream);
  objectOutput.writeObject(data);
  objectOutput.flushBuffer();
  ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
  ObjectInput deserialize=serialization.deserialize(url,byteArrayInputStream);
  assertArrayEquals(data,(float[])deserialize.readObject(float[].class),0.0001F);
  try {
    deserialize.readObject(float[].class);
    fail();
  }
 catch (  IOException expected) {
  }
}

}