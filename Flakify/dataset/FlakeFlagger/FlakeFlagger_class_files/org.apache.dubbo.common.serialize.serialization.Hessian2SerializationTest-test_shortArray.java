package org.apache.dubbo.common.serialize.serialization;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class Hessian2SerializationTest {
@Test public void test_shortArray() throws Exception {
  short[] data=new short[]{37,39,12};
  ObjectOutput objectOutput=serialization.serialize(url,byteArrayOutputStream);
  objectOutput.writeObject(data);
  objectOutput.flushBuffer();
  ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
  ObjectInput deserialize=serialization.deserialize(url,byteArrayInputStream);
  assertArrayEquals(data,(short[])deserialize.readObject());
  try {
    deserialize.readObject();
    fail();
  }
 catch (  IOException expected) {
  }
}

}