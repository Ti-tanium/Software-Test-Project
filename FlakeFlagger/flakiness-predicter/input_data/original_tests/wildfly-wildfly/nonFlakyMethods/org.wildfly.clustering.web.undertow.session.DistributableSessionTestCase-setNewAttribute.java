@Test public void setNewAttribute(){
  String name="name";
  Integer value=Integer.valueOf(1);
  this.validate(session -> session.setAttribute(name,value));
  SessionManager<LocalSessionContext,Batch> manager=mock(SessionManager.class);
  Batcher<Batch> batcher=mock(Batcher.class);
  BatchContext context=mock(BatchContext.class);
  SessionAttributes attributes=mock(SessionAttributes.class);
  SessionListener listener=mock(SessionListener.class);
  SessionListeners listeners=new SessionListeners();
  listeners.addSessionListener(listener);
  Object expected=null;
  when(this.session.getAttributes()).thenReturn(attributes);
  when(attributes.setAttribute(name,value)).thenReturn(expected);
  when(this.manager.getSessionListeners()).thenReturn(listeners);
  when(this.manager.getSessionManager()).thenReturn(manager);
  when(manager.getBatcher()).thenReturn(batcher);
  when(batcher.resumeBatch(this.batch)).thenReturn(context);
  Object result=this.adapter.setAttribute(name,value);
  assertSame(expected,result);
  verify(listener).attributeAdded(this.adapter,name,value);
  verify(listener,never()).attributeUpdated(same(this.adapter),same(name),same(value),any());
  verify(listener,never()).attributeRemoved(same(this.adapter),same(name),any());
  verify(context).close();
}
