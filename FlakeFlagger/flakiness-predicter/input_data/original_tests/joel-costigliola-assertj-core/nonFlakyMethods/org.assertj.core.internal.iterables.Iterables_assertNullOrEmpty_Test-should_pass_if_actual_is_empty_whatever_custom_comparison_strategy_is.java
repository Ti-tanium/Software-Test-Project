@Test public void should_pass_if_actual_is_empty_whatever_custom_comparison_strategy_is(){
  iterablesWithCaseInsensitiveComparisonStrategy.assertNullOrEmpty(someInfo(),emptyList());
}
