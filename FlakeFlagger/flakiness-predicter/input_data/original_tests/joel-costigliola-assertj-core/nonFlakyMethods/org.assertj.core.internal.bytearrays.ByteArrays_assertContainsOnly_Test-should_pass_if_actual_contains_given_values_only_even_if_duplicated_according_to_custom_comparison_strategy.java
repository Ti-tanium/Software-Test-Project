@Test public void should_pass_if_actual_contains_given_values_only_even_if_duplicated_according_to_custom_comparison_strategy(){
  arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(),actual,arrayOf(6,-8,10,6,-8,10));
}
