@Test public void assertStart(){
  guaranteeListenerManager.start();
  verify(jobNodeStorage,times(2)).addDataListener(ArgumentMatchers.<AbstractJobListener>any());
}
