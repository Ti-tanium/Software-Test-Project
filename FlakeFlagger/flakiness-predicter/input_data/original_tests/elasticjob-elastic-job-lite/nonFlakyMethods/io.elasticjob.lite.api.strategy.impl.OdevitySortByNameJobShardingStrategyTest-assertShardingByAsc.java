@Test public void assertShardingByAsc(){
  Map<JobInstance,List<Integer>> expected=new HashMap<>();
  expected.put(new JobInstance("host0@-@0"),Collections.singletonList(0));
  expected.put(new JobInstance("host1@-@0"),Collections.singletonList(1));
  expected.put(new JobInstance("host2@-@0"),Collections.<Integer>emptyList());
  assertThat(odevitySortByNameJobShardingStrategy.sharding(Arrays.asList(new JobInstance("host0@-@0"),new JobInstance("host1@-@0"),new JobInstance("host2@-@0")),"1",2),is(expected));
}
