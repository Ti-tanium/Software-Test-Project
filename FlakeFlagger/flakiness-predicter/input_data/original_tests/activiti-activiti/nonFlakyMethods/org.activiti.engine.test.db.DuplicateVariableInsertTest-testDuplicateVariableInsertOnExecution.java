/** 
 * Test for ACT-1887: Inserting the same new variable at the same time, from 2 different threads, using 2 modified commands that use a barrier for starting and a barrier for completing the command, so they each insert a new variable guaranteed.
 */
public void testDuplicateVariableInsertOnExecution() throws Exception {
  String processDefinitionId=deployOneTaskTestProcess();
  final ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinitionId);
  final CyclicBarrier startBarrier=new CyclicBarrier(2);
  final CyclicBarrier endBarrier=new CyclicBarrier(2);
  final List<Exception> exceptions=new ArrayList<Exception>();
  Thread firstInsertThread=new Thread(new Runnable(){
    @Override public void run(){
      try {
        managementService.executeCommand(new SetVariableWithBarriersCommand(startBarrier,endBarrier,processInstance.getId()));
      }
 catch (      Exception e) {
        exceptions.add(e);
      }
    }
  }
);
  Thread secondInsertThread=new Thread(new Runnable(){
    @Override public void run(){
      try {
        managementService.executeCommand(new SetVariableWithBarriersCommand(startBarrier,endBarrier,processInstance.getId()));
      }
 catch (      Exception e) {
        exceptions.add(e);
      }
    }
  }
);
  firstInsertThread.start();
  secondInsertThread.start();
  firstInsertThread.join();
  secondInsertThread.join();
  assertEquals(1,exceptions.size());
  Map<String,Object> variables=runtimeService.getVariables(processInstance.getId());
  assertEquals(1,variables.size());
  assertEquals("12345",variables.get("var"));
  runtimeService.deleteProcessInstance(processInstance.getId(),"ShouldNotFail");
}
