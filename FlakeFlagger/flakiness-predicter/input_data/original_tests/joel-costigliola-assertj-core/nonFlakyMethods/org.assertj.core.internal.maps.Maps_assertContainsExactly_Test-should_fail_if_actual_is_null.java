@SuppressWarnings("unchecked") @Test public void should_fail_if_actual_is_null() throws Exception {
  thrown.expectAssertionError(actualIsNull());
  maps.assertContainsExactly(someInfo(),null,entry("name","Yoda"));
}
