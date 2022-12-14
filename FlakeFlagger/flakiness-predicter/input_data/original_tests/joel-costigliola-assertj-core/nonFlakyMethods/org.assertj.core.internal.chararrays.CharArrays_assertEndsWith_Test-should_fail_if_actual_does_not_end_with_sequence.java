@Test public void should_fail_if_actual_does_not_end_with_sequence(){
  AssertionInfo info=someInfo();
  char[] sequence={'x','y','z'};
  try {
    arrays.assertEndsWith(info,actual,sequence);
  }
 catch (  AssertionError e) {
    verify(failures).failure(info,shouldEndWith(actual,sequence));
    return;
  }
  failBecauseExpectedAssertionErrorWasNotThrown();
}
