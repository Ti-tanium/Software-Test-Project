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
package org.activiti.engine.test.api.runtime;

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
public class RuntimeVariablesTest extends PluggableActivitiTestCase {

  private void checkVariable(String executionId, String name, String value, List<VariableInstance> variables){
    for (VariableInstance variable : variables){
      if (executionId.equals(variable.getExecutionId())){
        assertEquals(name, variable.getName());
        assertEquals(value, variable.getValue());
        return;
      }
    }
    fail();
  }
  
  @Deployment(resources={"org/activiti/engine/test/api/runtime/variableScope.bpmn20.xml"}) public void testGetVariablesByExecutionIdsForScope(){Map<String, Object> processVars=new HashMap<String, Object>();processVars.put("processVar","processVar");ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("variableScopeProcess",processVars);Set<String> executionIds=new HashSet<String>();List<Execution> executions=runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).list();for (Execution execution:executions){if (!processInstance.getId().equals(execution.getId())){executionIds.add(execution.getId());runtimeService.setVariableLocal(execution.getId(),"executionVar","executionVar");}}List<Task> tasks=taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();Set<String> taskIds=new HashSet<String>();for (Task task:tasks){taskService.setVariableLocal(task.getId(),"taskVar","taskVar");taskIds.add(task.getId());}List<VariableInstance> executionVariableInstances=runtimeService.getVariableInstancesByExecutionIds(executionIds);assertEquals(executionVariableInstances.size(),2);assertEquals(executionVariableInstances.get(0).getName(),"executionVar");assertEquals(executionVariableInstances.get(0).getValue(),"executionVar");assertEquals(executionVariableInstances.get(1).getName(),"executionVar");assertEquals(executionVariableInstances.get(1).getValue(),"executionVar");executionIds=new HashSet<String>();executionIds.add(processInstance.getId());executionVariableInstances=runtimeService.getVariableInstancesByExecutionIds(executionIds);assertEquals(executionVariableInstances.size(),1);assertEquals(executionVariableInstances.get(0).getName(),"processVar");assertEquals(executionVariableInstances.get(0).getValue(),"processVar");}
}
