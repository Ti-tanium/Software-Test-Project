@Test public void should_throw_error_if_iterable_to_look_for_is_null_whatever_custom_comparison_strategy_is(){
  thrown.expectNullPointerException(iterableToLookForIsNull());
  arraysWithCustomComparisonStrategy.assertContainsAll(someInfo(),actual,null);
}
