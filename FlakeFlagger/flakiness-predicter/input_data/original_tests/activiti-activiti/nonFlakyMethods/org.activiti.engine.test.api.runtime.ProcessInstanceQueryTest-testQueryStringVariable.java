@Deployment(resources={"org/activiti/engine/test/api/oneTaskProcess.bpmn20.xml"}) public void testQueryStringVariable(){
  Map<String,Object> vars=new HashMap<String,Object>();
  vars.put("stringVar","abcdef");
  ProcessInstance processInstance1=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  vars=new HashMap<String,Object>();
  vars.put("stringVar","abcdef");
  vars.put("stringVar2","ghijkl");
  ProcessInstance processInstance2=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  vars=new HashMap<String,Object>();
  vars.put("stringVar","azerty");
  ProcessInstance processInstance3=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  ProcessInstanceQuery query=runtimeService.createProcessInstanceQuery().variableValueEquals("stringVar","abcdef");
  List<ProcessInstance> processInstances=query.list();
  assertNotNull(processInstances);
  assertEquals(2,processInstances.size());
  query=runtimeService.createProcessInstanceQuery().variableValueEquals("stringVar","abcdef").variableValueEquals("stringVar2","ghijkl");
  ProcessInstance resultInstance=query.singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance2.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueNotEquals("stringVar","abcdef").singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueGreaterThan("stringVar","abcdef").singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueGreaterThan("stringVar","z").singleResult();
  assertNull(resultInstance);
  assertEquals(3,runtimeService.createProcessInstanceQuery().variableValueGreaterThanOrEqual("stringVar","abcdef").count());
  assertEquals(0,runtimeService.createProcessInstanceQuery().variableValueGreaterThanOrEqual("stringVar","z").count());
  processInstances=runtimeService.createProcessInstanceQuery().variableValueLessThan("stringVar","abcdeg").list();
  assertEquals(2,processInstances.size());
  List<String> expecedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  List<String> ids=new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(),processInstances.get(1).getId()));
  ids.removeAll(expecedIds);
  assertTrue(ids.isEmpty());
  assertEquals(0,runtimeService.createProcessInstanceQuery().variableValueLessThan("stringVar","abcdef").count());
  assertEquals(3,runtimeService.createProcessInstanceQuery().variableValueLessThanOrEqual("stringVar","z").count());
  processInstances=runtimeService.createProcessInstanceQuery().variableValueLessThanOrEqual("stringVar","abcdef").list();
  assertEquals(2,processInstances.size());
  expecedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  ids=new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(),processInstances.get(1).getId()));
  ids.removeAll(expecedIds);
  assertTrue(ids.isEmpty());
  assertEquals(3,runtimeService.createProcessInstanceQuery().variableValueLessThanOrEqual("stringVar","z").count());
  assertEquals(0,runtimeService.createProcessInstanceQuery().variableValueLessThanOrEqual("stringVar","aa").count());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueLike("stringVar","azert%").singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueLike("stringVar","%y").singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueLike("stringVar","%zer%").singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  assertEquals(3,runtimeService.createProcessInstanceQuery().variableValueLike("stringVar","a%").count());
  assertEquals(0,runtimeService.createProcessInstanceQuery().variableValueLike("stringVar","%x%").count());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueEquals("azerty").singleResult();
  assertNotNull(resultInstance);
  assertEquals(processInstance3.getId(),resultInstance.getId());
  processInstances=runtimeService.createProcessInstanceQuery().variableValueEquals("abcdef").list();
  assertEquals(2,processInstances.size());
  expecedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  ids=new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(),processInstances.get(1).getId()));
  ids.removeAll(expecedIds);
  assertTrue(ids.isEmpty());
  resultInstance=runtimeService.createProcessInstanceQuery().variableValueEquals("notmatchinganyvalues").singleResult();
  assertNull(resultInstance);
  runtimeService.deleteProcessInstance(processInstance1.getId(),"test");
  runtimeService.deleteProcessInstance(processInstance2.getId(),"test");
  runtimeService.deleteProcessInstance(processInstance3.getId(),"test");
}
