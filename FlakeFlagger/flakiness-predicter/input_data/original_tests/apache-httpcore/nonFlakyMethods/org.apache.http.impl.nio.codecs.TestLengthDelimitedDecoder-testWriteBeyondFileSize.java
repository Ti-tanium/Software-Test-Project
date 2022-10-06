@Test public void testWriteBeyondFileSize() throws Exception {
  ReadableByteChannel channel=new ReadableByteChannelMock(new String[]{"a"},"US-ASCII");
  HttpParams params=new BasicHttpParams();
  SessionInputBuffer inbuf=new SessionInputBufferImpl(1024,256,params);
  HttpTransportMetricsImpl metrics=new HttpTransportMetricsImpl();
  LengthDelimitedDecoder decoder=new LengthDelimitedDecoder(channel,inbuf,metrics,1);
  createTempFile();
  RandomAccessFile testfile=new RandomAccessFile(this.tmpfile,"rw");
  try {
    FileChannel fchannel=testfile.getChannel();
    Assert.assertEquals(0,testfile.length());
    try {
      decoder.transfer(fchannel,5,10);
      Assert.fail("IOException should have been thrown");
    }
 catch (    IOException expected) {
    }
  }
  finally {
    testfile.close();
  }
}
