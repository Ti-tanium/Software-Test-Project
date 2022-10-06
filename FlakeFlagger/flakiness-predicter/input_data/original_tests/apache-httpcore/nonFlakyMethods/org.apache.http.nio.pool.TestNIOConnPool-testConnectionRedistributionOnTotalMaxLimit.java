@Test public void testConnectionRedistributionOnTotalMaxLimit() throws Exception {
  IOSession iosession1=Mockito.mock(IOSession.class);
  SessionRequest sessionRequest1=Mockito.mock(SessionRequest.class);
  Mockito.when(sessionRequest1.getAttachment()).thenReturn("somehost");
  Mockito.when(sessionRequest1.getSession()).thenReturn(iosession1);
  IOSession iosession2=Mockito.mock(IOSession.class);
  SessionRequest sessionRequest2=Mockito.mock(SessionRequest.class);
  Mockito.when(sessionRequest2.getAttachment()).thenReturn("somehost");
  Mockito.when(sessionRequest2.getSession()).thenReturn(iosession2);
  IOSession iosession3=Mockito.mock(IOSession.class);
  SessionRequest sessionRequest3=Mockito.mock(SessionRequest.class);
  Mockito.when(sessionRequest3.getAttachment()).thenReturn("otherhost");
  Mockito.when(sessionRequest3.getSession()).thenReturn(iosession3);
  IOSession iosession4=Mockito.mock(IOSession.class);
  SessionRequest sessionRequest4=Mockito.mock(SessionRequest.class);
  Mockito.when(sessionRequest4.getAttachment()).thenReturn("otherhost");
  Mockito.when(sessionRequest4.getSession()).thenReturn(iosession4);
  ConnectingIOReactor ioreactor=Mockito.mock(ConnectingIOReactor.class);
  Mockito.when(ioreactor.connect(Mockito.eq(InetSocketAddress.createUnresolved("somehost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class))).thenReturn(sessionRequest1,sessionRequest2,sessionRequest1);
  Mockito.when(ioreactor.connect(Mockito.eq(InetSocketAddress.createUnresolved("otherhost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class))).thenReturn(sessionRequest3,sessionRequest4,sessionRequest3);
  LocalSessionPool pool=new LocalSessionPool(ioreactor,2,10);
  pool.setMaxPerRoute("somehost",2);
  pool.setMaxPerRoute("otherhost",2);
  pool.setMaxTotal(2);
  Future<LocalPoolEntry> future1=pool.lease("somehost",null);
  Future<LocalPoolEntry> future2=pool.lease("somehost",null);
  Future<LocalPoolEntry> future3=pool.lease("otherhost",null);
  Future<LocalPoolEntry> future4=pool.lease("otherhost",null);
  Mockito.verify(ioreactor,Mockito.times(2)).connect(Mockito.eq(InetSocketAddress.createUnresolved("somehost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  Mockito.verify(ioreactor,Mockito.never()).connect(Mockito.eq(InetSocketAddress.createUnresolved("otherhost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  pool.requestCompleted(sessionRequest1);
  pool.requestCompleted(sessionRequest2);
  Assert.assertTrue(future1.isDone());
  LocalPoolEntry entry1=future1.get();
  Assert.assertNotNull(entry1);
  Assert.assertTrue(future2.isDone());
  LocalPoolEntry entry2=future2.get();
  Assert.assertNotNull(entry2);
  Assert.assertFalse(future3.isDone());
  Assert.assertFalse(future4.isDone());
  PoolStats totals=pool.getTotalStats();
  Assert.assertEquals(0,totals.getAvailable());
  Assert.assertEquals(2,totals.getLeased());
  Assert.assertEquals(0,totals.getPending());
  pool.release(entry1,true);
  pool.release(entry2,true);
  Mockito.verify(ioreactor,Mockito.times(2)).connect(Mockito.eq(InetSocketAddress.createUnresolved("somehost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  Mockito.verify(ioreactor,Mockito.times(2)).connect(Mockito.eq(InetSocketAddress.createUnresolved("otherhost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  pool.requestCompleted(sessionRequest3);
  pool.requestCompleted(sessionRequest4);
  Assert.assertTrue(future3.isDone());
  LocalPoolEntry entry3=future3.get();
  Assert.assertNotNull(entry3);
  Assert.assertTrue(future4.isDone());
  LocalPoolEntry entry4=future4.get();
  Assert.assertNotNull(entry4);
  totals=pool.getTotalStats();
  Assert.assertEquals(0,totals.getAvailable());
  Assert.assertEquals(2,totals.getLeased());
  Assert.assertEquals(0,totals.getPending());
  Future<LocalPoolEntry> future5=pool.lease("somehost",null);
  Future<LocalPoolEntry> future6=pool.lease("otherhost",null);
  Mockito.verify(ioreactor,Mockito.times(2)).connect(Mockito.eq(InetSocketAddress.createUnresolved("somehost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  Mockito.verify(ioreactor,Mockito.times(2)).connect(Mockito.eq(InetSocketAddress.createUnresolved("otherhost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  pool.release(entry3,true);
  pool.release(entry4,true);
  Mockito.verify(ioreactor,Mockito.times(3)).connect(Mockito.eq(InetSocketAddress.createUnresolved("somehost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  Mockito.verify(ioreactor,Mockito.times(2)).connect(Mockito.eq(InetSocketAddress.createUnresolved("otherhost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  pool.requestCompleted(sessionRequest1);
  Assert.assertTrue(future5.isDone());
  LocalPoolEntry entry5=future5.get();
  Assert.assertNotNull(entry5);
  Assert.assertTrue(future6.isDone());
  LocalPoolEntry entry6=future6.get();
  Assert.assertNotNull(entry6);
  totals=pool.getTotalStats();
  Assert.assertEquals(0,totals.getAvailable());
  Assert.assertEquals(2,totals.getLeased());
  Assert.assertEquals(0,totals.getPending());
  pool.release(entry5,true);
  pool.release(entry6,true);
  Mockito.verify(ioreactor,Mockito.times(3)).connect(Mockito.eq(InetSocketAddress.createUnresolved("somehost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  Mockito.verify(ioreactor,Mockito.times(2)).connect(Mockito.eq(InetSocketAddress.createUnresolved("otherhost",80)),Mockito.any(SocketAddress.class),Mockito.any(),Mockito.any(SessionRequestCallback.class));
  totals=pool.getTotalStats();
  Assert.assertEquals(2,totals.getAvailable());
  Assert.assertEquals(0,totals.getLeased());
  Assert.assertEquals(0,totals.getPending());
}
