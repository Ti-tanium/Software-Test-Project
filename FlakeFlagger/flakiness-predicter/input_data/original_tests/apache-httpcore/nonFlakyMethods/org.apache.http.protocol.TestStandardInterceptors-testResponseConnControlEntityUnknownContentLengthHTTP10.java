@Test public void testResponseConnControlEntityUnknownContentLengthHTTP10() throws Exception {
  HttpContext context=new BasicHttpContext(null);
  BasicHttpRequest request=new BasicHttpRequest("GET","/");
  request.addHeader(new BasicHeader(HTTP.CONN_DIRECTIVE,HTTP.CONN_KEEP_ALIVE));
  context.setAttribute(ExecutionContext.HTTP_REQUEST,request);
  BasicHttpResponse response=new BasicHttpResponse(HttpVersion.HTTP_1_0,HttpStatus.SC_OK,"OK");
  BasicHttpEntity entity=new BasicHttpEntity();
  response.setEntity(entity);
  ResponseConnControl interceptor=new ResponseConnControl();
  interceptor.process(response,context);
  Header header=response.getFirstHeader(HTTP.CONN_DIRECTIVE);
  Assert.assertNotNull(header);
  Assert.assertEquals(HTTP.CONN_CLOSE,header.getValue());
}
