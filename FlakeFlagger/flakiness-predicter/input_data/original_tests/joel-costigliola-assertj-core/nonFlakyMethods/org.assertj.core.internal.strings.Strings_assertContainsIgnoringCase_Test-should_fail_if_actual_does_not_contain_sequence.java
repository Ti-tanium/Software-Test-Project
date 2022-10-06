@Test public void should_fail_if_actual_does_not_contain_sequence(){
  AssertionInfo info=someInfo();
  try {
    strings.assertContainsIgnoringCase(info,"Yoda","Luke");
  }
 catch (  AssertionError e) {
    verify(failures).failure(info,shouldContainIgnoringCase("Yoda","Luke"));
    return;
  }
  failBecauseExpectedAssertionErrorWasNotThrown();
}
