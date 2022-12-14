/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.test.api.task;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**

 */
public class TaskVariablesTest extends PluggableActivitiTestCase {

  @Deployment public void testGetVariablesLocalByTaskIds(){ProcessInstance processInstance1=runtimeService.startProcessInstanceByKey("twoTaskProcess");ProcessInstance processInstance2=runtimeService.startProcessInstanceByKey("twoTaskProcess");List<Task> taskList1=taskService.createTaskQuery().processInstanceId(processInstance1.getId()).list();List<Task> taskList2=taskService.createTaskQuery().processInstanceId(processInstance2.getId()).list();for (Task task:taskList1){if ("usertask1".equals(task.getTaskDefinitionKey())){taskService.setVariableLocal(task.getId(),"taskVar1","sayHello1");} else {taskService.setVariableLocal(task.getId(),"taskVar2","sayHello2");}taskService.setVariable(task.getId(),"executionVar1","helloWorld1");}for (Task task:taskList2){if ("usertask1".equals(task.getTaskDefinitionKey())){taskService.setVariableLocal(task.getId(),"taskVar3","sayHello3");} else {taskService.setVariableLocal(task.getId(),"taskVar4","sayHello4");}taskService.setVariable(task.getId(),"executionVar2","helloWorld2");}Set<String> taskIds=new HashSet<String>();taskIds.add(taskList1.get(0).getId());taskIds.add(taskList1.get(1).getId());List<VariableInstance> variables=taskService.getVariableInstancesLocalByTaskIds(taskIds);assertEquals(2,variables.size());checkVariable(taskList1.get(0).getId(),"taskVar1","sayHello1",variables);checkVariable(taskList1.get(1).getId(),"taskVar2","sayHello2",variables);taskIds=new HashSet<String>();taskIds.add(taskList1.get(0).getId());taskIds.add(taskList1.get(1).getId());taskIds.add(taskList2.get(0).getId());taskIds.add(taskList2.get(1).getId());variables=taskService.getVariableInstancesLocalByTaskIds(taskIds);assertEquals(4,variables.size());checkVariable(taskList1.get(0).getId(),"taskVar1","sayHello1",variables);checkVariable(taskList1.get(1).getId(),"taskVar2","sayHello2",variables);checkVariable(taskList2.get(0).getId(),"taskVar3","sayHello3",variables);checkVariable(taskList2.get(1).getId(),"taskVar4","sayHello4",variables);taskIds=new HashSet<String>();taskIds.add(taskList1.get(0).getId());taskIds.add(taskList2.get(1).getId());variables=taskService.getVariableInstancesLocalByTaskIds(taskIds);assertEquals(2,variables.size());checkVariable(taskList1.get(0).getId(),"taskVar1","sayHello1",variables);checkVariable(taskList2.get(1).getId(),"taskVar4","sayHello4",variables);}
  
  private void checkVariable(String taskId, String name, String value, List<VariableInstance> variables){
    for (VariableInstance variable : variables){
      if (taskId.equals(variable.getTaskId())){
        assertEquals(name, variable.getName());
        assertEquals(value, variable.getValue());
        return;
      }
    }
    fail();
  }
  
  public static class MyVariable implements Serializable {

    private String value;

    public MyVariable(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }

  }

}
