@Test public void should_return_are_not_equal_if_second_Object_is_null_and_first_is_not(){
  assertFalse(Objects.areEqual("Yoda",null));
}
