@Test public void testDeletes() throws Exception {
  byte[] TABLE=Bytes.toBytes("testDeletes");
  byte[][] ROWS=makeNAscii(ROW,6);
  byte[][] FAMILIES=makeNAscii(FAMILY,3);
  byte[][] VALUES=makeN(VALUE,5);
  long[] ts={1000,2000,3000,4000,5000};
  HTable ht=TEST_UTIL.createTable(TABLE,FAMILIES);
  Put put=new Put(ROW);
  put.add(FAMILIES[0],QUALIFIER,ts[0],VALUES[0]);
  put.add(FAMILIES[0],QUALIFIER,ts[1],VALUES[1]);
  ht.put(put);
  Delete delete=new Delete(ROW);
  delete.deleteFamily(FAMILIES[0],ts[0]);
  ht.delete(delete);
  Get get=new Get(ROW);
  get.addFamily(FAMILIES[0]);
  get.setMaxVersions(Integer.MAX_VALUE);
  Result result=ht.get(get);
  assertNResult(result,ROW,FAMILIES[0],QUALIFIER,new long[]{ts[1]},new byte[][]{VALUES[1]},0,0);
  Scan scan=new Scan(ROW);
  scan.addFamily(FAMILIES[0]);
  scan.setMaxVersions(Integer.MAX_VALUE);
  result=getSingleScanResult(ht,scan);
  assertNResult(result,ROW,FAMILIES[0],QUALIFIER,new long[]{ts[1]},new byte[][]{VALUES[1]},0,0);
  put=new Put(ROW);
  put.add(FAMILIES[0],QUALIFIER,ts[4],VALUES[4]);
  put.add(FAMILIES[0],QUALIFIER,ts[2],VALUES[2]);
  put.add(FAMILIES[0],QUALIFIER,ts[3],VALUES[3]);
  put.add(FAMILIES[0],null,ts[4],VALUES[4]);
  put.add(FAMILIES[0],null,ts[2],VALUES[2]);
  put.add(FAMILIES[0],null,ts[3],VALUES[3]);
  ht.put(put);
  delete=new Delete(ROW);
  delete.deleteColumn(FAMILIES[0],QUALIFIER);
  ht.delete(delete);
  get=new Get(ROW);
  get.addColumn(FAMILIES[0],QUALIFIER);
  get.setMaxVersions(Integer.MAX_VALUE);
  result=ht.get(get);
  assertNResult(result,ROW,FAMILIES[0],QUALIFIER,new long[]{ts[1],ts[2],ts[3]},new byte[][]{VALUES[1],VALUES[2],VALUES[3]},0,2);
  scan=new Scan(ROW);
  scan.addColumn(FAMILIES[0],QUALIFIER);
  scan.setMaxVersions(Integer.MAX_VALUE);
  result=getSingleScanResult(ht,scan);
  assertNResult(result,ROW,FAMILIES[0],QUALIFIER,new long[]{ts[1],ts[2],ts[3]},new byte[][]{VALUES[1],VALUES[2],VALUES[3]},0,2);
  delete=new Delete(ROW);
  delete.deleteColumn(FAMILIES[0],null);
  ht.delete(delete);
  delete=new Delete(ROW);
  delete.deleteColumns(FAMILIES[0],null);
  ht.delete(delete);
  put=new Put(ROW);
  put.add(FAMILIES[0],QUALIFIER,ts[0],VALUES[0]);
  put.add(FAMILIES[0],QUALIFIER,ts[4],VALUES[4]);
  ht.put(put);
  get=new Get(ROW);
  get.addFamily(FAMILIES[0]);
  get.setMaxVersions(Integer.MAX_VALUE);
  result=ht.get(get);
  assertNResult(result,ROW,FAMILIES[0],QUALIFIER,new long[]{ts[2],ts[3],ts[4]},new byte[][]{VALUES[2],VALUES[3],VALUES[4]},0,2);
  scan=new Scan(ROW);
  scan.addFamily(FAMILIES[0]);
  scan.setMaxVersions(Integer.MAX_VALUE);
  result=getSingleScanResult(ht,scan);
  assertNResult(result,ROW,FAMILIES[0],QUALIFIER,new long[]{ts[1],ts[2],ts[3]},new byte[][]{VALUES[1],VALUES[2],VALUES[3]},0,2);
  put=new Put(ROWS[0]);
  put.add(FAMILIES[1],QUALIFIER,ts[0],VALUES[0]);
  put.add(FAMILIES[1],QUALIFIER,ts[1],VALUES[1]);
  put.add(FAMILIES[2],QUALIFIER,ts[2],VALUES[2]);
  put.add(FAMILIES[2],QUALIFIER,ts[3],VALUES[3]);
  ht.put(put);
  put=new Put(ROWS[1]);
  put.add(FAMILIES[1],QUALIFIER,ts[0],VALUES[0]);
  put.add(FAMILIES[1],QUALIFIER,ts[1],VALUES[1]);
  put.add(FAMILIES[2],QUALIFIER,ts[2],VALUES[2]);
  put.add(FAMILIES[2],QUALIFIER,ts[3],VALUES[3]);
  ht.put(put);
  put=new Put(ROWS[2]);
  put.add(FAMILIES[1],QUALIFIER,ts[0],VALUES[0]);
  put.add(FAMILIES[1],QUALIFIER,ts[1],VALUES[1]);
  put.add(FAMILIES[2],QUALIFIER,ts[2],VALUES[2]);
  put.add(FAMILIES[2],QUALIFIER,ts[3],VALUES[3]);
  ht.put(put);
  delete=new Delete(ROWS[0]);
  delete.deleteFamily(FAMILIES[2]);
  ht.delete(delete);
  delete=new Delete(ROWS[1]);
  delete.deleteColumns(FAMILIES[1],QUALIFIER);
  ht.delete(delete);
  delete=new Delete(ROWS[2]);
  delete.deleteColumn(FAMILIES[1],QUALIFIER);
  delete.deleteColumn(FAMILIES[1],QUALIFIER);
  delete.deleteColumn(FAMILIES[2],QUALIFIER);
  ht.delete(delete);
  get=new Get(ROWS[0]);
  get.addFamily(FAMILIES[1]);
  get.addFamily(FAMILIES[2]);
  get.setMaxVersions(Integer.MAX_VALUE);
  result=ht.get(get);
  assertTrue("Expected 2 keys but received " + result.size(),result.size() == 2);
  assertNResult(result,ROWS[0],FAMILIES[1],QUALIFIER,new long[]{ts[0],ts[1]},new byte[][]{VALUES[0],VALUES[1]},0,1);
  scan=new Scan(ROWS[0]);
  scan.addFamily(FAMILIES[1]);
  scan.addFamily(FAMILIES[2]);
  scan.setMaxVersions(Integer.MAX_VALUE);
  result=getSingleScanResult(ht,scan);
  assertTrue("Expected 2 keys but received " + result.size(),result.size() == 2);
  assertNResult(result,ROWS[0],FAMILIES[1],QUALIFIER,new long[]{ts[0],ts[1]},new byte[][]{VALUES[0],VALUES[1]},0,1);
  get=new Get(ROWS[1]);
  get.addFamily(FAMILIES[1]);
  get.addFamily(FAMILIES[2]);
  get.setMaxVersions(Integer.MAX_VALUE);
  result=ht.get(get);
  assertTrue("Expected 2 keys but received " + result.size(),result.size() == 2);
  scan=new Scan(ROWS[1]);
  scan.addFamily(FAMILIES[1]);
  scan.addFamily(FAMILIES[2]);
  scan.setMaxVersions(Integer.MAX_VALUE);
  result=getSingleScanResult(ht,scan);
  assertTrue("Expected 2 keys but received " + result.size(),result.size() == 2);
  get=new Get(ROWS[2]);
  get.addFamily(FAMILIES[1]);
  get.addFamily(FAMILIES[2]);
  get.setMaxVersions(Integer.MAX_VALUE);
  result=ht.get(get);
  assertTrue("Expected 1 key but received " + result.size(),result.size() == 1);
  assertNResult(result,ROWS[2],FAMILIES[2],QUALIFIER,new long[]{ts[2]},new byte[][]{VALUES[2]},0,0);
  scan=new Scan(ROWS[2]);
  scan.addFamily(FAMILIES[1]);
  scan.addFamily(FAMILIES[2]);
  scan.setMaxVersions(Integer.MAX_VALUE);
  result=getSingleScanResult(ht,scan);
  assertTrue("Expected 1 key but received " + result.size(),result.size() == 1);
  assertNResult(result,ROWS[2],FAMILIES[2],QUALIFIER,new long[]{ts[2]},new byte[][]{VALUES[2]},0,0);
  delete=new Delete(ROWS[3]);
  delete.deleteFamily(FAMILIES[1]);
  ht.delete(delete);
  put=new Put(ROWS[3]);
  put.add(FAMILIES[2],QUALIFIER,VALUES[0]);
  ht.put(put);
  put=new Put(ROWS[4]);
  put.add(FAMILIES[1],QUALIFIER,VALUES[1]);
  put.add(FAMILIES[2],QUALIFIER,VALUES[2]);
  ht.put(put);
  get=new Get(ROWS[3]);
  get.addFamily(FAMILIES[1]);
  get.addFamily(FAMILIES[2]);
  get.setMaxVersions(Integer.MAX_VALUE);
  result=ht.get(get);
  assertTrue("Expected 1 key but received " + result.size(),result.size() == 1);
  get=new Get(ROWS[4]);
  get.addFamily(FAMILIES[1]);
  get.addFamily(FAMILIES[2]);
  get.setMaxVersions(Integer.MAX_VALUE);
  result=ht.get(get);
  assertTrue("Expected 2 keys but received " + result.size(),result.size() == 2);
  scan=new Scan(ROWS[3]);
  scan.addFamily(FAMILIES[1]);
  scan.addFamily(FAMILIES[2]);
  scan.setMaxVersions(Integer.MAX_VALUE);
  ResultScanner scanner=ht.getScanner(scan);
  result=scanner.next();
  assertTrue("Expected 1 key but received " + result.size(),result.size() == 1);
  assertTrue(Bytes.equals(result.sorted()[0].getRow(),ROWS[3]));
  assertTrue(Bytes.equals(result.sorted()[0].getValue(),VALUES[0]));
  result=scanner.next();
  assertTrue("Expected 2 keys but received " + result.size(),result.size() == 2);
  assertTrue(Bytes.equals(result.sorted()[0].getRow(),ROWS[4]));
  assertTrue(Bytes.equals(result.sorted()[1].getRow(),ROWS[4]));
  assertTrue(Bytes.equals(result.sorted()[0].getValue(),VALUES[1]));
  assertTrue(Bytes.equals(result.sorted()[1].getValue(),VALUES[2]));
  scanner.close();
  for (int i=0; i < 10; i++) {
    byte[] bytes=Bytes.toBytes(i);
    put=new Put(bytes);
    put.add(FAMILIES[0],QUALIFIER,bytes);
    ht.put(put);
  }
  for (int i=0; i < 10; i++) {
    byte[] bytes=Bytes.toBytes(i);
    get=new Get(bytes);
    get.addFamily(FAMILIES[0]);
    result=ht.get(get);
    assertTrue(result.size() == 1);
  }
  ArrayList<Delete> deletes=new ArrayList<Delete>();
  for (int i=0; i < 10; i++) {
    byte[] bytes=Bytes.toBytes(i);
    delete=new Delete(bytes);
    delete.deleteFamily(FAMILIES[0]);
    deletes.add(delete);
  }
  ht.delete(deletes);
  for (int i=0; i < 10; i++) {
    byte[] bytes=Bytes.toBytes(i);
    get=new Get(bytes);
    get.addFamily(FAMILIES[0]);
    result=ht.get(get);
    assertTrue(result.size() == 0);
  }
}
