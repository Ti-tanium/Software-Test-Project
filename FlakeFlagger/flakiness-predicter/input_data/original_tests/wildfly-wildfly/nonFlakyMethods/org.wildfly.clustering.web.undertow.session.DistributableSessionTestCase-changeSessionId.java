@Test public void changeSessionId(){
  HttpServerExchange exchange=new HttpServerExchange(null);
  SessionConfig config=mock(SessionConfig.class);
  this.validate(exchange,session -> session.changeSessionId(exchange,config));
  SessionManager<LocalSessionContext,Batch> manager=mock(SessionManager.class);
  Batcher<Batch> batcher=mock(Batcher.class);
  BatchContext context=mock(BatchContext.class);
  Session<LocalSessionContext> session=mock(Session.class);
  SessionAttributes oldAttributes=mock(SessionAttributes.class);
  SessionAttributes newAttributes=mock(SessionAttributes.class);
  SessionMetaData oldMetaData=mock(SessionMetaData.class);
  SessionMetaData newMetaData=mock(SessionMetaData.class);
  LocalSessionContext oldContext=mock(LocalSessionContext.class);
  LocalSessionContext newContext=mock(LocalSessionContext.class);
  SessionListener listener=mock(SessionListener.class);
  SessionListeners listeners=new SessionListeners();
  listeners.addSessionListener(listener);
  String oldSessionId="old";
  String newSessionId="new";
  String name="name";
  Object value=new Object();
  Instant now=Instant.now();
  Duration interval=Duration.ofSeconds(10L);
  AuthenticatedSession authenticatedSession=new AuthenticatedSession(null,null);
  when(this.manager.getSessionManager()).thenReturn(manager);
  when(manager.getBatcher()).thenReturn(batcher);
  when(batcher.resumeBatch(this.batch)).thenReturn(context);
  when(manager.createIdentifier()).thenReturn(newSessionId);
  when(manager.createSession(newSessionId)).thenReturn(session);
  when(this.session.getAttributes()).thenReturn(oldAttributes);
  when(this.session.getMetaData()).thenReturn(oldMetaData);
  when(session.getAttributes()).thenReturn(newAttributes);
  when(session.getMetaData()).thenReturn(newMetaData);
  when(oldAttributes.getAttributeNames()).thenReturn(Collections.singleton(name));
  when(oldAttributes.getAttribute(name)).thenReturn(value);
  when(newAttributes.setAttribute(name,value)).thenReturn(null);
  when(oldMetaData.getLastAccessedTime()).thenReturn(now);
  when(oldMetaData.getMaxInactiveInterval()).thenReturn(interval);
  when(this.session.getId()).thenReturn(oldSessionId);
  when(session.getId()).thenReturn(newSessionId);
  when(this.session.getLocalContext()).thenReturn(oldContext);
  when(session.getLocalContext()).thenReturn(newContext);
  when(oldContext.getAuthenticatedSession()).thenReturn(authenticatedSession);
  when(this.manager.getSessionListeners()).thenReturn(listeners);
  String result=this.adapter.changeSessionId(exchange,config);
  assertSame(newSessionId,result);
  verify(newMetaData).setLastAccessedTime(now);
  verify(newMetaData).setMaxInactiveInterval(interval);
  verify(config).setSessionId(exchange,newSessionId);
  verify(newContext).setAuthenticatedSession(same(authenticatedSession));
  verify(listener).sessionIdChanged(this.adapter,oldSessionId);
  verify(context).close();
}
