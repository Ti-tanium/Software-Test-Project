@Deployment(resources={"org/activiti/engine/test/api/oneTaskProcess.bpmn20.xml","org/activiti/engine/test/api/runtime/oneTaskProcess2.bpmn20.xml"}) public void testHistoricTaskInstanceQueryByDeploymentIdIn(){
  org.activiti.engine.repository.Deployment deployment=repositoryService.createDeploymentQuery().singleResult();
  HashSet<String> processInstanceIds=new HashSet<String>();
  for (int i=0; i < 4; i++) {
    processInstanceIds.add(runtimeService.startProcessInstanceByKey("oneTaskProcess",i + "").getId());
  }
  processInstanceIds.add(runtimeService.startProcessInstanceByKey("oneTaskProcess2","1").getId());
  List<String> deploymentIds=new ArrayList<String>();
  deploymentIds.add(deployment.getId());
  HistoricTaskInstanceQuery taskInstanceQuery=historyService.createHistoricTaskInstanceQuery().deploymentIdIn(deploymentIds);
  assertEquals(5,taskInstanceQuery.count());
  List<HistoricTaskInstance> taskInstances=taskInstanceQuery.list();
  assertNotNull(taskInstances);
  assertEquals(5,taskInstances.size());
  deploymentIds.add("invalid");
  taskInstanceQuery=historyService.createHistoricTaskInstanceQuery().deploymentIdIn(deploymentIds);
  assertEquals(5,taskInstanceQuery.count());
  deploymentIds=new ArrayList<String>();
  deploymentIds.add("invalid");
  taskInstanceQuery=historyService.createHistoricTaskInstanceQuery().deploymentIdIn(deploymentIds);
  assertEquals(0,taskInstanceQuery.count());
}
