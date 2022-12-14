@Deployment public void testTaskExecutionVariables(){
  String processInstanceId=runtimeService.startProcessInstanceByKey("oneTaskProcess").getId();
  String taskId=taskService.createTaskQuery().singleResult().getId();
  Map<String,Object> expectedVariables=new HashMap<String,Object>();
  assertEquals(expectedVariables,runtimeService.getVariables(processInstanceId));
  assertEquals(expectedVariables,taskService.getVariables(taskId));
  assertEquals(expectedVariables,runtimeService.getVariablesLocal(processInstanceId));
  assertEquals(expectedVariables,taskService.getVariablesLocal(taskId));
  runtimeService.setVariable(processInstanceId,"instrument","trumpet");
  expectedVariables=new HashMap<String,Object>();
  assertEquals(expectedVariables,taskService.getVariablesLocal(taskId));
  expectedVariables.put("instrument","trumpet");
  assertEquals(expectedVariables,runtimeService.getVariables(processInstanceId));
  assertEquals(expectedVariables,taskService.getVariables(taskId));
  assertEquals(expectedVariables,runtimeService.getVariablesLocal(processInstanceId));
  taskService.setVariable(taskId,"player","gonzo");
  assertTrue(taskService.hasVariable(taskId,"player"));
  assertFalse(taskService.hasVariableLocal(taskId,"budget"));
  expectedVariables=new HashMap<String,Object>();
  assertEquals(expectedVariables,taskService.getVariablesLocal(taskId));
  expectedVariables.put("player","gonzo");
  expectedVariables.put("instrument","trumpet");
  assertEquals(expectedVariables,runtimeService.getVariables(processInstanceId));
  assertEquals(expectedVariables,taskService.getVariables(taskId));
  assertEquals(expectedVariables,runtimeService.getVariablesLocal(processInstanceId));
  taskService.setVariableLocal(taskId,"budget","unlimited");
  assertTrue(taskService.hasVariableLocal(taskId,"budget"));
  assertTrue(taskService.hasVariable(taskId,"budget"));
  expectedVariables=new HashMap<String,Object>();
  expectedVariables.put("budget","unlimited");
  assertEquals(expectedVariables,taskService.getVariablesLocal(taskId));
  expectedVariables.put("player","gonzo");
  expectedVariables.put("instrument","trumpet");
  assertEquals(expectedVariables,taskService.getVariables(taskId));
  expectedVariables=new HashMap<String,Object>();
  expectedVariables.put("player","gonzo");
  expectedVariables.put("instrument","trumpet");
  assertEquals(expectedVariables,runtimeService.getVariables(processInstanceId));
  assertEquals(expectedVariables,runtimeService.getVariablesLocal(processInstanceId));
}
