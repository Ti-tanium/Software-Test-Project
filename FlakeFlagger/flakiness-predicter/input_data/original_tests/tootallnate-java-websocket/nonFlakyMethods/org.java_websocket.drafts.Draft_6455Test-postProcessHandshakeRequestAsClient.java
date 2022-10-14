@Test public void postProcessHandshakeRequestAsClient() throws Exception {
  Draft_6455 draft_6455=new Draft_6455();
  HandshakeImpl1Client request=new HandshakeImpl1Client();
  draft_6455.postProcessHandshakeRequestAsClient(request);
  assertEquals("websocket",request.getFieldValue("Upgrade"));
  assertEquals("Upgrade",request.getFieldValue("Connection"));
  assertEquals("13",request.getFieldValue("Sec-WebSocket-Version"));
  assertTrue(request.hasFieldValue("Sec-WebSocket-Key"));
  assertTrue(!request.hasFieldValue("Sec-WebSocket-Extensions"));
  assertTrue(!request.hasFieldValue("Sec-WebSocket-Protocol"));
  ArrayList<IProtocol> protocols=new ArrayList<IProtocol>();
  protocols.add(new Protocol("chat"));
  draft_6455=new Draft_6455(Collections.<IExtension>emptyList(),protocols);
  request=new HandshakeImpl1Client();
  draft_6455.postProcessHandshakeRequestAsClient(request);
  assertTrue(!request.hasFieldValue("Sec-WebSocket-Extensions"));
  assertEquals("chat",request.getFieldValue("Sec-WebSocket-Protocol"));
  protocols.add(new Protocol("chat2"));
  draft_6455=new Draft_6455(Collections.<IExtension>emptyList(),protocols);
  request=new HandshakeImpl1Client();
  draft_6455.postProcessHandshakeRequestAsClient(request);
  assertTrue(!request.hasFieldValue("Sec-WebSocket-Extensions"));
  assertEquals("chat, chat2",request.getFieldValue("Sec-WebSocket-Protocol"));
  protocols.clear();
  protocols.add(new Protocol(""));
  draft_6455=new Draft_6455(Collections.<IExtension>emptyList(),protocols);
  request=new HandshakeImpl1Client();
  draft_6455.postProcessHandshakeRequestAsClient(request);
  assertTrue(!request.hasFieldValue("Sec-WebSocket-Extensions"));
  assertTrue(!request.hasFieldValue("Sec-WebSocket-Protocol"));
}