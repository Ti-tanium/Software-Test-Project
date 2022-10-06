@Test public void smoke() throws Exception {
  smtpAppender.setLayout(buildPatternLayout(loggerContext));
  smtpAppender.start();
  Logger logger=loggerContext.getLogger("test");
  logger.addAppender(smtpAppender);
  logger.debug("hello");
  logger.error("en error",new Exception("an exception"));
  waitUntilEmailIsSent();
  System.out.println("*** " + ((ThreadPoolExecutor)loggerContext.getExecutorService()).getCompletedTaskCount());
  List<WiserMessage> wiserMsgList=WISER.getMessages();
  assertNotNull(wiserMsgList);
  assertEquals(numberOfOldMessages + 1,wiserMsgList.size());
  WiserMessage wm=wiserMsgList.get(numberOfOldMessages);
  MimeMessage mm=wm.getMimeMessage();
  assertEquals(TEST_SUBJECT,mm.getSubject());
  MimeMultipart mp=(MimeMultipart)mm.getContent();
  String body=getBody(mp.getBodyPart(0));
  System.out.println("[" + body);
  assertTrue(body.startsWith(HEADER.trim()));
  assertTrue(body.endsWith(FOOTER.trim()));
}
