public void testDeploySameFileTwice(){
  String bpmnResourceName="org/activiti/engine/test/bpmn/deployment/BpmnDeploymentTest.testGetBpmnXmlFileThroughService.bpmn20.xml";
  repositoryService.createDeployment().enableDuplicateFiltering().addClasspathResource(bpmnResourceName).name("twice").deploy();
  String deploymentId=repositoryService.createDeploymentQuery().singleResult().getId();
  List<String> deploymentResources=repositoryService.getDeploymentResourceNames(deploymentId);
  assertEquals(1,deploymentResources.size());
  assertEquals(bpmnResourceName,deploymentResources.get(0));
  repositoryService.createDeployment().enableDuplicateFiltering().addClasspathResource(bpmnResourceName).name("twice").deploy();
  List<org.activiti.engine.repository.Deployment> deploymentList=repositoryService.createDeploymentQuery().list();
  assertEquals(1,deploymentList.size());
  repositoryService.deleteDeployment(deploymentId);
}
