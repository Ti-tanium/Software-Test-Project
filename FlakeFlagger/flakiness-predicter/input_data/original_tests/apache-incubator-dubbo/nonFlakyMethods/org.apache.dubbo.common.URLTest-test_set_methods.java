@Test public void test_set_methods() throws Exception {
  URL url=URL.valueOf("dubbo://admin:hello1234@10.20.130.230:20880/context/path?version=1.0.0&application=morgan");
  url=url.setHost("host");
  assertEquals("dubbo",url.getProtocol());
  assertEquals("admin",url.getUsername());
  assertEquals("hello1234",url.getPassword());
  assertEquals("host",url.getHost());
  assertEquals(20880,url.getPort());
  assertEquals("context/path",url.getPath());
  assertEquals(2,url.getParameters().size());
  assertEquals("1.0.0",url.getParameter("version"));
  assertEquals("morgan",url.getParameter("application"));
  url=url.setPort(1);
  assertEquals("dubbo",url.getProtocol());
  assertEquals("admin",url.getUsername());
  assertEquals("hello1234",url.getPassword());
  assertEquals("host",url.getHost());
  assertEquals(1,url.getPort());
  assertEquals("context/path",url.getPath());
  assertEquals(2,url.getParameters().size());
  assertEquals("1.0.0",url.getParameter("version"));
  assertEquals("morgan",url.getParameter("application"));
  url=url.setPath("path");
  assertEquals("dubbo",url.getProtocol());
  assertEquals("admin",url.getUsername());
  assertEquals("hello1234",url.getPassword());
  assertEquals("host",url.getHost());
  assertEquals(1,url.getPort());
  assertEquals("path",url.getPath());
  assertEquals(2,url.getParameters().size());
  assertEquals("1.0.0",url.getParameter("version"));
  assertEquals("morgan",url.getParameter("application"));
  url=url.setProtocol("protocol");
  assertEquals("protocol",url.getProtocol());
  assertEquals("admin",url.getUsername());
  assertEquals("hello1234",url.getPassword());
  assertEquals("host",url.getHost());
  assertEquals(1,url.getPort());
  assertEquals("path",url.getPath());
  assertEquals(2,url.getParameters().size());
  assertEquals("1.0.0",url.getParameter("version"));
  assertEquals("morgan",url.getParameter("application"));
  url=url.setUsername("username");
  assertEquals("protocol",url.getProtocol());
  assertEquals("username",url.getUsername());
  assertEquals("hello1234",url.getPassword());
  assertEquals("host",url.getHost());
  assertEquals(1,url.getPort());
  assertEquals("path",url.getPath());
  assertEquals(2,url.getParameters().size());
  assertEquals("1.0.0",url.getParameter("version"));
  assertEquals("morgan",url.getParameter("application"));
  url=url.setPassword("password");
  assertEquals("protocol",url.getProtocol());
  assertEquals("username",url.getUsername());
  assertEquals("password",url.getPassword());
  assertEquals("host",url.getHost());
  assertEquals(1,url.getPort());
  assertEquals("path",url.getPath());
  assertEquals(2,url.getParameters().size());
  assertEquals("1.0.0",url.getParameter("version"));
  assertEquals("morgan",url.getParameter("application"));
}
