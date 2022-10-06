@Test public void testIllegalConstructor() throws Exception {
  try {
    new SerializableEntity(null,false);
    Assert.fail("IllegalArgumentException should have been thrown");
  }
 catch (  IllegalArgumentException ex) {
  }
}
