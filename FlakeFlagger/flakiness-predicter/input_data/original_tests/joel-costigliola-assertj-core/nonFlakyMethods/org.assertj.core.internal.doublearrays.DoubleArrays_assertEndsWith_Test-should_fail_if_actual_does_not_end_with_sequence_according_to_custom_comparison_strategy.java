@Test public void should_fail_if_actual_does_not_end_with_sequence_according_to_custom_comparison_strategy(){
  AssertionInfo info=someInfo();
  double[] sequence={20d,22d};
  try {
    arraysWithCustomComparisonStrategy.assertEndsWith(info,actual,sequence);
  }
 catch (  AssertionError e) {
    verify(failures).failure(info,shouldEndWith(actual,sequence,absValueComparisonStrategy));
    return;
  }
  failBecauseExpectedAssertionErrorWasNotThrown();
}
