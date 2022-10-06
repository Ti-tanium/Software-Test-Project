@Test public void testRoute_ReturnAll(){
  Router router=new ConditionRouterFactory().getRouter(getRouteUrl("host = " + NetUtils.getLocalHost() + " => "+ " host = "+ NetUtils.getLocalHost()));
  List<Invoker<String>> invokers=new ArrayList<Invoker<String>>();
  invokers.add(new MockInvoker<String>());
  invokers.add(new MockInvoker<String>());
  invokers.add(new MockInvoker<String>());
  List<Invoker<String>> filteredInvokers=router.route(invokers,URL.valueOf("consumer://" + NetUtils.getLocalHost() + "/com.foo.BarService"),new RpcInvocation());
  Assert.assertEquals(invokers,filteredInvokers);
}
