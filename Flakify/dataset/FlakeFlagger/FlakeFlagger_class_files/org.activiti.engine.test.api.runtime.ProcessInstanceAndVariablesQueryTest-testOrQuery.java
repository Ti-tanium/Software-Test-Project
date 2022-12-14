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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;

/**

 */
public class ProcessInstanceAndVariablesQueryTest extends PluggableActivitiTestCase {

  private static String PROCESS_DEFINITION_KEY = "oneTaskProcess";
  private static String PROCESS_DEFINITION_KEY_2 = "oneTaskProcess2";
  private static String PROCESS_DEFINITION_KEY_3 = "oneTaskProcess3";
  
  private List<String> processInstanceIds;

  /**
   * Setup starts 4 process instances of oneTaskProcess and 1 instance of oneTaskProcess2
   */
  protected void setUp() throws Exception {
    super.setUp();
    repositoryService.createDeployment()
      .addClasspathResource("org/activiti/engine/test/api/runtime/oneTaskProcess.bpmn20.xml")
      .addClasspathResource("org/activiti/engine/test/api/runtime/oneTaskProcess2.bpmn20.xml")
      .addClasspathResource("org/activiti/engine/test/api/runtime/oneTaskProcess3.bpmn20.xml")
      .deploy();
    
    Map<String, Object> startMap = new HashMap<String, Object>();
    startMap.put("test", "test");
    startMap.put("test2", "test2");
    processInstanceIds = new ArrayList<String>();
    for (int i = 0; i < 4; i++) {
      processInstanceIds.add(runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, i + "", startMap).getId());
    }
    
    startMap.clear();
    startMap.put("anothertest", 123);
    processInstanceIds.add(runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY_2, "1", startMap).getId());
    
    startMap.clear();
    startMap.put("casetest", "MyCaseTest");
    processInstanceIds.add(runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY_3, "1", startMap).getId());
  }

  protected void tearDown() throws Exception {
    for (org.activiti.engine.repository.Deployment deployment : repositoryService.createDeploymentQuery().list()) {
      repositoryService.deleteDeployment(deployment.getId(), true);
    }
    super.tearDown();
  }

  public void testOrQuery() {
    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().includeProcessVariables()
            .or().variableValueEquals("undefined", 999).variableValueEquals("anothertest", 123).endOr().singleResult();
    Map<String, Object> variableMap = processInstance.getProcessVariables();
    assertEquals(1, variableMap.size());
    assertEquals(123, variableMap.get("anothertest"));

    processInstance = runtimeService.createProcessInstanceQuery().includeProcessVariables()
            .or().variableValueEquals("undefined", 999).endOr().singleResult();
    assertNull(processInstance);

    processInstance = runtimeService.createProcessInstanceQuery().includeProcessVariables()
            .or().variableValueEquals("anothertest", 123).variableValueEquals("undefined", 999).endOr().singleResult();
    variableMap = processInstance.getProcessVariables();
    assertEquals(1, variableMap.size());
    assertEquals(123, variableMap.get("anothertest"));

    processInstance = runtimeService.createProcessInstanceQuery().includeProcessVariables()
            .or().variableValueEquals("anothertest", 999).endOr().singleResult();
    assertNull(processInstance);

    processInstance = runtimeService.createProcessInstanceQuery().includeProcessVariables()
            .or().variableValueEquals("anothertest", 999).variableValueEquals("anothertest", 123).endOr().singleResult();
    variableMap = processInstance.getProcessVariables();
    assertEquals(1, variableMap.size());
    assertEquals(123, variableMap.get("anothertest"));
  }

}
