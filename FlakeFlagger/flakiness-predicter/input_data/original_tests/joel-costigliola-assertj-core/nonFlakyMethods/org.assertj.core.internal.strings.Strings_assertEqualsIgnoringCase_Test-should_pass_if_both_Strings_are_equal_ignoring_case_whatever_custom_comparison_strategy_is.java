@Test public void should_pass_if_both_Strings_are_equal_ignoring_case_whatever_custom_comparison_strategy_is(){
  stringsWithCaseInsensitiveComparisonStrategy.assertEqualsIgnoringCase(someInfo(),"Yoda","YODA");
}
