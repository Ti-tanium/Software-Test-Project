@Test public void testSystemPropertiesBinding(){
  this.context.register(TestConfiguration.class);
  System.setProperty("name","foo");
  this.context.refresh();
  assertEquals(1,this.context.getBeanNamesForType(TestProperties.class).length);
  assertEquals("foo",this.context.getBean(TestProperties.class).name);
}
