@Deployment(resources={"org/activiti/engine/test/api/oneTaskProcess.bpmn20.xml"}) public void testQueryDateVariable() throws Exception {
  Map<String,Object> vars=new HashMap<String,Object>();
  Date date1=Calendar.getInstance().getTime();
  vars.put("dateVar",date1);
  ProcessInstance processInstance1=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  Calendar cal2=Calendar.getInstance();
  cal2.add(Calendar.SECOND,1);
  Date date2=cal2.getTime();
  vars=new HashMap<String,Object>();
  vars.put("dateVar",date1);
  vars.put("dateVar2",date2);
  ProcessInstance processInstance2=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  Calendar nextYear=Calendar.getInstance();
  nextYear.add(Calendar.YEAR,1);
  vars=new HashMap<String,Object>();
  vars.put("dateVar",nextYear.getTime());
  ProcessInstance processInstance3=runtimeService.startProcessInstanceByKey("oneTaskProcess",vars);
  Calendar nextMonth=Calendar.getInstance();
  nextMonth.add(Calendar.MONTH,1);
  Calendar twoYearsLater=Calendar.getInstance();
  twoYearsLater.add(Calendar.YEAR,2);
  Calendar oneYearAgo=Calendar.getInstance();
  oneYearAgo.add(Calendar.YEAR,-1);
  ExecutionQuery query=runtimeService.createExecutionQuery().variableValueEquals("dateVar",date1);
  List<Execution> executions=query.list();
  assertNotNull(executions);
  assertEquals(2,executions.size());
  query=runtimeService.createExecutionQuery().variableValueEquals("dateVar",date1).variableValueEquals("dateVar2",date2);
  Execution execution=query.singleResult();
  assertNotNull(execution);
  assertEquals(processInstance2.getId(),execution.getId());
  Date unexistingDate=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse("01/01/1989 12:00:00");
  execution=runtimeService.createExecutionQuery().variableValueEquals("dateVar",unexistingDate).singleResult();
  assertNull(execution);
  execution=runtimeService.createExecutionQuery().variableValueNotEquals("dateVar",date1).singleResult();
  assertNotNull(execution);
  assertEquals(processInstance3.getId(),execution.getId());
  execution=runtimeService.createExecutionQuery().variableValueGreaterThan("dateVar",nextMonth.getTime()).singleResult();
  assertNotNull(execution);
  assertEquals(processInstance3.getId(),execution.getId());
  assertEquals(0,runtimeService.createExecutionQuery().variableValueGreaterThan("dateVar",nextYear.getTime()).count());
  assertEquals(3,runtimeService.createExecutionQuery().variableValueGreaterThan("dateVar",oneYearAgo.getTime()).count());
  execution=runtimeService.createExecutionQuery().variableValueGreaterThanOrEqual("dateVar",nextMonth.getTime()).singleResult();
  assertNotNull(execution);
  assertEquals(processInstance3.getId(),execution.getId());
  execution=runtimeService.createExecutionQuery().variableValueGreaterThanOrEqual("dateVar",nextYear.getTime()).singleResult();
  assertNotNull(execution);
  assertEquals(processInstance3.getId(),execution.getId());
  assertEquals(3,runtimeService.createExecutionQuery().variableValueGreaterThanOrEqual("dateVar",oneYearAgo.getTime()).count());
  executions=runtimeService.createExecutionQuery().variableValueLessThan("dateVar",nextYear.getTime()).list();
  assertEquals(2,executions.size());
  List<String> expectedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  List<String> ids=new ArrayList<String>(Arrays.asList(executions.get(0).getId(),executions.get(1).getId()));
  ids.removeAll(expectedIds);
  assertTrue(ids.isEmpty());
  assertEquals(0,runtimeService.createExecutionQuery().variableValueLessThan("dateVar",date1).count());
  assertEquals(3,runtimeService.createExecutionQuery().variableValueLessThan("dateVar",twoYearsLater.getTime()).count());
  executions=runtimeService.createExecutionQuery().variableValueLessThanOrEqual("dateVar",nextYear.getTime()).list();
  assertEquals(3,executions.size());
  assertEquals(0,runtimeService.createExecutionQuery().variableValueLessThanOrEqual("dateVar",oneYearAgo.getTime()).count());
  execution=runtimeService.createExecutionQuery().variableValueEquals(nextYear.getTime()).singleResult();
  assertNotNull(execution);
  assertEquals(processInstance3.getId(),execution.getId());
  executions=runtimeService.createExecutionQuery().variableValueEquals(date1).list();
  assertEquals(2,executions.size());
  expectedIds=Arrays.asList(processInstance1.getId(),processInstance2.getId());
  ids=new ArrayList<String>(Arrays.asList(executions.get(0).getId(),executions.get(1).getId()));
  ids.removeAll(expectedIds);
  assertTrue(ids.isEmpty());
  execution=runtimeService.createExecutionQuery().variableValueEquals(twoYearsLater.getTime()).singleResult();
  assertNull(execution);
  runtimeService.deleteProcessInstance(processInstance1.getId(),"test");
  runtimeService.deleteProcessInstance(processInstance2.getId(),"test");
  runtimeService.deleteProcessInstance(processInstance3.getId(),"test");
}
