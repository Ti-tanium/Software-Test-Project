public void testMessageBoundaryEvent(){
  String deploymentId1=deployBoundaryMessageTestProcess();
  runtimeService.startProcessInstanceByKey("messageTest");
  assertEquals(1,getAllEventSubscriptions().size());
  String deploymentId2=deployBoundaryMessageTestProcess();
  runtimeService.startProcessInstanceByKey("messageTest");
  assertEquals(2,getAllEventSubscriptions().size());
  assertReceiveMessage("myMessage",2);
  List<Task> tasks=taskService.createTaskQuery().list();
  assertEquals(2,tasks.size());
  for (  Task task : tasks) {
    assertEquals("Task after message",task.getName());
  }
  cleanup(deploymentId1,deploymentId2);
}
