public void testUpdate_CompareDeletes(){
  GetDeleteTracker.DeleteCompare res=null;
  res=dt.compareDeletes(del10,del10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_BOTH,res);
  res=dt.compareDeletes(del10,del11);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_OLD,res);
  res=dt.compareDeletes(del11,del10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_NEW_NEXT_NEW,res);
  res=dt.compareDeletes(del10,del20);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_NEW_NEXT_NEW,res);
  res=dt.compareDeletes(del20,del10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_OLD,res);
  res=dt.compareDeletes(delQf10,delQf10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_BOTH,res);
  res=dt.compareDeletes(delQf10,delQf11);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_OLD,res);
  res=dt.compareDeletes(delQf11,delQf10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_NEW_NEXT_NEW,res);
  res=dt.compareDeletes(delQf10,delQf20);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_NEW_NEXT_BOTH,res);
  res=dt.compareDeletes(delQf20,delQf10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_BOTH,res);
  res=dt.compareDeletes(del10,delQf10);
  assertEquals(DeleteTracker.DeleteCompare.NEXT_OLD,res);
  res=dt.compareDeletes(delQf10,del10);
  assertEquals(DeleteTracker.DeleteCompare.NEXT_NEW,res);
  res=dt.compareDeletes(del10,delQf11);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_OLD,res);
  res=dt.compareDeletes(delQf11,del10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_NEW_NEXT_NEW,res);
  res=dt.compareDeletes(del11,delQf10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_NEW_NEXT_NEW,res);
  res=dt.compareDeletes(delQf10,del11);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_OLD,res);
  res=dt.compareDeletes(del20,delQf10);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_OLD_NEXT_OLD,res);
  res=dt.compareDeletes(delQf10,del20);
  assertEquals(DeleteTracker.DeleteCompare.INCLUDE_NEW_NEXT_NEW,res);
  res=dt.compareDeletes(del10,delQf20);
  assertEquals(DeleteTracker.DeleteCompare.NEXT_OLD,res);
  res=dt.compareDeletes(delQf20,del10);
  assertEquals(DeleteTracker.DeleteCompare.NEXT_NEW,res);
}
