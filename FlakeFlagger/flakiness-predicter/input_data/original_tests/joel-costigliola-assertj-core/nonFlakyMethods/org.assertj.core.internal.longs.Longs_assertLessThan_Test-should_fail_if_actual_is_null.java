@Test public void should_fail_if_actual_is_null(){
  thrown.expectAssertionError(actualIsNull());
  longs.assertLessThan(someInfo(),null,8L);
}
