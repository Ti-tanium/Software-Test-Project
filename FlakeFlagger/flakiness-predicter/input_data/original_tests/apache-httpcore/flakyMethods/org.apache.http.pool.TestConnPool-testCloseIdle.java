@Test public void testCloseIdle() throws Exception {
  HttpConnectionFactory connFactory=Mockito.mock(HttpConnectionFactory.class);
  HttpConnection conn1=Mockito.mock(HttpConnection.class);
  HttpConnection conn2=Mockito.mock(HttpConnection.class);
  Mockito.when(connFactory.create(Mockito.eq("somehost"))).thenReturn(conn1,conn2);
  LocalConnPool pool=new LocalConnPool(connFactory,2,2);
  Future<LocalPoolEntry> future1=pool.lease("somehost",null);
  LocalPoolEntry entry1=future1.get(1,TimeUnit.SECONDS);
  Assert.assertNotNull(entry1);
  Future<LocalPoolEntry> future2=pool.lease("somehost",null);
  LocalPoolEntry entry2=future2.get(1,TimeUnit.SECONDS);
  Assert.assertNotNull(entry2);
  entry1.updateExpiry(0,TimeUnit.MILLISECONDS);
  pool.release(entry1,true);
  Thread.sleep(200L);
  entry2.updateExpiry(0,TimeUnit.MILLISECONDS);
  pool.release(entry2,true);
  pool.closeIdle(50,TimeUnit.MILLISECONDS);
  Mockito.verify(conn1).close();
  Mockito.verify(conn2,Mockito.never()).close();
  PoolStats totals=pool.getTotalStats();
  Assert.assertEquals(1,totals.getAvailable());
  Assert.assertEquals(0,totals.getLeased());
  PoolStats stats=pool.getStats("somehost");
  Assert.assertEquals(1,stats.getAvailable());
  Assert.assertEquals(0,stats.getLeased());
  pool.closeIdle(-1,TimeUnit.MILLISECONDS);
  Mockito.verify(conn2).close();
  totals=pool.getTotalStats();
  Assert.assertEquals(0,totals.getAvailable());
  Assert.assertEquals(0,totals.getLeased());
  stats=pool.getStats("somehost");
  Assert.assertEquals(0,stats.getAvailable());
  Assert.assertEquals(0,stats.getLeased());
}
