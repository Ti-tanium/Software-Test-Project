@Test public void modIdsTest(){
  Assert.assertEquals(API_MODULE,factory.getApiModId("main").getName());
  Assert.assertEquals("main",factory.getApiModId("main").getSlot());
  Assert.assertEquals(IMPL_MODULE,factory.getImplModId("main").getName());
  Assert.assertEquals("main",factory.getImplModId("main").getSlot());
  Assert.assertEquals(INJECTION_MODULE,factory.getInjectionModId("main").getName());
  Assert.assertEquals("main",factory.getInjectionModId("main").getSlot());
  Assert.assertEquals(API_MODULE,factory.getApiModId("myfaces").getName());
  Assert.assertEquals("myfaces",factory.getApiModId("myfaces").getSlot());
  Assert.assertEquals(IMPL_MODULE,factory.getImplModId("myfaces").getName());
  Assert.assertEquals("myfaces",factory.getImplModId("myfaces").getSlot());
  Assert.assertEquals(INJECTION_MODULE,factory.getInjectionModId("myfaces").getName());
  Assert.assertEquals("myfaces",factory.getInjectionModId("myfaces").getSlot());
  Assert.assertEquals(API_MODULE,factory.getApiModId("myfaces2").getName());
  Assert.assertEquals("myfaces2",factory.getApiModId("myfaces2").getSlot());
  Assert.assertEquals(IMPL_MODULE,factory.getImplModId("myfaces2").getName());
  Assert.assertEquals("myfaces2",factory.getImplModId("myfaces2").getSlot());
  Assert.assertEquals(INJECTION_MODULE,factory.getInjectionModId("myfaces2").getName());
  Assert.assertEquals("myfaces2",factory.getInjectionModId("myfaces2").getSlot());
}
