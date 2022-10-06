@Deployment public void testVariableUpdates(){
  Map<String,Object> variables=new HashMap<String,Object>();
  variables.put("number","one");
  variables.put("character","a");
  variables.put("bytes",":-(".getBytes());
  ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("receiveTask",variables);
  runtimeService.setVariable(processInstance.getId(),"number","two");
  runtimeService.setVariable(processInstance.getId(),"bytes",":-)".getBytes());
  HistoricActivityInstance historicStartEvent=historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId("theStart").singleResult();
  assertNotNull(historicStartEvent);
  HistoricActivityInstance waitStateActivity=historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId("waitState").singleResult();
  assertNotNull(waitStateActivity);
  HistoricActivityInstance serviceTaskActivity=historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId("serviceTask").singleResult();
  assertNotNull(serviceTaskActivity);
  List<HistoricDetail> historicDetails=historyService.createHistoricDetailQuery().orderByVariableName().asc().orderByVariableRevision().asc().list();
  assertEquals(10,historicDetails.size());
  HistoricVariableUpdate historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(0);
  assertEquals("bytes",historicVariableUpdate.getVariableName());
  assertEquals(":-(",new String((byte[])historicVariableUpdate.getValue()));
  assertEquals(0,historicVariableUpdate.getRevision());
  assertNull(historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(1);
  assertEquals("bytes",historicVariableUpdate.getVariableName());
  assertEquals(":-)",new String((byte[])historicVariableUpdate.getValue()));
  assertEquals(1,historicVariableUpdate.getRevision());
  assertNull(historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(2);
  assertEquals("character",historicVariableUpdate.getVariableName());
  assertEquals("a",historicVariableUpdate.getValue());
  assertEquals(0,historicVariableUpdate.getRevision());
  assertNull(historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(3);
  assertEquals("number",historicVariableUpdate.getVariableName());
  assertEquals("one",historicVariableUpdate.getValue());
  assertEquals(0,historicVariableUpdate.getRevision());
  assertNull(historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(4);
  assertEquals("number",historicVariableUpdate.getVariableName());
  assertEquals("two",historicVariableUpdate.getValue());
  assertEquals(1,historicVariableUpdate.getRevision());
  assertNull(historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(5);
  assertEquals("zVar1",historicVariableUpdate.getVariableName());
  assertEquals("Event: start",historicVariableUpdate.getValue());
  assertEquals(0,historicVariableUpdate.getRevision());
  assertNull(historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(6);
  assertEquals("zVar2",historicVariableUpdate.getVariableName());
  assertEquals("Event: take",historicVariableUpdate.getValue());
  assertEquals(0,historicVariableUpdate.getRevision());
  assertNull(historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(7);
  assertEquals("zVar3",historicVariableUpdate.getVariableName());
  assertEquals("Event: start",historicVariableUpdate.getValue());
  assertEquals(0,historicVariableUpdate.getRevision());
  assertEquals(serviceTaskActivity.getId(),historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(8);
  assertEquals("zVar4",historicVariableUpdate.getVariableName());
  assertEquals("Event: end",historicVariableUpdate.getValue());
  assertEquals(0,historicVariableUpdate.getRevision());
  assertEquals(serviceTaskActivity.getId(),historicVariableUpdate.getActivityInstanceId());
  historicVariableUpdate=(HistoricVariableUpdate)historicDetails.get(9);
  assertEquals("zzz",historicVariableUpdate.getVariableName());
  assertEquals(123456789L,historicVariableUpdate.getValue());
  assertEquals(0,historicVariableUpdate.getRevision());
  assertEquals(serviceTaskActivity.getId(),historicVariableUpdate.getActivityInstanceId());
  runtimeService.trigger(runtimeService.createExecutionQuery().activityId("waitState").singleResult().getId());
  assertProcessEnded(processInstance.getId());
  HistoricVariableInstanceQuery historicProcessVariableQuery=historyService.createHistoricVariableInstanceQuery().orderByVariableName().asc();
  assertEquals(8,historicProcessVariableQuery.count());
  List<HistoricVariableInstance> historicVariables=historicProcessVariableQuery.list();
  HistoricVariableInstance historicVariable=historicVariables.get(0);
  assertEquals("bytes",historicVariable.getVariableName());
  assertEquals(":-)",new String((byte[])historicVariable.getValue()));
  assertNotNull(historicVariable.getCreateTime());
  assertNotNull(historicVariable.getLastUpdatedTime());
  historicVariable=historicVariables.get(1);
  assertEquals("character",historicVariable.getVariableName());
  assertEquals("a",historicVariable.getValue());
  assertNotNull(historicVariable.getCreateTime());
  assertNotNull(historicVariable.getLastUpdatedTime());
  historicVariable=historicVariables.get(2);
  assertEquals("number",historicVariable.getVariableName());
  assertEquals("two",historicVariable.getValue());
  assertNotNull(historicVariable.getCreateTime());
  assertNotNull(historicVariable.getLastUpdatedTime());
  assertNotSame(historicVariable.getCreateTime(),historicVariable.getLastUpdatedTime());
  historicVariable=historicVariables.get(3);
  assertEquals("zVar1",historicVariable.getVariableName());
  assertEquals("Event: start",historicVariable.getValue());
  assertNotNull(historicVariable.getCreateTime());
  assertNotNull(historicVariable.getLastUpdatedTime());
  historicVariable=historicVariables.get(4);
  assertEquals("zVar2",historicVariable.getVariableName());
  assertEquals("Event: take",historicVariable.getValue());
  assertNotNull(historicVariable.getCreateTime());
  assertNotNull(historicVariable.getLastUpdatedTime());
  historicVariable=historicVariables.get(5);
  assertEquals("zVar3",historicVariable.getVariableName());
  assertEquals("Event: start",historicVariable.getValue());
  assertNotNull(historicVariable.getCreateTime());
  assertNotNull(historicVariable.getLastUpdatedTime());
  historicVariable=historicVariables.get(6);
  assertEquals("zVar4",historicVariable.getVariableName());
  assertEquals("Event: end",historicVariable.getValue());
  assertNotNull(historicVariable.getCreateTime());
  assertNotNull(historicVariable.getLastUpdatedTime());
  historicVariable=historicVariables.get(7);
  assertEquals("zzz",historicVariable.getVariableName());
  assertEquals(123456789L,historicVariable.getValue());
  assertNotNull(historicVariable.getCreateTime());
  assertNotNull(historicVariable.getLastUpdatedTime());
  historicVariable=historyService.createHistoricVariableInstanceQuery().variableValueLike("number","tw%").singleResult();
  assertNotNull(historicVariable);
  assertEquals("number",historicVariable.getVariableName());
  assertEquals("two",historicVariable.getValue());
  historicVariable=historyService.createHistoricVariableInstanceQuery().variableValueLikeIgnoreCase("number","TW%").singleResult();
  assertNotNull(historicVariable);
  assertEquals("number",historicVariable.getVariableName());
  assertEquals("two",historicVariable.getValue());
  historicVariable=historyService.createHistoricVariableInstanceQuery().variableValueLikeIgnoreCase("number","TW2%").singleResult();
  assertNull(historicVariable);
}
