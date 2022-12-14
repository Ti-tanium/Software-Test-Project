@Test public void testNVParseAllWithCursor(){
  HeaderValueParser parser=BasicHeaderValueParser.DEFAULT;
  String s="test; test1 =  stuff   ; test2 =  \"stuff; stuff\"; test3=\"stuff";
  CharArrayBuffer buffer=new CharArrayBuffer(16);
  buffer.append(s);
  ParserCursor cursor=new ParserCursor(0,s.length());
  NameValuePair[] params=parser.parseParameters(buffer,cursor);
  Assert.assertEquals("test",params[0].getName());
  Assert.assertEquals(null,params[0].getValue());
  Assert.assertEquals("test1",params[1].getName());
  Assert.assertEquals("stuff",params[1].getValue());
  Assert.assertEquals("test2",params[2].getName());
  Assert.assertEquals("stuff; stuff",params[2].getValue());
  Assert.assertEquals("test3",params[3].getName());
  Assert.assertEquals("\"stuff",params[3].getValue());
  Assert.assertEquals(s.length(),cursor.getPos());
  Assert.assertTrue(cursor.atEnd());
  s="test; test1 =  stuff   ; test2 =  \"stuff; stuff\"; test3=\"stuff\",123";
  buffer=new CharArrayBuffer(16);
  buffer.append(s);
  cursor=new ParserCursor(0,s.length());
  params=parser.parseParameters(buffer,cursor);
  Assert.assertEquals("test",params[0].getName());
  Assert.assertEquals(null,params[0].getValue());
  Assert.assertEquals("test1",params[1].getName());
  Assert.assertEquals("stuff",params[1].getValue());
  Assert.assertEquals("test2",params[2].getName());
  Assert.assertEquals("stuff; stuff",params[2].getValue());
  Assert.assertEquals("test3",params[3].getName());
  Assert.assertEquals("stuff",params[3].getValue());
  Assert.assertEquals(s.length() - 3,cursor.getPos());
  Assert.assertFalse(cursor.atEnd());
  s="  ";
  buffer=new CharArrayBuffer(16);
  buffer.append(s);
  cursor=new ParserCursor(0,s.length());
  params=parser.parseParameters(buffer,cursor);
  Assert.assertEquals(0,params.length);
}
