@Test public void sessionExpired(){
  Deployment deployment=mock(Deployment.class);
  UndertowSessionManager manager=mock(UndertowSessionManager.class);
  SessionManager<LocalSessionContext,Batch> delegateManager=mock(SessionManager.class);
  Batcher<Batch> batcher=mock(Batcher.class);
  Batch batch=mock(Batch.class);
  SessionListener listener=mock(SessionListener.class);
  ImmutableSession session=mock(ImmutableSession.class);
  ImmutableSessionAttributes attributes=mock(ImmutableSessionAttributes.class);
  ImmutableSessionMetaData metaData=mock(ImmutableSessionMetaData.class);
  ArgumentCaptor<Session> capturedSession=ArgumentCaptor.forClass(Session.class);
  String expectedSessionId="session";
  SessionListeners listeners=new SessionListeners();
  listeners.addSessionListener(listener);
  SessionExpirationListener expirationListener=new UndertowSessionExpirationListener(deployment,listeners);
  when(deployment.getSessionManager()).thenReturn(manager);
  when(manager.getSessionManager()).thenReturn(delegateManager);
  when(delegateManager.getBatcher()).thenReturn(batcher);
  when(batcher.suspendBatch()).thenReturn(batch);
  when(session.getId()).thenReturn(expectedSessionId);
  when(session.getAttributes()).thenReturn(attributes);
  when(attributes.getAttributeNames()).thenReturn(Collections.emptySet());
  when(session.getMetaData()).thenReturn(metaData);
  when(metaData.getCreationTime()).thenReturn(Instant.now());
  when(metaData.getLastAccessedTime()).thenReturn(Instant.now());
  when(metaData.getMaxInactiveInterval()).thenReturn(Duration.ZERO);
  expirationListener.sessionExpired(session);
  verify(batcher).suspendBatch();
  verify(listener).sessionDestroyed(capturedSession.capture(),isNull(),same(SessionListener.SessionDestroyedReason.TIMEOUT));
  verify(batcher).resumeBatch(batch);
  assertSame(expectedSessionId,capturedSession.getValue().getId());
  assertSame(manager,capturedSession.getValue().getSessionManager());
}
