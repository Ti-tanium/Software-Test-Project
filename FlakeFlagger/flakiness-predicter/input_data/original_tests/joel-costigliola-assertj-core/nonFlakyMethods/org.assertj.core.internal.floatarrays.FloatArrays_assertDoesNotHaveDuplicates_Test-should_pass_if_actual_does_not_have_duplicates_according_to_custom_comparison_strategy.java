@Test public void should_pass_if_actual_does_not_have_duplicates_according_to_custom_comparison_strategy(){
  arraysWithCustomComparisonStrategy.assertDoesNotHaveDuplicates(someInfo(),actual);
}
