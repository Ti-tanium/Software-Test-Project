@Test public void should_iterate_with_default_params() throws Exception {
  long id=RandomUtils.nextLong();
  insertValues(id,5);
  Iterator<ClusteredEntityWithCompositePartitionKey> iter=manager.sliceQuery(ClusteredEntityWithCompositePartitionKey.class).partitionComponents(id,"type").iterator();
  assertThat(iter.hasNext()).isTrue();
  ClusteredEntityWithCompositePartitionKey next=iter.next();
  assertThat(next.getValue()).isEqualTo("value1");
  assertThat(next.getId().getId()).isEqualTo(id);
  assertThat(next.getId().getType()).isEqualTo("type");
  assertThat(next.getId().getIndexes()).isEqualTo(11);
  assertThat(iter.hasNext()).isTrue();
  assertThat(iter.hasNext()).isTrue();
  next=iter.next();
  assertThat(next.getValue()).isEqualTo("value2");
  assertThat(next.getId().getId()).isEqualTo(id);
  assertThat(next.getId().getType()).isEqualTo("type");
  assertThat(next.getId().getIndexes()).isEqualTo(12);
  assertThat(iter.hasNext()).isTrue();
  assertThat(iter.hasNext()).isTrue();
  next=iter.next();
  assertThat(next.getValue()).isEqualTo("value3");
  assertThat(next.getId().getId()).isEqualTo(id);
  assertThat(next.getId().getType()).isEqualTo("type");
  assertThat(next.getId().getIndexes()).isEqualTo(13);
  assertThat(iter.hasNext()).isTrue();
  assertThat(iter.hasNext()).isTrue();
  next=iter.next();
  assertThat(next.getValue()).isEqualTo("value4");
  assertThat(next.getId().getId()).isEqualTo(id);
  assertThat(next.getId().getType()).isEqualTo("type");
  assertThat(next.getId().getIndexes()).isEqualTo(14);
  assertThat(iter.hasNext()).isTrue();
  assertThat(iter.hasNext()).isTrue();
  next=iter.next();
  assertThat(next.getValue()).isEqualTo("value5");
  assertThat(next.getId().getId()).isEqualTo(id);
  assertThat(next.getId().getType()).isEqualTo("type");
  assertThat(next.getId().getIndexes()).isEqualTo(15);
  assertThat(iter.hasNext()).isFalse();
}
