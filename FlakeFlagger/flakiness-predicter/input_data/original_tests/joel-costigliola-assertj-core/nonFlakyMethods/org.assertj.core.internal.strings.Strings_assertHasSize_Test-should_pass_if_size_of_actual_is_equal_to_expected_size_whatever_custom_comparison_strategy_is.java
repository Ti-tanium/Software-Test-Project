@Test public void should_pass_if_size_of_actual_is_equal_to_expected_size_whatever_custom_comparison_strategy_is(){
  stringsWithCaseInsensitiveComparisonStrategy.assertHasSize(someInfo(),"Han",3);
}
