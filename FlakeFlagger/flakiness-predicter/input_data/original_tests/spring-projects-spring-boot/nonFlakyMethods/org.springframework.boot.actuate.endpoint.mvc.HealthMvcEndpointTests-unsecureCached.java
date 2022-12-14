@Test public void unsecureCached(){
  given(this.endpoint.getTimeToLive()).willReturn(10000L);
  given(this.endpoint.invoke()).willReturn(new Health.Builder().up().withDetail("foo","bar").build());
  Object result=this.mvc.invoke(this.user);
  assertTrue(result instanceof Health);
  assertTrue(((Health)result).getStatus() == Status.UP);
  given(this.endpoint.invoke()).willReturn(new Health.Builder().down().build());
  result=this.mvc.invoke(null);
  Health health=(Health)result;
  assertTrue(health.getStatus() == Status.UP);
}
