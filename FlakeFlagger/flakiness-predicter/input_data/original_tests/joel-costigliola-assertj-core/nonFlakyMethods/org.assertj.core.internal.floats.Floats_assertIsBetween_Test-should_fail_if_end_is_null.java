@Test(expected=NullPointerException.class) public void should_fail_if_end_is_null(){
  floats.assertIsBetween(someInfo(),ONE,ZERO,null);
}
