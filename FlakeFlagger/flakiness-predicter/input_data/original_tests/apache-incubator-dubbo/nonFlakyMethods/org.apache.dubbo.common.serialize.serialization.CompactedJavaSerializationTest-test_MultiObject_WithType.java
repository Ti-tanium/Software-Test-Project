@Test public void test_MultiObject_WithType() throws Exception {
  ObjectOutput objectOutput=serialization.serialize(url,byteArrayOutputStream);
  objectOutput.writeBool(false);
  objectOutput.writeObject(bigPerson);
  objectOutput.writeByte((byte)23);
  objectOutput.writeObject(mediaContent);
  objectOutput.writeInt(-23);
  objectOutput.flushBuffer();
  ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
  ObjectInput deserialize=serialization.deserialize(url,byteArrayInputStream);
  assertEquals(false,deserialize.readBool());
  assertEquals(bigPerson,deserialize.readObject(BigPerson.class));
  assertEquals((byte)23,deserialize.readByte());
  assertEquals(mediaContent,deserialize.readObject(MediaContent.class));
  assertEquals(-23,deserialize.readInt());
  try {
    deserialize.readObject();
    fail();
  }
 catch (  IOException expected) {
  }
}
