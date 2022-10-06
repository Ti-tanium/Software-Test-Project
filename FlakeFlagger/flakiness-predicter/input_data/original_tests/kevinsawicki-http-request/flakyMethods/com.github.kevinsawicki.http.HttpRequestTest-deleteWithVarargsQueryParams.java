/** 
 * Verify DELETE with query parameters
 * @throws Exception
 */
@Test public void deleteWithVarargsQueryParams() throws Exception {
  final Map<String,String> outputParams=new HashMap<String,String>();
  final AtomicReference<String> method=new AtomicReference<String>();
  handler=new RequestHandler(){
    @Override public void handle(    Request request,    HttpServletResponse response){
      method.set(request.getMethod());
      outputParams.put("name",request.getParameter("name"));
      outputParams.put("number",request.getParameter("number"));
      response.setStatus(HTTP_OK);
    }
  }
;
  HttpRequest request=delete(url,false,"name","user","number","100");
  assertTrue(request.ok());
  assertEquals("DELETE",method.get());
  assertEquals("user",outputParams.get("name"));
  assertEquals("100",outputParams.get("number"));
}
