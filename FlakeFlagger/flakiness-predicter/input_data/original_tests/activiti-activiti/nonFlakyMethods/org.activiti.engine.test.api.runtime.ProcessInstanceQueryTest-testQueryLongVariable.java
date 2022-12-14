@Deployment(resources={"org/activiti/engine/test/api/oneTaskProcess.bpmn20.xml"}) public void testQueryLongVariable(){
  Map<String,Object> vars=new HashMap<String,Object>();
  vars.put("longVar",12345L);
  ProcessInstance processInstance1=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  vars=new HashMap<String,Object>();
  vars.put("longVar",12345L);
  vars.put("longVar2",67890L);
  ProcessInstance processInstance2=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  vars=new HashMap<String,Object>();
  vars.put("longVar",55555L);
  ProcessInstance processInstance3=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  ProcessInstanceQuery query=runtimeService.createProcessInstanceQuery().variableValueEquals("longVar",12345L);
  List<ProcessInstance> processInstances=query.list();
  assertNotNull(processInstances);
  assertEquals(2,processInstances.size());
  query=runtimeService.createProcessInstanceQuery().variableValueEquals("longVar",12345L).variableValueEquals("longVar2",67890L);
  ProcessInstance resultInstance=query.singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance2.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueEquals("longVar",999L).singleResult();
  assertNull(resultInstance);
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueNotEquals("longVar",12345L).singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueGreaterThan("longVar",44444L).singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  assertEquals(0,runtimeService.createProcessInstanceQuery().variableValueGreaterThan("longVar",55555L).count());
  assertEquals(3,runtimeService.createProcessInstanceQuery().variableValueGreaterThan("longVar",1L).count());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueGreaterThanOrEqual("longVar",44444L).singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueGreaterThanOrEqual("longVar",55555L).singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  assertEquals(3,runtimeService.createProcessInstanceQuery().variableValueGreaterThanOrEqual("longVar",1L).count());
  processInstances=runtimeService.createProcessInstanceQuery().variableValueLessThan("longVar",55555L).list();
  assertEquals(2,processInstances.size());
  List<String> expectedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  List<String> ids=new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(),processInstances.get(1).getId()));
  ids.removeAll(expectedIds);
  assertTrue(ids.isEmpty());
  assertEquals(0,runtimeService.createProcessInstanceQuery().variableValueLessThan("longVar",12345L).count());
  assertEquals(3,runtimeService.createProcessInstanceQuery().variableValueLessThan("longVar",66666L).count());
  processInstances=runtimeService.createProcessInstanceQuery().variableValueLessThanOrEqual("longVar",55555L).list();
  assertEquals(3,processInstances.size());
  assertEquals(0,runtimeService.createProcessInstanceQuery().variableValueLessThanOrEqual("longVar",12344L).count());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueEquals(55555L).singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  processInstances=runtimeService.createProcessInstanceQuery().variableValueEquals(12345L).list();
  assertEquals(2,processInstances.size());
  expectedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  ids=new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(),processInstances.get(1).getId()));
  ids.removeAll(expectedIds);
  assertTrue(ids.isEmpty());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueEquals(999L).singleResult();
  assertNull(resultInstance);
  runtimeService.deleteProcessInstance(processInstance1.getId(),"test");
  runtimeService.deleteProcessInstance(processInstance2.getId(),"test");
  runtimeService.deleteProcessInstance(processInstance3.getId(),"test");
}
