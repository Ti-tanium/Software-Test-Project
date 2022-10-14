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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.test.Deployment;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**




 */
public class ProcessInstanceQueryTest extends PluggableActivitiTestCase {

  private static final int PROCESS_DEFINITION_KEY_DEPLOY_COUNT = 4;
  private static final int PROCESS_DEFINITION_KEY_2_DEPLOY_COUNT = 1;
  private static final int PROCESS_DEPLOY_COUNT = PROCESS_DEFINITION_KEY_DEPLOY_COUNT + PROCESS_DEFINITION_KEY_2_DEPLOY_COUNT;
  private static final String PROCESS_DEFINITION_KEY = "oneTaskProcess";
  private static final String PROCESS_DEFINITION_KEY_2 = "oneTaskProcess2";
  private static final String PROCESS_DEFINITION_NAME = "oneTaskProcessName";
  private static final String PROCESS_DEFINITION_NAME_2 = "oneTaskProcess2Name";
  private static final String PROCESS_DEFINITION_CATEGORY = "org.activiti.enginge.test.api.runtime.Category";
  private static final String PROCESS_DEFINITION_CATEGORY_2 = "org.activiti.enginge.test.api.runtime.2Category";
  

  private org.activiti.engine.repository.Deployment deployment;
  private List<String> processInstanceIds;

  /**
   * Setup starts 4 process instances of oneTaskProcess and 1 instance of oneTaskProcess2
   */
  protected void setUp() throws Exception {
    super.setUp();
    deployment = repositoryService.createDeployment().addClasspathResource("org/activiti/engine/test/api/runtime/oneTaskProcess.bpmn20.xml")
        .addClasspathResource("org/activiti/engine/test/api/runtime/oneTaskProcess2.bpmn20.xml").deploy();

    processInstanceIds = new ArrayList<String>();
    for (int i = 0; i < PROCESS_DEFINITION_KEY_DEPLOY_COUNT; i++) {
      processInstanceIds.add(runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, i + "").getId());
    }
    processInstanceIds.add(runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY_2, "1").getId());
  }

  protected void tearDown() throws Exception {
    for (org.activiti.engine.repository.Deployment deployment : repositoryService.createDeploymentQuery().list()) {
      repositoryService.deleteDeployment(deployment.getId(), true);
    }
    super.tearDown();
  }

  @Deployment(resources = { "org/activiti/engine/test/api/oneTaskProcess.bpmn20.xml" })
  public void testQueryIntegerVariable() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("integerVar", 12345);
    ProcessInstance processInstance1 = runtimeService.startProcessInstanceByKey("oneTaskProcess", vars);

    vars = new HashMap<String, Object>();
    vars.put("integerVar", 12345);
    vars.put("integerVar2", 67890);
    ProcessInstance processInstance2 = runtimeService.startProcessInstanceByKey("oneTaskProcess", vars);

    vars = new HashMap<String, Object>();
    vars.put("integerVar", 55555);
    ProcessInstance processInstance3 = runtimeService.startProcessInstanceByKey("oneTaskProcess", vars);

    // Query on single integer variable, should result in 2 matches
    ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().variableValueEquals("integerVar", 12345);
    List<ProcessInstance> processInstances = query.list();
    assertNotNull(processInstances);
    assertEquals(2, processInstances.size());

    // Query on two integer variables, should result in single value
    query = runtimeService.createProcessInstanceQuery().variableValueEquals("integerVar", 12345).variableValueEquals("integerVar2", 67890);
    ProcessInstance resultInstance = query.singleResult();
    assertNotNull(resultInstance);
    assertEquals(processInstance2.getId(), resultInstance.getId());

    // Query with unexisting variable value
    resultInstance = runtimeService.createProcessInstanceQuery().variableValueEquals("integerVar", 9999).singleResult();
    assertNull(resultInstance);

    // Test NOT_EQUALS
    resultInstance = runtimeService.createProcessInstanceQuery().variableValueNotEquals("integerVar", 12345).singleResult();
    assertNotNull(resultInstance);
    assertEquals(processInstance3.getId(), resultInstance.getId());

    // Test GREATER_THAN
    resultInstance = runtimeService.createProcessInstanceQuery().variableValueGreaterThan("integerVar", 44444).singleResult();
    assertNotNull(resultInstance);
    assertEquals(processInstance3.getId(), resultInstance.getId());

    assertEquals(0, runtimeService.createProcessInstanceQuery().variableValueGreaterThan("integerVar", 55555).count());
    assertEquals(3, runtimeService.createProcessInstanceQuery().variableValueGreaterThan("integerVar", 1).count());

    // Test GREATER_THAN_OR_EQUAL
    resultInstance = runtimeService.createProcessInstanceQuery().variableValueGreaterThanOrEqual("integerVar", 44444).singleResult();
    assertNotNull(resultInstance);
    assertEquals(processInstance3.getId(), resultInstance.getId());

    resultInstance = runtimeService.createProcessInstanceQuery().variableValueGreaterThanOrEqual("integerVar", 55555).singleResult();
    assertNotNull(resultInstance);
    assertEquals(processInstance3.getId(), resultInstance.getId());

    assertEquals(3, runtimeService.createProcessInstanceQuery().variableValueGreaterThanOrEqual("integerVar", 1).count());

    // Test LESS_THAN
    processInstances = runtimeService.createProcessInstanceQuery().variableValueLessThan("integerVar", 55555).list();
    assertEquals(2, processInstances.size());

    List<String> expectedIds = Arrays.asList(processInstance1.getId(), processInstance2.getId());
    List<String> ids = new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(), processInstances.get(1).getId()));
    ids.removeAll(expectedIds);
    assertTrue(ids.isEmpty());

    assertEquals(0, runtimeService.createProcessInstanceQuery().variableValueLessThan("integerVar", 12345).count());
    assertEquals(3, runtimeService.createProcessInstanceQuery().variableValueLessThan("integerVar", 66666).count());

    // Test LESS_THAN_OR_EQUAL
    processInstances = runtimeService.createProcessInstanceQuery().variableValueLessThanOrEqual("integerVar", 55555).list();
    assertEquals(3, processInstances.size());

    assertEquals(0, runtimeService.createProcessInstanceQuery().variableValueLessThanOrEqual("integerVar", 12344).count());

    // Test value-only matching
    resultInstance = runtimeService.createProcessInstanceQuery().variableValueEquals(55555).singleResult();
    assertNotNull(resultInstance);
    assertEquals(processInstance3.getId(), resultInstance.getId());

    processInstances = runtimeService.createProcessInstanceQuery().variableValueEquals(12345).list();
    assertEquals(2, processInstances.size());
    expectedIds = Arrays.asList(processInstance1.getId(), processInstance2.getId());
    ids = new ArrayList<String>(Arrays.asList(processInstances.get(0).getId(), processInstances.get(1).getId()));
    ids.removeAll(expectedIds);
    assertTrue(ids.isEmpty());

    resultInstance = runtimeService.createProcessInstanceQuery().variableValueEquals(9999).singleResult();
    assertNull(resultInstance);

    runtimeService.deleteProcessInstance(processInstance1.getId(), "test");
    runtimeService.deleteProcessInstance(processInstance2.getId(), "test");
    runtimeService.deleteProcessInstance(processInstance3.getId(), "test");
  }
}
