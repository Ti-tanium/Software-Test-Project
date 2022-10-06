@Test public void testChunkedConsistence() throws IOException {
  String input="76126;27823abcd;:q38a-\nkjc\rk%1ad\tkh/asdui\r\njkh+?\\suweb";
  ByteArrayOutputStream buffer=new ByteArrayOutputStream();
  OutputStream out=new ChunkedOutputStream(new SessionOutputBufferMock(buffer));
  out.write(EncodingUtils.getBytes(input,CONTENT_CHARSET));
  out.flush();
  out.close();
  out.close();
  buffer.close();
  InputStream in=new ChunkedInputStream(new SessionInputBufferMock(buffer.toByteArray()));
  byte[] d=new byte[10];
  ByteArrayOutputStream result=new ByteArrayOutputStream();
  int len=0;
  while ((len=in.read(d)) > 0) {
    result.write(d,0,len);
  }
  String output=EncodingUtils.getString(result.toByteArray(),CONTENT_CHARSET);
  Assert.assertEquals(input,output);
}
