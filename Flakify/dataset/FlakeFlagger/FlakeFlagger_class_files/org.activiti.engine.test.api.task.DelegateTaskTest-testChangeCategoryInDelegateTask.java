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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**

 */
public class DelegateTaskTest extends PluggableActivitiTestCase {

  @Deployment public void testChangeCategoryInDelegateTask(){Map<String, Object> variables=new HashMap<String, Object>();variables.put("approvers",Collections.singletonList("kermit"));ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("delegateTaskTest",variables);List<Task> tasks=taskService.createTaskQuery().processInstanceId(processInstance.getId()).list();for (Task task:tasks){assertEquals("approval",task.getCategory());Map<String, Object> taskVariables=new HashMap<String, Object>();taskVariables.put("outcome","approve");taskService.complete(task.getId(),taskVariables,true);}assertEquals(0,taskService.createTaskQuery().processInstanceId(processInstance.getId()).count());for (HistoricTaskInstance historicTaskInstance:historyService.createHistoricTaskInstanceQuery().list()){assertEquals("approved",historicTaskInstance.getCategory());}}

}
