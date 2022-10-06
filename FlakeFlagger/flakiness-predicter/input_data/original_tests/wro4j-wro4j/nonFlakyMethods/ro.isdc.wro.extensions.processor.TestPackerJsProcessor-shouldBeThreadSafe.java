@Test public void shouldBeThreadSafe() throws Exception {
  final PackerJsProcessor processor=new PackerJsProcessor(){
    @Override protected void onException(    final WroRuntimeException e){
      throw e;
    }
  }
;
  final Callable<Void> task=new Callable<Void>(){
    public Void call(){
      try {
        processor.process(new StringReader("alert(1);"),new StringWriter());
      }
 catch (      final Exception e) {
        throw new RuntimeException(e);
      }
      return null;
    }
  }
;
  WroTestUtils.runConcurrently(task,20);
}
