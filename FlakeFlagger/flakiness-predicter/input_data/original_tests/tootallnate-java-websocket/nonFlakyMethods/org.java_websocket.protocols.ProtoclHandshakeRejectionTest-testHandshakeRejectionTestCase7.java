@Test(timeout=5000) public void testHandshakeRejectionTestCase7() throws Exception {
  testProtocolRejection(7,new Draft_6455(Collections.<IExtension>emptyList(),Collections.<IProtocol>singletonList(new Protocol("chat"))));
}
