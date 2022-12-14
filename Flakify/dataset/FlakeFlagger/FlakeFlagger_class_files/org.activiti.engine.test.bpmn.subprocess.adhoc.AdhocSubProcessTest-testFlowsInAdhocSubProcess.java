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

package org.activiti.engine.test.bpmn.subprocess.adhoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**

 */
public class AdhocSubProcessTest extends PluggableActivitiTestCase {

  @Deployment public void testFlowsInAdhocSubProcess(){Map<String, Object> variableMap=new HashMap<String, Object>();variableMap.put("completed",false);ProcessInstance pi=runtimeService.startProcessInstanceByKey("simpleSubProcess",variableMap);Execution execution=runtimeService.createExecutionQuery().activityId("adhocSubProcess").singleResult();assertNotNull(execution);List<FlowNode> enabledActivities=runtimeService.getEnabledActivitiesFromAdhocSubProcess(execution.getId());assertEquals(2,enabledActivities.size());runtimeService.executeActivityInAdhocSubProcess(execution.getId(),"subProcessTask");Task subProcessTask=taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();assertEquals("Task in subprocess",subProcessTask.getName());taskService.complete(subProcessTask.getId());try {runtimeService.executeActivityInAdhocSubProcess(execution.getId(),"subProcessTask2");fail("exception expected because can only enable one activity in a sequential ad-hoc sub process");} catch (ActivitiException e){}subProcessTask=taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();assertEquals("The next task",subProcessTask.getName());variableMap=new HashMap<String, Object>();variableMap.put("completed",true);taskService.complete(subProcessTask.getId(),variableMap);Task afterTask=taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();assertEquals("After task",afterTask.getName());taskService.complete(afterTask.getId());assertNull(runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult());}
}
