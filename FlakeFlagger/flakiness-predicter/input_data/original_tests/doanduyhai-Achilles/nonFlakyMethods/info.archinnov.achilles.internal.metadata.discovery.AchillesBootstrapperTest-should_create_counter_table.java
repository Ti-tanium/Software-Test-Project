@Test public void should_create_counter_table() throws Exception {
  Map<Class<?>,EntityMeta> metas=ImmutableMap.<Class<?>,EntityMeta>of(UserBean.class,meta);
  Map<String,TableMetadata> tableMetaDatas=ImmutableMap.of();
  when(schemaContext.fetchTableMetaData()).thenReturn(tableMetaDatas);
  when(meta.getTableName()).thenReturn("UserBean");
  when(schemaContext.entityMetaEntrySet()).thenReturn(metas.entrySet());
  when(schemaContext.hasSimpleCounter()).thenReturn(true);
  bootstrapper.validateOrCreateTables(schemaContext);
  verify(schemaContext).createTableForCounter();
}
