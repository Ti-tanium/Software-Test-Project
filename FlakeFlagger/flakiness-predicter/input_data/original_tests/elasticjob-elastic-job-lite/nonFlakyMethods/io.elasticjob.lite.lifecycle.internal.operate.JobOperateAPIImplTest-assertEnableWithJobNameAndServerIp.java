@Test public void assertEnableWithJobNameAndServerIp(){
  jobOperateAPI.enable(Optional.of("test_job"),Optional.of("localhost"));
  verify(regCenter).persist("/test_job/servers/localhost","");
}
