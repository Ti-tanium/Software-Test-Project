@Test public void testAvailable() throws IOException {
  String s="5\r\n12345\r\n0\r\n";
  ChunkedInputStream in=new ChunkedInputStream(new SessionInputBufferMock(EncodingUtils.getBytes(s,CONTENT_CHARSET)));
  Assert.assertEquals(0,in.available());
  in.read();
  Assert.assertEquals(4,in.available());
}
