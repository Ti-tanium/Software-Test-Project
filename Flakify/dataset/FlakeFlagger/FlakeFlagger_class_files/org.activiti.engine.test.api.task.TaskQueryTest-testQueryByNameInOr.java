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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntity;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.engine.test.Deployment;

import com.fasterxml.jackson.databind.node.ObjectNode;

import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.mockito.Mockito;

/**



 */
public class TaskQueryTest extends PluggableActivitiTestCase {

  private List<String> taskIds;

  private static final String KERMIT = "kermit";
  private static final List<String> KERMITSGROUPS = Arrays.asList("management","accountancy");

  private static final String GONZO = "gonzo";
  private static final List<String> GONZOSGROUPS = Arrays.asList();

  private static final String FOZZIE = "fozzie";
  private static final List<String> FOZZIESGROUPS = Arrays.asList("management");

  private UserGroupManager userGroupManager = Mockito.mock(UserGroupManager.class);


  public void testQueryByNameInOr(){final List<String> taskNameList=new ArrayList<String>(2);taskNameList.add("testTask");taskNameList.add("gonzoTask");TaskQuery query=taskService.createTaskQuery().or().taskNameIn(taskNameList).taskId("invalid");assertEquals(7,query.list().size());assertEquals(7,query.count());try {query.singleResult();fail("expected exception");} catch (ActivitiException e){}}

  /**
   * Generates some test tasks. - 6 tasks where kermit is a candidate - 1 tasks where gonzo is assignee - 2 tasks assigned to management group - 2 tasks assigned to accountancy group - 1 task assigned
   * to both the management and accountancy group
   */
  private List<String> generateTestTasks() throws Exception {
    List<String> ids = new ArrayList<String>();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
    // 6 tasks for kermit
    processEngineConfiguration.getClock().setCurrentTime(sdf.parse("01/01/2001 01:01:01.000"));
    for (int i = 0; i < 6; i++) {
      Task task = taskService.newTask();
      task.setName("testTask");
      task.setDescription("testTask description");
      task.setOwner(GONZO);
      task.setPriority(3);
      taskService.saveTask(task);
      ids.add(task.getId());
      taskService.addCandidateUser(task.getId(), KERMIT);
    }

    processEngineConfiguration.getClock().setCurrentTime(sdf.parse("02/02/2002 02:02:02.000"));
    // 1 task for gonzo
    Task task = taskService.newTask();
    task.setName("gonzoTask");
    task.setDescription("gonzo description");
    task.setPriority(4);
    taskService.saveTask(task);
    taskService.setAssignee(task.getId(), GONZO);
    taskService.setVariable(task.getId(), "testVar", "someVariable");
    ids.add(task.getId());

    processEngineConfiguration.getClock().setCurrentTime(sdf.parse("03/03/2003 03:03:03.000"));
    // 2 tasks for management group
    for (int i = 0; i < 2; i++) {
      task = taskService.newTask();
      task.setName("managementTask");
      task.setPriority(10);
      taskService.saveTask(task);
      taskService.addCandidateGroup(task.getId(), "management");
      ids.add(task.getId());
    }

    processEngineConfiguration.getClock().setCurrentTime(sdf.parse("04/04/2004 04:04:04.000"));
    // 2 tasks for accountancy group
    for (int i = 0; i < 2; i++) {
      task = taskService.newTask();
      task.setName("accountancyTask");
      task.setDescription("accountancy description");
      taskService.saveTask(task);
      taskService.addCandidateGroup(task.getId(), "accountancy");
      ids.add(task.getId());
    }

    processEngineConfiguration.getClock().setCurrentTime(sdf.parse("05/05/2005 05:05:05.000"));
    // 1 task assigned to management and accountancy group
    task = taskService.newTask();
    task.setName("managementAndAccountancyTask");
    taskService.saveTask(task);
    taskService.addCandidateGroup(task.getId(), "management");
    taskService.addCandidateGroup(task.getId(), "accountancy");
    ids.add(task.getId());

    return ids;
  }

}
