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

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.impl.EventSubscriptionQueryImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.EventSubscriptionEntity;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * A test specifically written to test how events (start/boundary) are handled 
 * when deploying a new version of a process definition. 
 * 

 */
public class MessageEventsAndNewVersionDeploymentsWithTenantIdTest extends PluggableActivitiTestCase {
  
  private static final String TEST_PROCESS_GLOBAL_BOUNDARY_MESSAGE = 
      "org/activiti/engine/test/bpmn/deployment/MessageEventsAndNewVersionDeploymentsTest.testGlobalMessageBoundaryEvent.bpmn20.xml";
  
  private static final String TEST_PROCESS_START_MESSAGE = 
      "org/activiti/engine/test/bpmn/deployment/MessageEventsAndNewVersionDeploymentsTest.testStartMessageEvent.bpmn20.xml";
  
  private static final String TEST_PROCESS_NO_EVENTS =
      "org/activiti/engine/test/bpmn/deployment/MessageEventsAndNewVersionDeploymentsTest.processWithoutEvents.bpmn20.xml";
  
  private static final String TEST_PROCESS_BOTH_START_AND_BOUNDARY_MESSAGE =
      "org/activiti/engine/test/bpmn/deployment/MessageEventsAndNewVersionDeploymentsTest.testBothBoundaryAndStartMessage.bpmn20.xml";
  
  private static final String TEST_PROCESS_BOTH_START_AND_BOUNDARY_MESSAGE_SAME_MESSAGE =
      "org/activiti/engine/test/bpmn/deployment/MessageEventsAndNewVersionDeploymentsTest.testBothBoundaryAndStartMessageSameMessage.bpmn20.xml";
	  
  private static final String TENANT_ID = "223344";
  
  /* 
   * BOUNDARY MESSAGE EVENT 
   */
  
  
  
  
  /*
   * START MESSAGE EVENT
   */
  
  /**
   * v1 -> has start message event
   * v2 -> has no start message event
   * v3 -> has start message event
   */
  public void testDeployIntermediateVersionWithoutMessageStartEvent() {
    String deploymentId1 = deployStartMessageTestProcess();
    assertEquals(1, getAllEventSubscriptions().size());
    assertEquals(0, runtimeService.createProcessInstanceQuery().count());
    runtimeService.startProcessInstanceByMessageAndTenantId("myStartMessage", TENANT_ID);
    assertEquals(1, runtimeService.createProcessInstanceQuery().count());
    assertEventSubscriptionsCount(1);
    
    String deploymentId2 = deployProcessWithoutEvents();
    assertEquals(0, getAllEventSubscriptions().size());
    assertEquals(1, runtimeService.createProcessInstanceQuery().count());
    try {
      runtimeService.startProcessInstanceByMessageAndTenantId("myStartMessage", TENANT_ID);
      fail();
    } catch (Exception e) { }
    assertEquals(1, runtimeService.createProcessInstanceQuery().count());
    assertEventSubscriptionsCount(0);
    
    String deploymentId3 = deployStartMessageTestProcess();
    assertEquals(1, getAllEventSubscriptions().size());
    assertEquals(1, runtimeService.createProcessInstanceQuery().count());
    runtimeService.startProcessInstanceByMessageAndTenantId("myStartMessage", TENANT_ID);
    assertEquals(2, runtimeService.createProcessInstanceQuery().count());
    assertEventSubscriptionsCount(1);
    
    List<EventSubscriptionEntity> eventSubscriptions = getAllEventSubscriptions();
    assertEquals(repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId3).singleResult().getId(), 
        eventSubscriptions.get(0).getProcessDefinitionId());
    
    cleanup(deploymentId1, deploymentId2, deploymentId3);
  }
  
  

  
  /*
   * BOTH BOUNDARY AND START MESSAGE 
   */
  
  
  
  /*
   * HELPERS
   */
  
  private String deployBoundaryMessageTestProcess() {
    return deploy(TEST_PROCESS_GLOBAL_BOUNDARY_MESSAGE);
  }
  
  private String deployStartMessageTestProcess() {
    return deploy(TEST_PROCESS_START_MESSAGE);
  }

  private String deployProcessWithoutEvents() {
    return deploy(TEST_PROCESS_NO_EVENTS);
  }
  
  private String deployProcessWithBothStartAndBoundaryMessage() {
    return deploy(TEST_PROCESS_BOTH_START_AND_BOUNDARY_MESSAGE);
  }
  
  private String deployProcessWithBothStartAndBoundarySameMessage() {
    return deploy(TEST_PROCESS_BOTH_START_AND_BOUNDARY_MESSAGE_SAME_MESSAGE);
  }
  
  private String deploy(String path) {
    String deploymentId = repositoryService
      .createDeployment()
	  .tenantId(TENANT_ID)
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
  
  private List<String> getExecutionIdsForMessageEventSubscription(final String messageName) {
    return managementService.executeCommand(new Command<List<String>>() {
      public List<String> execute(CommandContext commandContext) {
        EventSubscriptionQueryImpl query = new EventSubscriptionQueryImpl(commandContext);
        query.eventType("message");
        query.eventName(messageName);
        query.tenantId(TENANT_ID);
        query.orderByCreated().desc();
        List<EventSubscriptionEntity> eventSubscriptions = query.list();
        
        List<String> executionIds = new ArrayList<String>();
        for (EventSubscriptionEntity eventSubscription : eventSubscriptions) {
          executionIds.add(eventSubscription.getExecutionId());
        }
        return executionIds;
      }
    });
  }
  
  private List<EventSubscriptionEntity> getAllEventSubscriptions() {
    return managementService.executeCommand(new Command<List<EventSubscriptionEntity>>() {
      public List<EventSubscriptionEntity> execute(CommandContext commandContext) {
        EventSubscriptionQueryImpl query = new EventSubscriptionQueryImpl(commandContext);
        query.tenantId(TENANT_ID);
        query.orderByCreated().desc();
        
        List<EventSubscriptionEntity> eventSubscriptionEntities = query.list();
        for (EventSubscriptionEntity entity : eventSubscriptionEntities) {
          assertEquals("message", entity.getEventType());
          assertNotNull(entity.getProcessDefinitionId());
        }
        return eventSubscriptionEntities;
      }
    });
  }
  
  private void assertReceiveMessage(String messageName, int executionIdsCount) {
    List<String> executionIds =getExecutionIdsForMessageEventSubscription(messageName);
    assertEquals(executionIdsCount, executionIds.size());
    for (String executionId : executionIds) {
      runtimeService.messageEventReceived(messageName, executionId);
    }
  }
  
  private void assertEventSubscriptionsCount(long count) {
  	assertEquals(count, getAllEventSubscriptions().size());
  }

}