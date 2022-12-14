package org.activiti.engine.test.api.variables;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**

 */
public class SerializableVariableTest extends PluggableActivitiTestCase {
  
  @Deployment public void testUpdateSerializableInServiceTask(){Map<String, Object> vars=new HashMap<String, Object>();vars.put("myVar",new TestSerializableVariable(1));ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("testUpdateSerializableInServiceTask",vars);Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();taskService.complete(task.getId());TestSerializableVariable testSerializableVariable=(TestSerializableVariable)runtimeService.getVariable(processInstance.getId(),"myVar");assertEquals(2,testSerializableVariable.getNumber());}
  
  public static class TestUpdateSerializableVariableDelegate implements JavaDelegate {
    
    public void execute(DelegateExecution execution) {
      TestSerializableVariable var = (TestSerializableVariable) execution.getVariable("myVar");
      var.setNumber(2);
    }
    
  }
  
  public static class TestSerializableVariable implements Serializable {

    private static final long serialVersionUID = 1L;
    private int number;

    public TestSerializableVariable(int number) {
      this.number = number;
    }

    public int getNumber() {
      return number;
    }

    public void setNumber(int number) {
      this.number = number;
    }

  }

}
