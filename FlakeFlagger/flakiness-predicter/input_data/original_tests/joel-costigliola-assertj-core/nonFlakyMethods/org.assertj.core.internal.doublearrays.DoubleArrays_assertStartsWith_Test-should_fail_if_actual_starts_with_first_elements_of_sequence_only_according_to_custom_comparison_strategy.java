@Test public void should_fail_if_actual_starts_with_first_elements_of_sequence_only_according_to_custom_comparison_strategy(){
  AssertionInfo info=someInfo();
  double[] sequence={6d,20d};
  try {
    arraysWithCustomComparisonStrategy.assertStartsWith(info,actual,sequence);
  }
 catch (  AssertionError e) {
    verify(failures).failure(info,shouldStartWith(actual,sequence,absValueComparisonStrategy));
    return;
  }
  failBecauseExpectedAssertionErrorWasNotThrown();
}
