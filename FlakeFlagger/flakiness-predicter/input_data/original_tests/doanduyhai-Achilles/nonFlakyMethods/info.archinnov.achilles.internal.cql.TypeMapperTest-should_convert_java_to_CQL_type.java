@Test public void should_convert_java_to_CQL_type() throws Exception {
  assertThat(toCQLType(String.class)).isSameAs(TEXT);
  assertThat(toCQLType(Long.class)).isSameAs(BIGINT);
  assertThat(toCQLType(long.class)).isSameAs(BIGINT);
  assertThat(toCQLType(ByteBuffer.class)).isSameAs(BLOB);
  assertThat(toCQLType(Boolean.class)).isSameAs(BOOLEAN);
  assertThat(toCQLType(boolean.class)).isSameAs(BOOLEAN);
  assertThat(toCQLType(BigDecimal.class)).isSameAs(DECIMAL);
  assertThat(toCQLType(Double.class)).isSameAs(DOUBLE);
  assertThat(toCQLType(double.class)).isSameAs(DOUBLE);
  assertThat(toCQLType(Float.class)).isSameAs(FLOAT);
  assertThat(toCQLType(float.class)).isSameAs(FLOAT);
  assertThat(toCQLType(InetAddress.class)).isSameAs(INET);
  assertThat(toCQLType(Integer.class)).isSameAs(INT);
  assertThat(toCQLType(int.class)).isSameAs(INT);
  assertThat(toCQLType(BigInteger.class)).isSameAs(VARINT);
  assertThat(toCQLType(Date.class)).isSameAs(TIMESTAMP);
  assertThat(toCQLType(UUID.class)).isSameAs(UUID);
  assertThat(toCQLType(List.class)).isSameAs(LIST);
  assertThat(toCQLType(Set.class)).isSameAs(SET);
  assertThat(toCQLType(Map.class)).isSameAs(MAP);
  assertThat(toCQLType(Object.class)).isSameAs(TEXT);
  assertThat(toCQLType(UserBean.class)).isSameAs(TEXT);
  assertThat(toCQLType(Counter.class)).isSameAs(COUNTER);
  assertThat(toCQLType(InternalTimeUUID.class)).isSameAs(TIMEUUID);
}
