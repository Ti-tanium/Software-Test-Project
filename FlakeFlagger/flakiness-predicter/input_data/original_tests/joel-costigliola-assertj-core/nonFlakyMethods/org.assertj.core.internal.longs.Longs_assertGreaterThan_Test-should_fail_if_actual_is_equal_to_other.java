@Test public void should_fail_if_actual_is_equal_to_other(){
  AssertionInfo info=someInfo();
  try {
    longs.assertGreaterThan(info,6L,6L);
  }
 catch (  AssertionError e) {
    verify(failures).failure(info,shouldBeGreater(6L,6L));
    return;
  }
  failBecauseExpectedAssertionErrorWasNotThrown();
}
