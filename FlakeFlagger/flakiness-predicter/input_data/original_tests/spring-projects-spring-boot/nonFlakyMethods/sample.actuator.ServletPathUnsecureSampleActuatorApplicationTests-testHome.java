@Test public void testHome() throws Exception {
  @SuppressWarnings("rawtypes") ResponseEntity<Map> entity=new TestRestTemplate().getForEntity("http://localhost:" + this.port + "/spring/",Map.class);
  assertEquals(HttpStatus.OK,entity.getStatusCode());
  @SuppressWarnings("unchecked") Map<String,Object> body=entity.getBody();
  assertEquals("Hello Phil",body.get("message"));
  assertFalse("Wrong headers: " + entity.getHeaders(),entity.getHeaders().containsKey("Set-Cookie"));
}
