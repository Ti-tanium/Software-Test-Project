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
package org.activiti.engine.test.api.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.bpmn.helper.MessageThrowingEventListener;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**
 * Test case for all {@link ActivitiEventListener}s that throw a message BPMN event when an {@link ActivitiEvent} has been dispatched.
 * 

 */
public class MessageThrowingEventListenerTest extends PluggableActivitiTestCase {

  @Deployment public void testThrowMessageDefinedInProcessDefinition() throws Exception{ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("testMessage");assertNotNull(processInstance);Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();assertNotNull(task);taskService.setAssignee(task.getId(),"kermit");task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey("subTask").singleResult();assertNotNull(task);assertEquals("kermit",task.getAssignee());Task boundaryTask=taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey("boundaryTask").singleResult();assertNotNull(boundaryTask);}
}
