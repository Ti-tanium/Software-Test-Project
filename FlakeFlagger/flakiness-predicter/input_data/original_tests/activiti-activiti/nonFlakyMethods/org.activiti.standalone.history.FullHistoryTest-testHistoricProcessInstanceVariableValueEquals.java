@Deployment public void testHistoricProcessInstanceVariableValueEquals() throws Exception {
  Map<String,Object> variables=new HashMap<String,Object>();
  variables.put("longVar",12345L);
  variables.put("shortVar",(short)123);
  variables.put("integerVar",1234);
  variables.put("stringVar","stringValue");
  variables.put("booleanVar",true);
  Date date=Calendar.getInstance().getTime();
  variables.put("dateVar",date);
  variables.put("nullVar",null);
  ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("HistoricProcessInstanceTest",variables);
  assertEquals(7,historyService.createHistoricDetailQuery().variableUpdates().processInstanceId(processInstance.getId()).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("longVar",12345L).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("shortVar",(short)123).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("integerVar",1234).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("stringVar","stringValue").count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("booleanVar",true).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("dateVar",date).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("nullVar",null).count());
  variables.put("longVar",67890L);
  variables.put("shortVar",(short)456);
  variables.put("integerVar",5678);
  variables.put("stringVar","updatedStringValue");
  variables.put("booleanVar",false);
  Calendar otherCal=Calendar.getInstance();
  otherCal.add(Calendar.DAY_OF_MONTH,1);
  Date otherDate=otherCal.getTime();
  variables.put("dateVar",otherDate);
  variables.put("nullVar",null);
  runtimeService.setVariables(processInstance.getId(),variables);
  assertEquals(14,historyService.createHistoricDetailQuery().variableUpdates().processInstanceId(processInstance.getId()).count());
  assertEquals(0,historyService.createHistoricProcessInstanceQuery().variableValueEquals("longVar",12345L).count());
  assertEquals(0,historyService.createHistoricProcessInstanceQuery().variableValueEquals("shortVar",(short)123).count());
  assertEquals(0,historyService.createHistoricProcessInstanceQuery().variableValueEquals("integerVar",1234).count());
  assertEquals(0,historyService.createHistoricProcessInstanceQuery().variableValueEquals("stringVar","stringValue").count());
  assertEquals(0,historyService.createHistoricProcessInstanceQuery().variableValueEquals("booleanVar",true).count());
  assertEquals(0,historyService.createHistoricProcessInstanceQuery().variableValueEquals("dateVar",date).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("longVar",67890L).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("shortVar",(short)456).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("integerVar",5678).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("stringVar","updatedStringValue").count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("booleanVar",false).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("dateVar",otherDate).count());
  assertEquals(1,historyService.createHistoricProcessInstanceQuery().variableValueEquals("nullVar",null).count());
}
