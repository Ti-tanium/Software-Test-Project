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

package org.activiti.engine.test.bpmn.servicetask;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

import com.fasterxml.jackson.databind.node.ObjectNode;


/**

 */
public class DynamicServiceTaskTest extends PluggableActivitiTestCase {
  
  @Deployment public void testChangeExpression(){DummyTestBean testBean=new DummyTestBean();Map<String, Object> varMap=new HashMap<String, Object>();varMap.put("bean",testBean);ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("dynamicServiceTask",varMap);Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();taskService.complete(task.getId());if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.ACTIVITY)){HistoricVariableInstance historicVariableInstance=historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).variableName("executed").singleResult();assertNotNull(historicVariableInstance);assertTrue((Boolean)historicVariableInstance.getValue());}assertProcessEnded(processInstance.getId());testBean=new DummyTestBean();varMap=new HashMap<String, Object>();varMap.put("bean2",testBean);processInstance=runtimeService.startProcessInstanceByKey("dynamicServiceTask",varMap);String processDefinitionId=processInstance.getProcessDefinitionId();ObjectNode infoNode=dynamicBpmnService.changeServiceTaskExpression("service","${bean2.test(execution)}");dynamicBpmnService.saveProcessDefinitionInfo(processDefinitionId,infoNode);task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();taskService.complete(task.getId());if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.ACTIVITY)){HistoricVariableInstance historicVariableInstance=historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstance.getId()).variableName("executed").singleResult();assertNotNull(historicVariableInstance);assertTrue((Boolean)historicVariableInstance.getValue());}assertProcessEnded(processInstance.getId());}
}
