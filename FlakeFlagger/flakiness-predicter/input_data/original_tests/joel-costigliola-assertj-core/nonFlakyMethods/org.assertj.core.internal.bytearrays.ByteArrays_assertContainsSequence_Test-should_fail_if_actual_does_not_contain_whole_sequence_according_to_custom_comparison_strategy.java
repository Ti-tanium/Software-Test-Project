@Test public void should_fail_if_actual_does_not_contain_whole_sequence_according_to_custom_comparison_strategy(){
  AssertionInfo info=someInfo();
  byte[] sequence={6,20};
  try {
    arraysWithCustomComparisonStrategy.assertContainsSequence(info,actual,sequence);
  }
 catch (  AssertionError e) {
    verify(failures).failure(info,shouldContainSequence(actual,sequence,absValueComparisonStrategy));
    return;
  }
  failBecauseExpectedAssertionErrorWasNotThrown();
}
