@Test public void should_pass_if_doubles_are_equal(){
  doubles.assertEqual(someInfo(),new Double(8d),8d,offset(1d));
}
