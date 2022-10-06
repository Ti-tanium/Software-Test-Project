@Test public void shouldRenameResourceWithSomeContent() throws Exception {
  final String result=namingStrategy.rename("anotherFile.js",new ByteArrayInputStream("someContent".getBytes()));
  assertEquals("99ef8ae827896f2af4032d5dab9298ec86309abf/anotherFile.js",result);
}
