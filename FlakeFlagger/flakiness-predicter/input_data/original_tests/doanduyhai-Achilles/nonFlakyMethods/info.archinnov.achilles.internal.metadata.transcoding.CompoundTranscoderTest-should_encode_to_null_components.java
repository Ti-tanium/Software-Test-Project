@Test public void should_encode_to_null_components() throws Exception {
  Method userIdGetter=EmbeddedKey.class.getDeclaredMethod("getUserId");
  Method nameGetter=EmbeddedKey.class.getDeclaredMethod("getName");
  PropertyMeta pm=PropertyMetaTestBuilder.valueClass(EmbeddedKey.class).type(EMBEDDED_ID).compClasses(Long.class,String.class).compGetters(userIdGetter,nameGetter).build();
  List<Object> actual=transcoder.encodeToComponents(pm,(Object)null);
  assertThat(actual).isEmpty();
}
