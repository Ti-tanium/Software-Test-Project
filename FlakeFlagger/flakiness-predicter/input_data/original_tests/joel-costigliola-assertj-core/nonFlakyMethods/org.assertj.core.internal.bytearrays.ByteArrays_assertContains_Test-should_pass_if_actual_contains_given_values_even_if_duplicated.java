@Test public void should_pass_if_actual_contains_given_values_even_if_duplicated(){
  arrays.assertContains(someInfo(),actual,arrayOf(6,6));
}
