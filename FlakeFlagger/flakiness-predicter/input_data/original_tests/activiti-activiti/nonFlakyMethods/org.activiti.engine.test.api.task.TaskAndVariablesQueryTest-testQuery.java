@Deployment public void testQuery(){
  Task task=taskService.createTaskQuery().includeTaskLocalVariables().taskAssignee(GONZO).singleResult();
  Map<String,Object> variableMap=task.getTaskLocalVariables();
  assertEquals(3,variableMap.size());
  assertEquals(0,task.getProcessVariables().size());
  assertNotNull(variableMap.get("testVar"));
  assertEquals("someVariable",variableMap.get("testVar"));
  assertNotNull(variableMap.get("testVar2"));
  assertEquals(123,variableMap.get("testVar2"));
  assertNotNull(variableMap.get("testVarBinary"));
  assertEquals("This is a binary variable",new String((byte[])variableMap.get("testVarBinary")));
  List<Task> tasks=taskService.createTaskQuery().list();
  assertEquals(3,tasks.size());
  task=taskService.createTaskQuery().includeProcessVariables().taskAssignee(GONZO).singleResult();
  assertEquals(0,task.getProcessVariables().size());
  assertEquals(0,task.getTaskLocalVariables().size());
  Map<String,Object> startMap=new HashMap<String,Object>();
  startMap.put("processVar",true);
  startMap.put("binaryVariable","This is a binary process variable".getBytes());
  runtimeService.startProcessInstanceByKey("oneTaskProcess",startMap);
  task=taskService.createTaskQuery().includeProcessVariables().taskAssignee(KERMIT).singleResult();
  assertEquals(2,task.getProcessVariables().size());
  assertEquals(0,task.getTaskLocalVariables().size());
  assertTrue((Boolean)task.getProcessVariables().get("processVar"));
  assertEquals("This is a binary process variable",new String((byte[])task.getProcessVariables().get("binaryVariable")));
  taskService.setVariable(task.getId(),"anotherProcessVar",123);
  taskService.setVariableLocal(task.getId(),"localVar","test");
  task=taskService.createTaskQuery().includeTaskLocalVariables().taskAssignee(KERMIT).singleResult();
  assertEquals(0,task.getProcessVariables().size());
  assertEquals(1,task.getTaskLocalVariables().size());
  assertEquals("test",task.getTaskLocalVariables().get("localVar"));
  task=taskService.createTaskQuery().includeProcessVariables().taskAssignee(KERMIT).singleResult();
  assertEquals(3,task.getProcessVariables().size());
  assertEquals(0,task.getTaskLocalVariables().size());
  assertEquals(true,task.getProcessVariables().get("processVar"));
  assertEquals(123,task.getProcessVariables().get("anotherProcessVar"));
  assertEquals("This is a binary process variable",new String((byte[])task.getProcessVariables().get("binaryVariable")));
  tasks=taskService.createTaskQuery().includeTaskLocalVariables().taskCandidateUser(KERMIT,KERMITSGROUPS).list();
  assertEquals(2,tasks.size());
  assertEquals(2,tasks.get(0).getTaskLocalVariables().size());
  assertEquals("test",tasks.get(0).getTaskLocalVariables().get("test"));
  assertEquals(0,tasks.get(0).getProcessVariables().size());
  tasks=taskService.createTaskQuery().includeProcessVariables().taskCandidateUser(KERMIT,KERMITSGROUPS).list();
  assertEquals(2,tasks.size());
  assertEquals(0,tasks.get(0).getProcessVariables().size());
  assertEquals(0,tasks.get(0).getTaskLocalVariables().size());
  task=taskService.createTaskQuery().includeTaskLocalVariables().taskAssignee(KERMIT).taskVariableValueEquals("localVar","test").singleResult();
  assertEquals(0,task.getProcessVariables().size());
  assertEquals(1,task.getTaskLocalVariables().size());
  assertEquals("test",task.getTaskLocalVariables().get("localVar"));
  task=taskService.createTaskQuery().includeProcessVariables().taskAssignee(KERMIT).taskVariableValueEquals("localVar","test").singleResult();
  assertEquals(3,task.getProcessVariables().size());
  assertEquals(0,task.getTaskLocalVariables().size());
  assertEquals(true,task.getProcessVariables().get("processVar"));
  assertEquals(123,task.getProcessVariables().get("anotherProcessVar"));
  task=taskService.createTaskQuery().includeTaskLocalVariables().includeProcessVariables().taskAssignee(KERMIT).singleResult();
  assertEquals(3,task.getProcessVariables().size());
  assertEquals(1,task.getTaskLocalVariables().size());
  assertEquals("test",task.getTaskLocalVariables().get("localVar"));
  assertEquals(true,task.getProcessVariables().get("processVar"));
  assertEquals(123,task.getProcessVariables().get("anotherProcessVar"));
  assertEquals("This is a binary process variable",new String((byte[])task.getProcessVariables().get("binaryVariable")));
}
