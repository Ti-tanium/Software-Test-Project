@Test public void testDuration(){
  doTest("BEGIN:VCALENDAR\r\nBEGIN:VEVENT\r\n" + "DTSTART:20080504T123456Z\r\n" + "DURATION:P1D\r\n"+ "END:VEVENT\r\nEND:VCALENDAR",null,null,null,"20080504T123456Z","20080505T123456Z");
  doTest("BEGIN:VCALENDAR\r\nBEGIN:VEVENT\r\n" + "DTSTART:20080504T123456Z\r\n" + "DURATION:P1DT2H3M4S\r\n"+ "END:VEVENT\r\nEND:VCALENDAR",null,null,null,"20080504T123456Z","20080505T143800Z");
}
