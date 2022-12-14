public void testSplit() throws Exception {
  String start="a";
  String end="{";
  SimpleTotalOrderPartitioner<byte[]> p=new SimpleTotalOrderPartitioner<byte[]>();
  this.conf.set(SimpleTotalOrderPartitioner.START,start);
  this.conf.set(SimpleTotalOrderPartitioner.END,end);
  p.setConf(this.conf);
  ImmutableBytesWritable c=new ImmutableBytesWritable(Bytes.toBytes("c"));
  int partition=p.getPartition(c,HConstants.EMPTY_BYTE_ARRAY,1);
  assertEquals(0,partition);
  partition=p.getPartition(c,HConstants.EMPTY_BYTE_ARRAY,2);
  assertEquals(0,partition);
  partition=p.getPartition(c,HConstants.EMPTY_BYTE_ARRAY,3);
  assertEquals(0,partition);
  ImmutableBytesWritable q=new ImmutableBytesWritable(Bytes.toBytes("q"));
  partition=p.getPartition(q,HConstants.EMPTY_BYTE_ARRAY,2);
  assertEquals(1,partition);
  partition=p.getPartition(q,HConstants.EMPTY_BYTE_ARRAY,3);
  assertEquals(2,partition);
  ImmutableBytesWritable startBytes=new ImmutableBytesWritable(Bytes.toBytes(start));
  partition=p.getPartition(startBytes,HConstants.EMPTY_BYTE_ARRAY,2);
  assertEquals(0,partition);
  partition=p.getPartition(startBytes,HConstants.EMPTY_BYTE_ARRAY,3);
  assertEquals(0,partition);
  ImmutableBytesWritable endBytes=new ImmutableBytesWritable(Bytes.toBytes("z"));
  partition=p.getPartition(endBytes,HConstants.EMPTY_BYTE_ARRAY,2);
  assertEquals(1,partition);
  partition=p.getPartition(endBytes,HConstants.EMPTY_BYTE_ARRAY,3);
  assertEquals(2,partition);
}
