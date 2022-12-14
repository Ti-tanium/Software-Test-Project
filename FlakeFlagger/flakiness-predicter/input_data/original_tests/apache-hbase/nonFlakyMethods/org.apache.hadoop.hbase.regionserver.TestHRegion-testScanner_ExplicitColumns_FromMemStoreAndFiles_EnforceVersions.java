public void testScanner_ExplicitColumns_FromMemStoreAndFiles_EnforceVersions() throws IOException {
  byte[] tableName=Bytes.toBytes("testtable");
  byte[] row1=Bytes.toBytes("row1");
  byte[] fam1=Bytes.toBytes("fam1");
  byte[][] families={fam1};
  byte[] qf1=Bytes.toBytes("qualifier1");
  byte[] qf2=Bytes.toBytes("qualifier2");
  long ts1=1;
  long ts2=ts1 + 1;
  long ts3=ts1 + 2;
  long ts4=ts1 + 3;
  String method=this.getName();
  initHRegion(tableName,method,families);
  KeyValue kv14=new KeyValue(row1,fam1,qf1,ts4,KeyValue.Type.Put,null);
  KeyValue kv13=new KeyValue(row1,fam1,qf1,ts3,KeyValue.Type.Put,null);
  KeyValue kv12=new KeyValue(row1,fam1,qf1,ts2,KeyValue.Type.Put,null);
  KeyValue kv11=new KeyValue(row1,fam1,qf1,ts1,KeyValue.Type.Put,null);
  KeyValue kv24=new KeyValue(row1,fam1,qf2,ts4,KeyValue.Type.Put,null);
  KeyValue kv23=new KeyValue(row1,fam1,qf2,ts3,KeyValue.Type.Put,null);
  KeyValue kv22=new KeyValue(row1,fam1,qf2,ts2,KeyValue.Type.Put,null);
  KeyValue kv21=new KeyValue(row1,fam1,qf2,ts1,KeyValue.Type.Put,null);
  Put put=null;
  put=new Put(row1);
  put.add(kv14);
  put.add(kv24);
  region.put(put);
  region.flushcache();
  put=new Put(row1);
  put.add(kv23);
  put.add(kv13);
  region.put(put);
  region.flushcache();
  put=new Put(row1);
  put.add(kv22);
  put.add(kv12);
  region.put(put);
  region.flushcache();
  put=new Put(row1);
  put.add(kv21);
  put.add(kv11);
  region.put(put);
  List<KeyValue> expected=new ArrayList<KeyValue>();
  expected.add(kv14);
  expected.add(kv13);
  expected.add(kv12);
  expected.add(kv24);
  expected.add(kv23);
  expected.add(kv22);
  Scan scan=new Scan(row1);
  scan.addColumn(fam1,qf1);
  scan.addColumn(fam1,qf2);
  int versions=3;
  scan.setMaxVersions(versions);
  List<KeyValue> actual=new ArrayList<KeyValue>();
  InternalScanner scanner=region.getScanner(scan);
  boolean hasNext=scanner.next(actual);
  assertEquals(false,hasNext);
  for (int i=0; i < expected.size(); i++) {
    assertEquals(expected.get(i),actual.get(i));
  }
}
