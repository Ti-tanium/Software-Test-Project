@Test public void varyMatchesAbsentRequestHeaderField() throws Exception {
  server.enqueue(new MockResponse().addHeader("Cache-Control: max-age=60").addHeader("Vary: Foo").setBody("A"));
  server.enqueue(new MockResponse().setBody("B"));
  server.play();
  assertEquals("A",readAscii(openConnection(server.getUrl("/"))));
  assertEquals("A",readAscii(openConnection(server.getUrl("/"))));
}
