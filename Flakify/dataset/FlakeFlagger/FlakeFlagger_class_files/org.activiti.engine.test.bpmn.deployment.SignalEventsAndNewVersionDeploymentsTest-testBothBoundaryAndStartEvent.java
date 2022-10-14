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

package org.activiti.engine.test.bpmn.deployment;

import java.util.List;

import org.activiti.engine.impl.EventSubscriptionQueryImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**
 * A test specifically written to test how events (start/boundary) are handled 
 * when deploying a new version of a process definition. 
 * 

 */
public class SignalEventsAndNewVersionDeploymentsTest extends PluggableActivitiTestCase {
  
  private static final String TEST_PROCESS_GLOBAL_BOUNDARY_SIGNAL = 
      "org/activiti/engine/test/bpmn/deployment/SignalEventsAndNewVersionDeploymentsTest.testGlobalSignalBoundaryEvent.bpmn20.xml";
  
  private static final String TEST_PROCESS_START_SIGNAL = 
      "org/activiti/engine/test/bpmn/deployment/SignalEventsAndNewVersionDeploymentsTest.testStartSignalEvent.bpmn20.xml";
  
  private static final String TEST_PROCESS_NO_EVENTS =
      "org/activiti/engine/test/bpmn/deployment/SignalEventsAndNewVersionDeploymentsTest.processWithoutEvents.bpmn20.xml";
  
  private static final String TEST_PROCESS_BOTH_START_AND_BOUNDARY_SIGNAL =
      "org/activiti/engine/test/bpmn/deployment/SignalEventsAndNewVersionDeploymentsTest.testBothBoundaryAndStartSignal.bpmn20.xml";
  
  private static final String TEST_PROCESS_BOTH_START_AND_BOUNDARY_SIGNAL_SAME_SIGNAL =
      "org/activiti/engine/test/bpmn/deployment/SignalEventsAndNewVersionDeploymentsTest.testBothBoundaryAndStartSignalSameSignal.bpmn20.xml";
  
  /* 
   * BOUNDARY SIGNAL EVENT 
   */
  
  
  
  
  /*
   * START SIGNAL EVENT
   */
  
  


  /*
   * BOTH BOUNDARY AND START SIGNAL 
   */
  
  public void testBothBoundaryAndStartEvent() {
    
    // Deploy process with both boundary and start event
    
    String deploymentId1 = deployProcessWithBothStartAndBoundarySignal();
    assertEventSubscriptionsCount(1);
    assertEquals(0, runtimeService.createExecutionQuery().count());
    
    runtimeService.signalEventReceived("myStartSignal");
    runtimeService.signalEventReceived("myStartSignal");
    assertEquals(2, runtimeService.createProcessInstanceQuery().count());
    assertEquals(3, getAllEventSubscriptions().size()); // 1 for the start, 2 for the boundary
    
    // Deploy version with only a boundary signal
    String deploymentId2 = deployBoundarySignalTestProcess();
    runtimeService.signalEventReceived("myStartSignal");
    assertEquals(2, runtimeService.createProcessInstanceQuery().count());
    assertEventSubscriptionsCount(2);
    
    // Deploy version with signal start 
    String deploymentId3 = deployStartSignalTestProcess();
    runtimeService.signalEventReceived("myStartSignal");
    assertEquals(3, runtimeService.createProcessInstanceQuery().count());
    assertEventSubscriptionsCount(3);
    
    // Delete last version again, making the one with the boundary the latest
    repositoryService.deleteDeployment(deploymentId3, true);
    runtimeService.signalEventReceived("myStartSignal");
    assertEquals(2, runtimeService.createProcessInstanceQuery().count()); // -1, cause process instance of deploymentId3 is gone too
    assertEventSubscriptionsCount(2);
    
    // Test the boundary signal
    runtimeService.signalEventReceived("myBoundarySignal");
    assertEquals(2, taskService.createTaskQuery().taskName("Task after boundary signal").list().size());

    // Delete second version
    repositoryService.deleteDeployment(deploymentId2, true);
    runtimeService.signalEventReceived("myStartSignal");
    assertEquals(3, runtimeService.createProcessInstanceQuery().count()); // -1, cause process instance of deploymentId3 is gone too
    assertEventSubscriptionsCount(2);
    
    cleanup(deploymentId1);
  }
  
 
  
  /*
   * HELPERS
   */
  
  private String deployBoundarySignalTestProcess() {
    return deploy(TEST_PROCESS_GLOBAL_BOUNDARY_SIGNAL);
  }
  
  private String deployStartSignalTestProcess() {
    return deploy(TEST_PROCESS_START_SIGNAL);
  }

  private String deployProcessWithoutEvents() {
    return deploy(TEST_PROCESS_NO_EVENTS);
  }
  
  private String deployProcessWithBothStartAndBoundarySignal() {
    return deploy(TEST_PROCESS_BOTH_START_AND_BOUNDARY_SIGNAL);
  }
  
  private String deployProcessWithBothStartAndBoundarySignalSameSignal() {
    return deploy(TEST_PROCESS_BOTH_START_AND_BOUNDARY_SIGNAL_SAME_SIGNAL);
  }
  
  private String deploy(String path) {
    String deploymentId = repositoryService
      .createDeployment()
      .addClasspathResource(path)
      .deploy()
      .getId();
    return deploymentId;
  }
  
  private void cleanup(String ... deploymentIds) {
    for (String deploymentId : deploymentIds) {
      repositoryService.deleteDeployment(deploymentId, true);
    }
  }
  
  private List<EventSubscriptionEntity> getAllEventSubscriptions() {
    return managementService.executeCommand(new Command<List<EventSubscriptionEntity>>() {
      public List<EventSubscriptionEntity> execute(CommandContext commandContext) {
        EventSubscriptionQueryImpl query = new EventSubscriptionQueryImpl(commandContext);
        query.orderByCreated().desc();
        
        List<EventSubscriptionEntity> eventSubscriptionEntities = query.list();
        for (EventSubscriptionEntity eventSubscriptionEntity : eventSubscriptionEntities) {
          assertEquals("signal", eventSubscriptionEntity.getEventType());
          assertNotNull(eventSubscriptionEntity.getProcessDefinitionId());
        }
        return eventSubscriptionEntities;
      }
    });
  }
  
  private void assertEventSubscriptionsCount(long count) {
  	assertEquals(count, getAllEventSubscriptions().size());
  }
  
}
