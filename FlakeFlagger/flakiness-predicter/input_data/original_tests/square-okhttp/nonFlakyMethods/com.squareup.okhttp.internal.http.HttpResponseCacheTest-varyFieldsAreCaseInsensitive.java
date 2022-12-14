@Test public void varyFieldsAreCaseInsensitive() throws Exception {
  server.enqueue(new MockResponse().addHeader("Cache-Control: max-age=60").addHeader("Vary: ACCEPT-LANGUAGE").setBody("A"));
  server.enqueue(new MockResponse().setBody("B"));
  server.play();
  URL url=server.getUrl("/");
  URLConnection connection1=openConnection(url);
  connection1.addRequestProperty("Accept-Language","fr-CA");
  assertEquals("A",readAscii(connection1));
  URLConnection connection2=openConnection(url);
  connection2.addRequestProperty("accept-language","fr-CA");
  assertEquals("A",readAscii(connection2));
}
