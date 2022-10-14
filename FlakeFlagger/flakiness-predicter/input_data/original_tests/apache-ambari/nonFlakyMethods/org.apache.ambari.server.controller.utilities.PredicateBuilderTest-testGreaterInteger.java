@Test public void testGreaterInteger(){
  String p1=PropertyHelper.getPropertyId("cat1","prop1");
  Resource resource=new ResourceImpl(Resource.Type.Cluster);
  resource.setProperty(p1,2);
  PredicateBuilder pb=new PredicateBuilder();
  Predicate predicate1=pb.property(p1).greaterThan(1).toPredicate();
  Assert.assertTrue(predicate1.evaluate(resource));
  PredicateBuilder pb2=new PredicateBuilder();
  Predicate predicate2=pb2.property(p1).greaterThan(99).toPredicate();
  Assert.assertFalse(predicate2.evaluate(resource));
}