@Test public void testBasicProtocolDowngrade() throws Exception {
  HttpProcessor httprocessor=Mockito.mock(HttpProcessor.class);
  ConnectionReuseStrategy connReuseStrategy=Mockito.mock(ConnectionReuseStrategy.class);
  HttpResponseFactory responseFactory=Mockito.mock(HttpResponseFactory.class);
  HttpRequestHandlerResolver handlerResolver=Mockito.mock(HttpRequestHandlerResolver.class);
  HttpParams params=new SyncBasicHttpParams();
  HttpService httpservice=new HttpService(httprocessor,connReuseStrategy,responseFactory,handlerResolver,params);
  HttpContext context=new BasicHttpContext();
  HttpServerConnection conn=Mockito.mock(HttpServerConnection.class);
  HttpRequest request=new BasicHttpRequest("GET","/",new HttpVersion(20,45));
  Mockito.when(conn.receiveRequestHeader()).thenReturn(request);
  HttpResponse response=new BasicHttpResponse(HttpVersion.HTTP_1_1,200,"OK");
  Mockito.when(responseFactory.newHttpResponse(HttpVersion.HTTP_1_1,200,context)).thenReturn(response);
  Mockito.when(connReuseStrategy.keepAlive(response,context)).thenReturn(false);
  httpservice.handleRequest(conn,context);
  Mockito.verify(responseFactory).newHttpResponse(HttpVersion.HTTP_1_1,200,context);
}
