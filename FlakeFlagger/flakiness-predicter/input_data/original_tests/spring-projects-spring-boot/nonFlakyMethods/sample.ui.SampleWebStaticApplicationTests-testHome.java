@Test public void testHome() throws Exception {
  ResponseEntity<String> entity=new TestRestTemplate().getForEntity("http://localhost:" + this.port,String.class);
  assertEquals(HttpStatus.OK,entity.getStatusCode());
  assertTrue("Wrong body (title doesn't match):\n" + entity.getBody(),entity.getBody().contains("<title>Static"));
}
