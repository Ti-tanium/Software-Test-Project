@Test public void test_PersonList() throws Exception {
  List<Person> args=new ArrayList<Person>();
  args.add(new Person());
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
