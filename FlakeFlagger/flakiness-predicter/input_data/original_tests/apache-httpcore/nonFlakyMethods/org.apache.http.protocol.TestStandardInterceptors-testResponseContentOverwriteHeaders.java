@Test public void testResponseContentOverwriteHeaders() throws Exception {
  ResponseContent interceptor=new ResponseContent(true);
  HttpContext context=new BasicHttpContext(null);
  HttpResponse response=new BasicHttpResponse(HttpVersion.HTTP_1_1,HttpStatus.SC_OK,"OK");
  response.addHeader(new BasicHeader(HTTP.CONTENT_LEN,"10"));
  response.addHeader(new BasicHeader(HTTP.TRANSFER_ENCODING,"whatever"));
  interceptor.process(response,context);
  Assert.assertEquals("0",response.getFirstHeader(HTTP.CONTENT_LEN).getValue());
  Assert.assertEquals("whatever",response.getFirstHeader(HTTP.TRANSFER_ENCODING).getValue());
}
