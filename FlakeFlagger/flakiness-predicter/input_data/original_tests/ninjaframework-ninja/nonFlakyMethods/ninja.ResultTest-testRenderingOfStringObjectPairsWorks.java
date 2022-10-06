@Test public void testRenderingOfStringObjectPairsWorks(){
  String object1=new String("stringy1");
  String object2=new String("stringy2");
  Result result=new Result(200);
  result.render("object1",object1);
  result.render("object2",object2);
  Map<String,Object> resultMap=(Map)result.getRenderable();
  assertEquals(object1,resultMap.get("object1"));
  assertEquals(object2,resultMap.get("object2"));
}
