@SuppressWarnings("unchecked") @Test public void testJavaException(){
  ExceptionFilter exceptionFilter=new ExceptionFilter();
  RpcInvocation invocation=new RpcInvocation("sayHello",new Class<?>[]{String.class},new Object[]{"world"});
  RpcResult rpcResult=new RpcResult();
  rpcResult.setException(new IllegalArgumentException("java"));
  Invoker<DemoService> invoker=mock(Invoker.class);
  when(invoker.invoke(invocation)).thenReturn(rpcResult);
  when(invoker.getInterface()).thenReturn(DemoService.class);
  Result newResult=exceptionFilter.invoke(invoker,invocation);
  Assert.assertEquals(rpcResult.getException(),newResult.getException());
}
