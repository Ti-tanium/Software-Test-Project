@Test public void should_fail_if_root_cause_is_not_instance_of_expected_type() throws Exception {
  AssertionInfo info=someInfo();
  Class<NullPointerException> expectedCauseType=NullPointerException.class;
  try {
    throwables.assertHasRootCauseExactlyInstanceOf(info,throwableWithCause,expectedCauseType);
  }
 catch (  AssertionError err) {
    verify(failures).failure(info,shouldHaveRootCauseExactlyInstance(throwableWithCause,expectedCauseType));
    return;
  }
  failBecauseExpectedAssertionErrorWasNotThrown();
}
