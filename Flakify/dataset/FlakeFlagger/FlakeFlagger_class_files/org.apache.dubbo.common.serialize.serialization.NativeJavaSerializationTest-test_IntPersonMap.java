package org.apache.dubbo.common.serialize.serialization;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class NativeJavaSerializationTest {
@Test public void test_IntPersonMap() throws Exception {
  Map<Integer,Person> args=new HashMap<Integer,Person>();
  args.put(1,new Person());
  try {
    ObjectOutput objectOutput=serialization.serialize(url,byteArrayOutputStream);
    objectOutput.writeObject(args);
    fail();
  }
 catch (  NotSerializableException expected) {
  }
catch (  IllegalStateException expected) {
    assertThat(expected.getMessage(),containsString("Serialized class org.apache.dubbo.common.model.Person must implement java.io.Serializable"));
  }
}

}