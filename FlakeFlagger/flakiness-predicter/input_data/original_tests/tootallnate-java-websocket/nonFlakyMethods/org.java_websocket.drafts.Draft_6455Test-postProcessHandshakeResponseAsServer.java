@Test public void postProcessHandshakeResponseAsServer() throws Exception {
  Draft_6455 draft_6455=new Draft_6455();
  HandshakeImpl1Server response=new HandshakeImpl1Server();
  HandshakeImpl1Client request=new HandshakeImpl1Client();
  request.put("Sec-WebSocket-Key","dGhlIHNhbXBsZSBub25jZQ==");
  request.put("Connection","upgrade");
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertTrue(response.hasFieldValue("Date"));
  assertTrue(response.hasFieldValue("Sec-WebSocket-Accept"));
  assertEquals("Web Socket Protocol Handshake",response.getHttpStatusMessage());
  assertEquals("TooTallNate Java-WebSocket",response.getFieldValue("Server"));
  assertEquals("upgrade",response.getFieldValue("Connection"));
  assertEquals("websocket",response.getFieldValue("Upgrade"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Protocol"));
  response=new HandshakeImpl1Server();
  draft_6455.acceptHandshakeAsServer(handshakedata);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  response=new HandshakeImpl1Server();
  draft_6455.acceptHandshakeAsServer(handshakedataProtocol);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  response=new HandshakeImpl1Server();
  draft_6455.acceptHandshakeAsServer(handshakedataExtension);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  response=new HandshakeImpl1Server();
  draft_6455.acceptHandshakeAsServer(handshakedataProtocolExtension);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  response=new HandshakeImpl1Server();
  draft_6455=new Draft_6455(Collections.<IExtension>emptyList(),Collections.<IProtocol>singletonList(new Protocol("chat")));
  draft_6455.acceptHandshakeAsServer(handshakedataProtocol);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertEquals("chat",response.getFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  response=new HandshakeImpl1Server();
  draft_6455.reset();
  draft_6455.acceptHandshakeAsServer(handshakedataExtension);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  response=new HandshakeImpl1Server();
  draft_6455.reset();
  draft_6455.acceptHandshakeAsServer(handshakedataProtocolExtension);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertEquals("chat",response.getFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  ArrayList<IProtocol> protocols=new ArrayList<IProtocol>();
  protocols.add(new Protocol("test"));
  protocols.add(new Protocol("chat"));
  draft_6455=new Draft_6455(Collections.<IExtension>emptyList(),protocols);
  draft_6455.acceptHandshakeAsServer(handshakedataProtocol);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertEquals("test",response.getFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  response=new HandshakeImpl1Server();
  draft_6455.reset();
  draft_6455.acceptHandshakeAsServer(handshakedataExtension);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
  response=new HandshakeImpl1Server();
  draft_6455.reset();
  draft_6455.acceptHandshakeAsServer(handshakedataProtocolExtension);
  draft_6455.postProcessHandshakeResponseAsServer(request,response);
  assertEquals("test",response.getFieldValue("Sec-WebSocket-Protocol"));
  assertTrue(!response.hasFieldValue("Sec-WebSocket-Extensions"));
}
