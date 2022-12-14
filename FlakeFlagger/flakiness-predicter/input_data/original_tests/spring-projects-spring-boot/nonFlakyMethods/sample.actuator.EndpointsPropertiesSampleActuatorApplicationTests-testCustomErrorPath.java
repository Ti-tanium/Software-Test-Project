@Test public void testCustomErrorPath() throws Exception {
  @SuppressWarnings("rawtypes") ResponseEntity<Map> entity=new TestRestTemplate("user","password").getForEntity("http://localhost:" + this.port + "/oops",Map.class);
  assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,entity.getStatusCode());
  @SuppressWarnings("unchecked") Map<String,Object> body=entity.getBody();
  assertEquals("None",body.get("error"));
  assertEquals(999,body.get("status"));
}
