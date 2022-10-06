@Test public void testMalformedChunkSizeDecoding() throws Exception {
  String s="5\r\n01234\r\n5zz\r\n56789\r\n6\r\nabcdef\r\n0\r\n\r\n";
  ReadableByteChannel channel=new ReadableByteChannelMock(new String[]{s},"US-ASCII");
  HttpParams params=new BasicHttpParams();
  SessionInputBuffer inbuf=new SessionInputBufferImpl(1024,256,params);
  HttpTransportMetricsImpl metrics=new HttpTransportMetricsImpl();
  ChunkDecoder decoder=new ChunkDecoder(channel,inbuf,metrics);
  ByteBuffer dst=ByteBuffer.allocate(1024);
  try {
    decoder.read(dst);
    Assert.fail("MalformedChunkCodingException should have been thrown");
  }
 catch (  MalformedChunkCodingException ex) {
  }
}
