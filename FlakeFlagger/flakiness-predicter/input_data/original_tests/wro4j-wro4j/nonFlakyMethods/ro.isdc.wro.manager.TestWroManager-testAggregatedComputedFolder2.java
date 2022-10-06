@Test public void testAggregatedComputedFolder2() throws Exception {
  final HttpServletRequest request=Mockito.mock(HttpServletRequest.class);
  final HttpServletResponse response=Context.get().getResponse();
  Mockito.when(request.getRequestURI()).thenReturn("/wro4j/wro/path/to/g1.css");
  Context.set(Context.webContext(request,response,Mockito.mock(FilterConfig.class)));
  managerFactory.create().process();
  Assert.assertEquals("/wro4j/wro/path/to/",Context.get().getAggregatedFolderPath());
}
