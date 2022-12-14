@Test public void testMMSTO(){
  doTestResult("MMSTO:+15551212","+15551212",ParsedResultType.SMS);
  doTestResult("mmsto:+15551212","+15551212",ParsedResultType.SMS);
  doTestResult("mmsto:+15551212:subject","+15551212\nsubject",ParsedResultType.SMS);
  doTestResult("mmsto:+15551212:My message","+15551212\nMy message",ParsedResultType.SMS);
  doTestResult("mmsto:+15551212:What's up?","+15551212\nWhat's up?",ParsedResultType.SMS);
  doTestResult("mmsto:+15551212:Directions: Do this","+15551212\nDirections: Do this",ParsedResultType.SMS);
  doTestResult("mmsto:212-555-1212:Here's a longer message. Should be fine.","212-555-1212\nHere's a longer message. Should be fine.",ParsedResultType.SMS);
}
