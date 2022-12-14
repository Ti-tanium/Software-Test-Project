@Deployment public void testQuery(){
  if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.ACTIVITY)) {
    HistoricTaskInstance task=historyService.createHistoricTaskInstanceQuery().includeTaskLocalVariables().taskAssignee(GONZO).singleResult();
    Map<String,Object> variableMap=task.getTaskLocalVariables();
    assertEquals(2,variableMap.size());
    assertEquals(0,task.getProcessVariables().size());
    assertNotNull(variableMap.get("testVar"));
    assertEquals("someVariable",variableMap.get("testVar"));
    assertNotNull(variableMap.get("testVar2"));
    assertEquals(123,variableMap.get("testVar2"));
    List<HistoricTaskInstance> tasks=historyService.createHistoricTaskInstanceQuery().list();
    assertEquals(3,tasks.size());
    task=historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskAssignee(GONZO).singleResult();
    assertEquals(0,task.getProcessVariables().size());
    assertEquals(0,task.getTaskLocalVariables().size());
    Map<String,Object> startMap=new HashMap<String,Object>();
    startMap.put("processVar",true);
    runtimeService.startProcessInstanceByKey("oneTaskProcess",startMap);
    task=historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskAssignee(KERMIT).singleResult();
    assertEquals(1,task.getProcessVariables().size());
    assertEquals(0,task.getTaskLocalVariables().size());
    assertTrue((Boolean)task.getProcessVariables().get("processVar"));
    taskService.setVariable(task.getId(),"anotherProcessVar",123);
    taskService.setVariableLocal(task.getId(),"localVar","test");
    task=historyService.createHistoricTaskInstanceQuery().includeTaskLocalVariables().taskAssignee(KERMIT).singleResult();
    assertEquals(0,task.getProcessVariables().size());
    assertEquals(1,task.getTaskLocalVariables().size());
    assertEquals("test",task.getTaskLocalVariables().get("localVar"));
    task=historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskAssignee(KERMIT).singleResult();
    assertEquals(2,task.getProcessVariables().size());
    assertEquals(0,task.getTaskLocalVariables().size());
    assertEquals(true,task.getProcessVariables().get("processVar"));
    assertEquals(123,task.getProcessVariables().get("anotherProcessVar"));
    task=historyService.createHistoricTaskInstanceQuery().taskVariableValueLike("testVar","someVaria%").singleResult();
    assertNotNull(task);
    assertEquals("gonzoTask",task.getName());
    task=historyService.createHistoricTaskInstanceQuery().taskVariableValueLikeIgnoreCase("testVar","somevaria%").singleResult();
    assertNotNull(task);
    assertEquals("gonzoTask",task.getName());
    task=historyService.createHistoricTaskInstanceQuery().taskVariableValueLikeIgnoreCase("testVar","somevaria2%").singleResult();
    assertNull(task);
    tasks=historyService.createHistoricTaskInstanceQuery().includeTaskLocalVariables().taskInvolvedUser(KERMIT).orderByTaskCreateTime().asc().list();
    assertEquals(3,tasks.size());
    assertEquals(1,tasks.get(0).getTaskLocalVariables().size());
    assertEquals("test",tasks.get(0).getTaskLocalVariables().get("test"));
    assertEquals(0,tasks.get(0).getProcessVariables().size());
    tasks=historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskInvolvedUser(KERMIT).orderByTaskCreateTime().asc().list();
    assertEquals(3,tasks.size());
    assertEquals(0,tasks.get(0).getProcessVariables().size());
    assertEquals(0,tasks.get(0).getTaskLocalVariables().size());
    task=historyService.createHistoricTaskInstanceQuery().includeTaskLocalVariables().taskAssignee(KERMIT).taskVariableValueEquals("localVar","test").singleResult();
    assertEquals(0,task.getProcessVariables().size());
    assertEquals(1,task.getTaskLocalVariables().size());
    assertEquals("test",task.getTaskLocalVariables().get("localVar"));
    task=historyService.createHistoricTaskInstanceQuery().includeProcessVariables().taskAssignee(KERMIT).taskVariableValueEquals("localVar","test").singleResult();
    assertEquals(2,task.getProcessVariables().size());
    assertEquals(0,task.getTaskLocalVariables().size());
    assertEquals(true,task.getProcessVariables().get("processVar"));
    assertEquals(123,task.getProcessVariables().get("anotherProcessVar"));
    task=historyService.createHistoricTaskInstanceQuery().includeTaskLocalVariables().includeProcessVariables().taskAssignee(KERMIT).singleResult();
    assertEquals(2,task.getProcessVariables().size());
    assertEquals(1,task.getTaskLocalVariables().size());
    assertEquals("test",task.getTaskLocalVariables().get("localVar"));
    assertEquals(true,task.getProcessVariables().get("processVar"));
    assertEquals(123,task.getProcessVariables().get("anotherProcessVar"));
    task=historyService.createHistoricTaskInstanceQuery().taskAssignee(GONZO).singleResult();
    taskService.complete(task.getId());
    task=(HistoricTaskInstance)historyService.createHistoricTaskInstanceQuery().includeTaskLocalVariables().finished().singleResult();
    variableMap=task.getTaskLocalVariables();
    assertEquals(2,variableMap.size());
    assertEquals(0,task.getProcessVariables().size());
    assertNotNull(variableMap.get("testVar"));
    assertEquals("someVariable",variableMap.get("testVar"));
    assertNotNull(variableMap.get("testVar2"));
    assertEquals(123,variableMap.get("testVar2"));
  }
}
