@Test public void should_pass_if_actual_contains_given_values_only_even_if_duplicated_according_to_custom_comparison_strategy(){
  arraysWithCustomComparisonStrategy.assertContainsOnly(someInfo(),actual,arrayOf(6L,-8L,10L,6L,-8L,10L));
}
