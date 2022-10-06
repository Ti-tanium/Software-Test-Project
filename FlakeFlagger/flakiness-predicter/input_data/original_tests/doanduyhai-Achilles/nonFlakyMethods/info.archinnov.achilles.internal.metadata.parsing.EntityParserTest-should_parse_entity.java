@Test public void should_parse_entity() throws Exception {
  configContext.setEnableSchemaUpdate(true);
  initEntityParsingContext(Bean.class);
  EntityMeta meta=parser.parseEntity(entityContext);
  assertThat(meta.getClassName()).isEqualTo("info.archinnov.achilles.test.parser.entity.Bean");
  assertThat(meta.getTableName()).isEqualTo("Bean");
  assertThat(meta.getIdMeta().<Long>getValueClass()).isEqualTo(Long.class);
  assertThat(meta.getIdMeta().getPropertyName()).isEqualTo("id");
  assertThat(meta.<Long>getIdClass()).isEqualTo(Long.class);
  assertThat(meta.getPropertyMetas()).hasSize(8);
  PropertyMeta id=meta.getPropertyMetas().get("id");
  PropertyMeta name=meta.getPropertyMetas().get("name");
  PropertyMeta age=meta.getPropertyMetas().get("age_in_year");
  PropertyMeta friends=meta.getPropertyMetas().get("friends");
  PropertyMeta followers=meta.getPropertyMetas().get("followers");
  PropertyMeta preferences=meta.getPropertyMetas().get("preferences");
  PropertyMeta creator=meta.getPropertyMetas().get("creator");
  PropertyMeta count=meta.getPropertyMetas().get("count");
  assertThat(id).isNotNull();
  assertThat(name).isNotNull();
  assertThat(age).isNotNull();
  assertThat(friends).isNotNull();
  assertThat(followers).isNotNull();
  assertThat(preferences).isNotNull();
  assertThat(creator).isNotNull();
  assertThat(count).isNotNull();
  assertThat(id.getPropertyName()).isEqualTo("id");
  assertThat(id.<Long>getValueClass()).isEqualTo(Long.class);
  assertThat(id.type()).isEqualTo(ID);
  assertThat(id.getReadConsistencyLevel()).isEqualTo(ConsistencyLevel.ONE);
  assertThat(id.getWriteConsistencyLevel()).isEqualTo(ConsistencyLevel.ALL);
  assertThat(name.getPropertyName()).isEqualTo("name");
  assertThat(name.<String>getValueClass()).isEqualTo(String.class);
  assertThat(name.type()).isEqualTo(SIMPLE);
  assertThat(name.getReadConsistencyLevel()).isEqualTo(ConsistencyLevel.ONE);
  assertThat(name.getWriteConsistencyLevel()).isEqualTo(ConsistencyLevel.ALL);
  assertThat(age.getPropertyName()).isEqualTo("age_in_year");
  assertThat(age.<Long>getValueClass()).isEqualTo(Long.class);
  assertThat(age.type()).isEqualTo(SIMPLE);
  assertThat(age.getReadConsistencyLevel()).isEqualTo(ConsistencyLevel.ONE);
  assertThat(age.getWriteConsistencyLevel()).isEqualTo(ConsistencyLevel.ALL);
  assertThat(friends.getPropertyName()).isEqualTo("friends");
  assertThat(friends.<String>getValueClass()).isEqualTo(String.class);
  assertThat(friends.type()).isEqualTo(PropertyType.LIST);
  assertThat(friends.getReadConsistencyLevel()).isEqualTo(ConsistencyLevel.ONE);
  assertThat(friends.getWriteConsistencyLevel()).isEqualTo(ConsistencyLevel.ALL);
  assertThat(followers.getPropertyName()).isEqualTo("followers");
  assertThat(followers.<String>getValueClass()).isEqualTo(String.class);
  assertThat(followers.type()).isEqualTo(PropertyType.SET);
  assertThat(followers.getReadConsistencyLevel()).isEqualTo(ConsistencyLevel.ONE);
  assertThat(followers.getWriteConsistencyLevel()).isEqualTo(ConsistencyLevel.ALL);
  assertThat(preferences.getPropertyName()).isEqualTo("preferences");
  assertThat(preferences.<String>getValueClass()).isEqualTo(String.class);
  assertThat(preferences.type()).isEqualTo(PropertyType.MAP);
  assertThat(preferences.<Integer>getKeyClass()).isEqualTo(Integer.class);
  assertThat(preferences.getReadConsistencyLevel()).isEqualTo(ConsistencyLevel.ONE);
  assertThat(preferences.getWriteConsistencyLevel()).isEqualTo(ConsistencyLevel.ALL);
  assertThat(creator.getPropertyName()).isEqualTo("creator");
  assertThat(creator.<UserBean>getValueClass()).isEqualTo(UserBean.class);
  assertThat(creator.type()).isEqualTo(SIMPLE);
  assertThat(count.getPropertyName()).isEqualTo("count");
  assertThat(count.<Counter>getValueClass()).isEqualTo(Counter.class);
  assertThat(count.type()).isEqualTo(COUNTER);
  assertThat(meta.getReadConsistencyLevel()).isEqualTo(ConsistencyLevel.ONE);
  assertThat(meta.getWriteConsistencyLevel()).isEqualTo(ConsistencyLevel.ALL);
  assertThat(meta.getAllMetasExceptIdAndCounters()).hasSize(6).containsOnly(name,age,friends,followers,preferences,creator);
  assertThat(meta.getAllMetasExceptCounters()).hasSize(7).containsOnly(id,name,age,friends,followers,preferences,creator);
  assertThat(meta.getInsertStrategy()).isEqualTo(InsertStrategy.ALL_FIELDS);
  assertThat(meta.isSchemaUpdateEnabled()).isTrue();
}
