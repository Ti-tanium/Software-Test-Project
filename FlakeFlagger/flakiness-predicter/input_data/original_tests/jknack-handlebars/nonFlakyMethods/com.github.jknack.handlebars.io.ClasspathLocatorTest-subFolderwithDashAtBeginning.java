@Test public void subFolderwithDashAtBeginning() throws IOException {
  TemplateLoader locator=new ClassPathTemplateLoader();
  locator.setSuffix(".yml");
  Reader reader=locator.load(URI.create("/mustache/specs/comments"));
  assertNotNull(reader);
  reader.close();
}
