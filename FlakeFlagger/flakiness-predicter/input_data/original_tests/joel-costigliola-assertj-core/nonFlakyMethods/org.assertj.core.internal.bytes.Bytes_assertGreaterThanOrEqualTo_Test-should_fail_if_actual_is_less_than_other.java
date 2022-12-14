@Test public void should_fail_if_actual_is_less_than_other(){
  AssertionInfo info=someInfo();
  try {
    bytes.assertGreaterThanOrEqualTo(info,(byte)6,(byte)8);
  }
 catch (  AssertionError e) {
    verify(failures).failure(info,shouldBeGreaterOrEqual((byte)6,(byte)8));
    return;
  }
  failBecauseExpectedAssertionErrorWasNotThrown();
}
