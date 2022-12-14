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

import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.delegate.event.impl.ActivitiEventSupport;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;

/**
 * Test for event-listeners that are registered on a process-definition scope, rather than on the global engine-wide scope.
 * 

 */
public class ProcessDefinitionScopedEventListenerTest extends PluggableActivitiTestCase {

  protected TestActivitiEventListener testListenerAsBean;
  protected Map<Object, Object> oldBeans;

  @Deployment(resources={"org/activiti/engine/test/api/runtime/oneTaskProcess.bpmn20.xml","org/activiti/engine/test/api/event/simpleProcess.bpmn20.xml"}) public void testProcessDefinitionScopedListener() throws Exception{ProcessDefinition firstDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deploymentIdFromDeploymentAnnotation).processDefinitionKey("oneTaskProcess").singleResult();assertNotNull(firstDefinition);ProcessDefinition secondDefinition=repositoryService.createProcessDefinitionQuery().deploymentId(deploymentIdFromDeploymentAnnotation).processDefinitionKey("simpleProcess").singleResult();assertNotNull(firstDefinition);TestActivitiEventListener listener=new TestActivitiEventListener();BpmnModel bpmnModel=repositoryService.getBpmnModel(firstDefinition.getId());assertNotNull(bpmnModel);((ActivitiEventSupport)bpmnModel.getEventSupport()).addEventListener(listener);ProcessInstance processInstance=runtimeService.startProcessInstanceById(firstDefinition.getId());assertNotNull(processInstance);assertFalse(listener.getEventsReceived().isEmpty());listener.clearEventsReceived();ProcessInstance otherInstance=runtimeService.startProcessInstanceById(secondDefinition.getId());assertNotNull(otherInstance);assertTrue(listener.getEventsReceived().isEmpty());}
}
