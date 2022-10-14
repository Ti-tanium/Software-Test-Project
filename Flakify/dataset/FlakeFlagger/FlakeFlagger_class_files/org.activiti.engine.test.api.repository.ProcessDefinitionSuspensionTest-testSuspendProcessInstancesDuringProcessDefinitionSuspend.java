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
package org.activiti.engine.test.api.repository;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

public class ProcessDefinitionSuspensionTest extends PluggableActivitiTestCase {

  @Deployment(resources={"org/activiti/engine/test/api/runtime/oneTaskProcess.bpmn20.xml"}) public void testSuspendProcessInstancesDuringProcessDefinitionSuspend(){int nrOfProcessInstances=9;ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().singleResult();for (int i=0;i < nrOfProcessInstances;i++){runtimeService.startProcessInstanceByKey(processDefinition.getKey());}assertEquals(nrOfProcessInstances,runtimeService.createProcessInstanceQuery().count());assertEquals(0,runtimeService.createProcessInstanceQuery().suspended().count());assertEquals(nrOfProcessInstances,runtimeService.createProcessInstanceQuery().active().count());repositoryService.suspendProcessDefinitionById(processDefinition.getId(),true,null);for (ProcessInstance processInstance:runtimeService.createProcessInstanceQuery().list()){assertTrue(processInstance.isSuspended());}for (Task task:taskService.createTaskQuery().list()){try {taskService.complete(task.getId());fail("A suspended task shouldn't be able to be continued");} catch (ActivitiException e){}}assertEquals(nrOfProcessInstances,runtimeService.createProcessInstanceQuery().count());assertEquals(nrOfProcessInstances,runtimeService.createProcessInstanceQuery().suspended().count());assertEquals(0,runtimeService.createProcessInstanceQuery().active().count());repositoryService.activateProcessDefinitionById(processDefinition.getId(),true,null);for (Task task:taskService.createTaskQuery().list()){taskService.complete(task.getId());}assertEquals(0,runtimeService.createProcessInstanceQuery().count());assertEquals(0,runtimeService.createProcessInstanceQuery().suspended().count());assertEquals(0,runtimeService.createProcessInstanceQuery().active().count());}

}
