@Test public void should_pass_if_actual_is_instance_of_type(){
  objects.assertIsInstanceOf(someInfo(),actual,Person.class);
}
