@Test public void should_pass_if_actual_starts_with_sequence_according_to_custom_comparison_strategy(){
  arraysWithCustomComparisonStrategy.assertStartsWith(someInfo(),actual,arrayOf(6L,-8L,10L));
}
