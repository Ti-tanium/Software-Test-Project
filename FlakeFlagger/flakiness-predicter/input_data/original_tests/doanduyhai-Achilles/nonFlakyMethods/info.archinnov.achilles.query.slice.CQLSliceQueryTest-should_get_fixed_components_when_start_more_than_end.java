@Test public void should_get_fixed_components_when_start_more_than_end() throws Exception {
  when(sliceQuery.getClusteringsFrom()).thenReturn(Arrays.<Object>asList(11L,"a",11.0));
  when(sliceQuery.getClusteringsTo()).thenReturn(Arrays.<Object>asList(11L,"a"));
  cqlSliceQuery=new CQLSliceQuery<ClusteredEntity>(sliceQuery,EACH_QUORUM);
  assertThat(cqlSliceQuery.getFixedComponents()).containsExactly(11L,"a");
}
