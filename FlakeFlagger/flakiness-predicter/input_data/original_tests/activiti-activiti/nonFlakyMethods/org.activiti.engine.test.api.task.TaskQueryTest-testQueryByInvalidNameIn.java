public void testQueryByInvalidNameIn(){
  final List<String> taskNameList=new ArrayList<String>(1);
  taskNameList.add("invalid");
  TaskQuery query=taskService.createTaskQuery().taskNameIn(taskNameList);
  assertEquals(0,query.list().size());
  assertEquals(0,query.count());
  try {
    taskService.createTaskQuery().or().taskId("invalid").taskNameIn(null).singleResult();
    fail("expected exception");
  }
 catch (  ActivitiIllegalArgumentException e) {
  }
}
