@Test public void should_succeed_since_actual_is_zero(){
  doubles.assertIsNotPositive(someInfo(),0d);
}
