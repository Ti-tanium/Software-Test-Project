@Test public void testExecutionEntityEnclosingRequestWithExpectContinueFailure() throws Exception {
  HttpProcessor httprocessor=Mockito.mock(HttpProcessor.class);
  HttpClientConnection conn=Mockito.mock(HttpClientConnection.class);
  HttpRequestExecutor executor=new HttpRequestExecutor();
  HttpContext context=new BasicHttpContext();
  HttpEntityEnclosingRequest request=new BasicHttpEntityEnclosingRequest("POST","/");
  request.addHeader(HTTP.EXPECT_DIRECTIVE,HTTP.EXPECT_CONTINUE);
  HttpEntity entity=Mockito.mock(HttpEntity.class);
  request.setEntity(entity);
  executor.preProcess(request,httprocessor,context);
  Mockito.verify(httprocessor).process(request,context);
  Mockito.when(conn.receiveResponseHeader()).thenReturn(new BasicHttpResponse(HttpVersion.HTTP_1_1,402,"OK"));
  Mockito.when(conn.isResponseAvailable(Mockito.anyInt())).thenReturn(true);
  HttpResponse response=executor.execute(request,conn,context);
  Mockito.verify(conn).sendRequestHeader(request);
  Mockito.verify(conn,Mockito.never()).sendRequestEntity(request);
  Mockito.verify(conn,Mockito.times(2)).flush();
  Mockito.verify(conn).isResponseAvailable(2000);
  Mockito.verify(conn).receiveResponseHeader();
  Mockito.verify(conn).receiveResponseEntity(response);
  executor.postProcess(response,httprocessor,context);
  Mockito.verify(httprocessor).process(response,context);
}
