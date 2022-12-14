@Test public void should_query_with_default_params() throws Exception {
  long partitionKey=RandomUtils.nextLong();
  List<ClusteredEntity> entities=manager.sliceQuery(ClusteredEntity.class).partitionComponents(partitionKey).fromClusterings(1,"name2").toClusterings(1,"name4").get();
  assertThat(entities).isEmpty();
  String clusteredValuePrefix=insertValues(partitionKey,1,5);
  entities=manager.sliceQuery(ClusteredEntity.class).partitionComponents(partitionKey).fromClusterings(1,"name2").toClusterings(1,"name4").get();
  assertThat(entities).hasSize(3);
  assertThat(entities.get(0).getValue()).isEqualTo(clusteredValuePrefix + 2);
  assertThat(entities.get(0).getId().getId()).isEqualTo(partitionKey);
  assertThat(entities.get(0).getId().getCount()).isEqualTo(1);
  assertThat(entities.get(0).getId().getName()).isEqualTo("name2");
  assertThat(entities.get(1).getValue()).isEqualTo(clusteredValuePrefix + 3);
  assertThat(entities.get(1).getId().getId()).isEqualTo(partitionKey);
  assertThat(entities.get(1).getId().getCount()).isEqualTo(1);
  assertThat(entities.get(1).getId().getName()).isEqualTo("name3");
  assertThat(entities.get(2).getValue()).isEqualTo(clusteredValuePrefix + 4);
  assertThat(entities.get(2).getId().getId()).isEqualTo(partitionKey);
  assertThat(entities.get(2).getId().getCount()).isEqualTo(1);
  assertThat(entities.get(2).getId().getName()).isEqualTo("name4");
  entities=manager.sliceQuery(ClusteredEntity.class).fromEmbeddedId(new ClusteredKey(partitionKey,1,"name2")).toEmbeddedId(new ClusteredKey(partitionKey,1,"name4")).get();
  assertThat(entities).hasSize(3);
  assertThat(entities.get(0).getValue()).isEqualTo(clusteredValuePrefix + 2);
  assertThat(entities.get(0).getId().getId()).isEqualTo(partitionKey);
  assertThat(entities.get(0).getId().getCount()).isEqualTo(1);
  assertThat(entities.get(0).getId().getName()).isEqualTo("name2");
  assertThat(entities.get(1).getValue()).isEqualTo(clusteredValuePrefix + 3);
  assertThat(entities.get(1).getId().getId()).isEqualTo(partitionKey);
  assertThat(entities.get(1).getId().getCount()).isEqualTo(1);
  assertThat(entities.get(1).getId().getName()).isEqualTo("name3");
  assertThat(entities.get(2).getValue()).isEqualTo(clusteredValuePrefix + 4);
  assertThat(entities.get(2).getId().getId()).isEqualTo(partitionKey);
  assertThat(entities.get(2).getId().getCount()).isEqualTo(1);
  assertThat(entities.get(2).getId().getName()).isEqualTo("name4");
}
