public void testOrQuery(){
  if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.ACTIVITY)) {
    HistoricProcessInstance processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().variableValueEquals("anothertest",123).processDefinitionId("undefined").endOr().singleResult();
    Map<String,Object> variableMap=processInstance.getProcessVariables();
    assertEquals(1,variableMap.size());
    assertEquals(123,variableMap.get("anothertest"));
    processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().variableValueEquals("anothertest",123).processDefinitionId("undefined").endOr().or().processDefinitionKey(PROCESS_DEFINITION_KEY_2).processDefinitionId("undefined").endOr().singleResult();
    variableMap=processInstance.getProcessVariables();
    assertEquals(1,variableMap.size());
    assertEquals(123,variableMap.get("anothertest"));
    processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().variableValueEquals("anothertest",123).processDefinitionId("undefined").endOr().or().processDefinitionKey(PROCESS_DEFINITION_KEY).processDefinitionId("undefined").endOr().singleResult();
    assertNull(processInstance);
    processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().variableValueLikeIgnoreCase("casetest","mytest").processDefinitionId("undefined").endOr().singleResult();
    assertNotNull(processInstance);
    variableMap=processInstance.getProcessVariables();
    assertEquals(1,variableMap.size());
    assertEquals("MyTest",variableMap.get("casetest"));
    List<HistoricProcessInstance> instanceList=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().processDefinitionKey(PROCESS_DEFINITION_KEY).processDefinitionId("undefined").endOr().list();
    assertEquals(4,instanceList.size());
    processInstance=instanceList.get(0);
    variableMap=processInstance.getProcessVariables();
    assertEquals(2,variableMap.size());
    assertEquals("test",variableMap.get("test"));
    assertEquals("test2",variableMap.get("test2"));
    processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().processDefinitionKey(PROCESS_DEFINITION_KEY_2).processDefinitionId("undefined").endOr().singleResult();
    variableMap=processInstance.getProcessVariables();
    assertEquals(1,variableMap.size());
    assertEquals(123,variableMap.get("anothertest"));
    processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().finished().processDefinitionId("undefined").endOr().singleResult();
    variableMap=processInstance.getProcessVariables();
    assertEquals(2,variableMap.size());
    assertEquals("test",variableMap.get("test"));
    assertEquals("test2",variableMap.get("test2"));
    instanceList=historyService.createHistoricProcessInstanceQuery().or().variableValueEquals("test","test").processDefinitionId("undefined").endOr().includeProcessVariables().listPage(0,50);
    assertEquals(4,instanceList.size());
    instanceList=historyService.createHistoricProcessInstanceQuery().or().variableValueEquals("test","test").processDefinitionId("undefined").endOr().includeProcessVariables().listPage(0,1);
    assertEquals(1,instanceList.size());
    processInstance=instanceList.get(0);
    variableMap=processInstance.getProcessVariables();
    assertEquals(2,variableMap.size());
    assertEquals("test",variableMap.get("test"));
    assertEquals("test2",variableMap.get("test2"));
    instanceList=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().processDefinitionKey(PROCESS_DEFINITION_KEY).processDefinitionId("undefined").endOr().listPage(1,2);
    assertEquals(2,instanceList.size());
    processInstance=instanceList.get(0);
    variableMap=processInstance.getProcessVariables();
    assertEquals(2,variableMap.size());
    assertEquals("test",variableMap.get("test"));
    assertEquals("test2",variableMap.get("test2"));
    instanceList=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().processDefinitionKey(PROCESS_DEFINITION_KEY).processDefinitionId("undefined").endOr().listPage(3,4);
    assertEquals(1,instanceList.size());
    processInstance=instanceList.get(0);
    variableMap=processInstance.getProcessVariables();
    assertEquals(2,variableMap.size());
    assertEquals("test",variableMap.get("test"));
    assertEquals("test2",variableMap.get("test2"));
    instanceList=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().or().processDefinitionKey(PROCESS_DEFINITION_KEY).processDefinitionId("undefined").endOr().listPage(4,2);
    assertEquals(0,instanceList.size());
    instanceList=historyService.createHistoricProcessInstanceQuery().or().variableValueEquals("test","test").processDefinitionId("undefined").endOr().includeProcessVariables().orderByProcessInstanceId().asc().listPage(0,50);
    assertEquals(4,instanceList.size());
    instanceList=historyService.createHistoricProcessInstanceQuery().or().variableValueEquals("test","test").processDefinitionId("undefined").endOr().or().processDefinitionKey(PROCESS_DEFINITION_KEY).processDefinitionId("undefined").endOr().includeProcessVariables().orderByProcessInstanceId().asc().listPage(0,50);
    assertEquals(4,instanceList.size());
  }
}
