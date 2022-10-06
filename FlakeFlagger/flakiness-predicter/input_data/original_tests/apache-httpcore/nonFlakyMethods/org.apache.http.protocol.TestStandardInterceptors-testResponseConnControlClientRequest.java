@Test public void testResponseConnControlClientRequest() throws Exception {
  HttpContext context=new BasicHttpContext(null);
  BasicHttpRequest request=new BasicHttpRequest("GET","/");
  request.addHeader(new BasicHeader(HTTP.CONN_DIRECTIVE,HTTP.CONN_KEEP_ALIVE));
  context.setAttribute(ExecutionContext.HTTP_REQUEST,request);
  HttpResponse response=new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK,"OK");
  StringEntity entity=new StringEntity("whatever");
  response.setEntity(entity);
  ResponseConnControl interceptor=new ResponseConnControl();
  interceptor.process(response,context);
  Header header=response.getFirstHeader(HTTP.CONN_DIRECTIVE);
  Assert.assertNotNull(header);
  Assert.assertEquals(HTTP.CONN_KEEP_ALIVE,header.getValue());
}
