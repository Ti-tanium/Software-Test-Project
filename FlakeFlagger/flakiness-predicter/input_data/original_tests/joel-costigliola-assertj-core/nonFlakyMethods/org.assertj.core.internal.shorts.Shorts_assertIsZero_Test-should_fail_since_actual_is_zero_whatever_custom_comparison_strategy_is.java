@Test public void should_fail_since_actual_is_zero_whatever_custom_comparison_strategy_is(){
  try {
    shortsWithAbsValueComparisonStrategy.assertIsNotZero(someInfo(),(short)0);
  }
 catch (  AssertionError e) {
    assertEquals(e.getMessage(),"\nExpecting:\n <0>\nnot to be equal to:\n <0>\n");
  }
}
