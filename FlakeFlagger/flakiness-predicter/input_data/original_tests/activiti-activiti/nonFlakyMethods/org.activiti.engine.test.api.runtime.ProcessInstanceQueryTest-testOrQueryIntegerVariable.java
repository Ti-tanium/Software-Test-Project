@Deployment(resources={"org/activiti/engine/test/api/oneTaskProcess.bpmn20.xml"}) public void testOrQueryIntegerVariable(){
  Map<String,Object> vars=new HashMap<String,Object>();
  vars.put("integerVar",12345);
  ProcessInstance processInstance1=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  vars=new HashMap<String,Object>();
  vars.put("integerVar",12345);
  vars.put("integerVar2",67890);
  ProcessInstance processInstance2=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  vars=new HashMap<String,Object>();
  vars.put("integerVar",55555);
  ProcessInstance processInstance3=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  ProcessInstanceQuery query=runtimeService.createProcessInstanceQuery().or().variableValueEquals("integerVar",12345).processDefinitionId("undefined").endOr();
  List<ProcessInstance> processInstances=query.list();
  assertNotNull(processInstances);
  assertEquals(2,processInstances.size());
  query=runtimeService.createProcessInstanceQuery().or().variableValueEquals("integerVar",12345).processDefinitionId("undefined").endOr().or().processDefinitionKey("oneTaskProcess").processDefinitionId("undefined").endOr();
  processInstances=query.list();
  assertNotNull(processInstances);
  assertEquals(2,processInstances.size());
  query=runtimeService.createProcessInstanceQuery().variableValueEquals("integerVar",12345).or().variableValueEquals("integerVar2",67890).processDefinitionId("undefined").endOr();
  ProcessInstance resultInstance=query.singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance2.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().or().variableValueEquals("integerVar",9999).processDefinitionId("undefined").endOr().singleResult();
  assertNull(resultInstance);
  resultInstance=runtimeService.createProcessInstanceQuery().or().variableValueNotEquals("integerVar",12345).processDefinitionId("undefined").endOr().singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().or().variableValueGreaterThan("integerVar",44444).processDefinitionId("undefined").endOr().singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().or().variableValueGreaterThan("integerVar",44444).processDefinitionId("undefined").endOr().or().processDefinitionKey("oneTaskProcess").processDefinitionId("undefined").endOr().singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  assertEquals(0,runtimeService.createProcessInstanceQuery().or().variableValueGreaterThan("integerVar",55555).processDefinitionId("undefined").endOr().count());
  assertEquals(3,runtimeService.createProcessInstanceQuery().or().variableValueGreaterThan("integerVar",1).processDefinitionId("undefined").endOr().count());
  resultInstance=runtimeService.createProcessInstanceQuery().or().variableValueGreaterThanOrEqual("integerVar",44444).processDefinitionId("undefined").endOr().singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().or().variableValueGreaterThanOrEqual("integerVar",55555).processDefinitionId("undefined").endOr().singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  assertEquals(3,runtimeService.createProcessInstanceQuery().or().variableValueGreaterThanOrEqual("integerVar",1).processDefinitionId("undefined").endOr().count());
  processInstances=runtimeService.createProcessInstanceQuery().or().variableValueLessThan("integerVar",55555).processDefinitionId("undefined").endOr().list();
  assertEquals(2,processInstances.size());
  List<String> expectedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  List<String> ids=new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(),processInstances.get(1).getId()));
  ids.removeAll(expectedIds);
  assertTrue(ids.isEmpty());
  assertEquals(0,runtimeService.createProcessInstanceQuery().or().variableValueLessThan("integerVar",12345).processDefinitionId("undefined").endOr().count());
  assertEquals(3,runtimeService.createProcessInstanceQuery().or().variableValueLessThan("integerVar",66666).processDefinitionId("undefined").endOr().count());
  processInstances=runtimeService.createProcessInstanceQuery().or().variableValueLessThanOrEqual("integerVar",55555).processDefinitionId("undefined").endOr().list();
  assertEquals(3,processInstances.size());
  assertEquals(0,runtimeService.createProcessInstanceQuery().or().variableValueLessThanOrEqual("integerVar",12344).processDefinitionId("undefined").endOr().count());
  resultInstance=runtimeService.createProcessInstanceQuery().or().variableValueEquals(55555).processDefinitionId("undefined").endOr().singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  processInstances=runtimeService.createProcessInstanceQuery().or().variableValueEquals(12345).processDefinitionId("undefined").endOr().list();
  assertEquals(2,processInstances.size());
  expectedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  ids=new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(),processInstances.get(1).getId()));
  ids.removeAll(expectedIds);
  assertTrue(ids.isEmpty());
  resultInstance=runtimeService.createProcessInstanceQuery().or().variableValueEquals(9999).processDefinitionId("undefined").endOr().singleResult();
  assertNull(resultInstance);
  runtimeService.deleteProcessInstance(processInstance1.getId(),"test");
  runtimeService.deleteProcessInstance(processInstance2.getId(),"test");
  runtimeService.deleteProcessInstance(processInstance3.getId(),"test");
}
