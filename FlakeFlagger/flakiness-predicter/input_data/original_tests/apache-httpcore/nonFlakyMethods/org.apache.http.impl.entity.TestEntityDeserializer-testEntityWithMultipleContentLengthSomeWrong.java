@Test public void testEntityWithMultipleContentLengthSomeWrong() throws Exception {
  SessionInputBuffer inbuffer=new SessionInputBufferMock(new byte[]{'0'});
  HttpMessage message=new DummyHttpMessage();
  message.getParams().setBooleanParameter(CoreProtocolPNames.STRICT_TRANSFER_ENCODING,false);
  message.addHeader("Content-Type","unknown");
  message.addHeader("Content-Length","1");
  message.addHeader("Content-Length","yyy");
  message.addHeader("Content-Length","xxx");
  EntityDeserializer entitygen=new EntityDeserializer(new LaxContentLengthStrategy());
  HttpEntity entity=entitygen.deserialize(inbuffer,message);
  Assert.assertNotNull(entity);
  Assert.assertEquals(1,entity.getContentLength());
  Assert.assertFalse(entity.isChunked());
  InputStream instream=entity.getContent();
  Assert.assertNotNull(instream);
  Assert.assertTrue(instream instanceof ContentLengthInputStream);
  message.getParams().setBooleanParameter(CoreProtocolPNames.STRICT_TRANSFER_ENCODING,true);
  try {
    entitygen.deserialize(inbuffer,message);
    Assert.fail("ProtocolException should have been thrown");
  }
 catch (  ProtocolException ex) {
  }
}
