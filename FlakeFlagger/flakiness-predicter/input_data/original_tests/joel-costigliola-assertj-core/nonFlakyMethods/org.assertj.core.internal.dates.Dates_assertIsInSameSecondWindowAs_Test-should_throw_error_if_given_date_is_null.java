@Test public void should_throw_error_if_given_date_is_null(){
  thrown.expectNullPointerException(dateToCompareActualWithIsNull());
  dates.assertIsInSameSecondWindowAs(someInfo(),actual,null);
}
