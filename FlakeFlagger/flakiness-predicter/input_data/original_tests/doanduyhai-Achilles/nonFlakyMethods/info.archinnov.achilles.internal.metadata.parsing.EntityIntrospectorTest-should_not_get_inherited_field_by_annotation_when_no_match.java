@Test public void should_not_get_inherited_field_by_annotation_when_no_match() throws Exception {
  assertThat(introspector.getInheritedPrivateFields(ChildBean.class,TimeUUID.class)).isNull();
}
