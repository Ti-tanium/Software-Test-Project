@Test public void should_fail_if_actual_is_null(){
  thrown.expectAssertionError(actualIsNull());
  floats.assertLessThanOrEqualTo(someInfo(),null,8f);
}
