package org.apache.dubbo.common.serialize.serialization;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class JavaSerializationTest {
@Test public void test_StringSPersonListMap() throws Exception {
  Map<String,List<SerializablePerson>> args=new HashMap<String,List<SerializablePerson>>();
  List<SerializablePerson> sublist=new ArrayList<SerializablePerson>();
  sublist.add(new SerializablePerson());
  args.put("1",sublist);
  assertObject(args);
}

}