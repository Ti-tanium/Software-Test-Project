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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.engine.test.Deployment;

/**

 */
public class TaskAndVariablesQueryTest extends PluggableActivitiTestCase {

    private List<String> taskIds;
    private List<String> multipleTaskIds;

    private static final String KERMIT = "kermit";
    private static final List<String> KERMITSGROUPS = Arrays.asList("management",
                                                                    "accountancy");

    private static final String GONZO = "gonzo";

    @Deployment
    public void testQuery() {
        Task task = taskService.createTaskQuery().includeTaskLocalVariables().taskAssignee(GONZO).singleResult();
        Map<String, Object> variableMap = task.getTaskLocalVariables();
        assertEquals(3,
                     variableMap.size());
        assertEquals(0,
                     task.getProcessVariables().size());
        assertNotNull(variableMap.get("testVar"));
        assertEquals("someVariable",
                     variableMap.get("testVar"));
        assertNotNull(variableMap.get("testVar2"));
        assertEquals(123,
                     variableMap.get("testVar2"));
        assertNotNull(variableMap.get("testVarBinary"));
        assertEquals("This is a binary variable",
                     new String((byte[]) variableMap.get("testVarBinary")));

        List<Task> tasks = taskService.createTaskQuery().list();
        assertEquals(3,
                     tasks.size());

        task = taskService.createTaskQuery().includeProcessVariables().taskAssignee(GONZO).singleResult();
        assertEquals(0,
                     task.getProcessVariables().size());
        assertEquals(0,
                     task.getTaskLocalVariables().size());

        Map<String, Object> startMap = new HashMap<String, Object>();
        startMap.put("processVar",
                     true);
        startMap.put("binaryVariable",
                     "This is a binary process variable".getBytes());
        runtimeService.startProcessInstanceByKey("oneTaskProcess",
                                                 startMap);

        task = taskService.createTaskQuery().includeProcessVariables().taskAssignee(KERMIT).singleResult();
        assertEquals(2,
                     task.getProcessVariables().size());
        assertEquals(0,
                     task.getTaskLocalVariables().size());
        assertTrue((Boolean) task.getProcessVariables().get("processVar"));
        assertEquals("This is a binary process variable",
                     new String((byte[]) task.getProcessVariables().get("binaryVariable")));

        taskService.setVariable(task.getId(),
                                "anotherProcessVar",
                                123);
        taskService.setVariableLocal(task.getId(),
                                     "localVar",
                                     "test");

        task = taskService.createTaskQuery().includeTaskLocalVariables().taskAssignee(KERMIT).singleResult();
        assertEquals(0,
                     task.getProcessVariables().size());
        assertEquals(1,
                     task.getTaskLocalVariables().size());
        assertEquals("test",
                     task.getTaskLocalVariables().get("localVar"));

        task = taskService.createTaskQuery().includeProcessVariables().taskAssignee(KERMIT).singleResult();
        assertEquals(3,
                     task.getProcessVariables().size());
        assertEquals(0,
                     task.getTaskLocalVariables().size());
        assertEquals(true,
                     task.getProcessVariables().get("processVar"));
        assertEquals(123,
                     task.getProcessVariables().get("anotherProcessVar"));
        assertEquals("This is a binary process variable",
                     new String((byte[]) task.getProcessVariables().get("binaryVariable")));

        tasks = taskService.createTaskQuery().includeTaskLocalVariables().taskCandidateUser(KERMIT,
                                                                                            KERMITSGROUPS).list();
        assertEquals(2,
                     tasks.size());
        assertEquals(2,
                     tasks.get(0).getTaskLocalVariables().size());
        assertEquals("test",
                     tasks.get(0).getTaskLocalVariables().get("test"));
        assertEquals(0,
                     tasks.get(0).getProcessVariables().size());

        tasks = taskService.createTaskQuery().includeProcessVariables().taskCandidateUser(KERMIT,
                                                                                          KERMITSGROUPS).list();
        assertEquals(2,
                     tasks.size());
        assertEquals(0,
                     tasks.get(0).getProcessVariables().size());
        assertEquals(0,
                     tasks.get(0).getTaskLocalVariables().size());

        task = taskService.createTaskQuery().includeTaskLocalVariables().taskAssignee(KERMIT).taskVariableValueEquals("localVar",
                                                                                                                      "test").singleResult();
        assertEquals(0,
                     task.getProcessVariables().size());
        assertEquals(1,
                     task.getTaskLocalVariables().size());
        assertEquals("test",
                     task.getTaskLocalVariables().get("localVar"));

        task = taskService.createTaskQuery().includeProcessVariables().taskAssignee(KERMIT).taskVariableValueEquals("localVar",
                                                                                                                    "test").singleResult();
        assertEquals(3,
                     task.getProcessVariables().size());
        assertEquals(0,
                     task.getTaskLocalVariables().size());
        assertEquals(true,
                     task.getProcessVariables().get("processVar"));
        assertEquals(123,
                     task.getProcessVariables().get("anotherProcessVar"));

        task = taskService.createTaskQuery().includeTaskLocalVariables().includeProcessVariables().taskAssignee(KERMIT).singleResult();
        assertEquals(3,
                     task.getProcessVariables().size());
        assertEquals(1,
                     task.getTaskLocalVariables().size());
        assertEquals("test",
                     task.getTaskLocalVariables().get("localVar"));
        assertEquals(true,
                     task.getProcessVariables().get("processVar"));
        assertEquals(123,
                     task.getProcessVariables().get("anotherProcessVar"));
        assertEquals("This is a binary process variable",
                     new String((byte[]) task.getProcessVariables().get("binaryVariable")));
    }

    /**
     * Generates some test tasks. - 2 tasks where kermit is a candidate and 1 task where gonzo is assignee
     */
    private List<String> generateTestTasks() throws Exception {
        List<String> ids = new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        // 2 tasks for kermit
        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("01/01/2001 01:01:01.000"));
        for (int i = 0; i < 2; i++) {
            Task task = taskService.newTask();
            task.setName("testTask");
            task.setDescription("testTask description");
            task.setPriority(3);
            taskService.saveTask(task);
            ids.add(task.getId());
            taskService.setVariableLocal(task.getId(),
                                         "test",
                                         "test");
            taskService.setVariableLocal(task.getId(),
                                         "testBinary",
                                         "This is a binary variable".getBytes());
            taskService.addCandidateUser(task.getId(),
                                         KERMIT);
        }

        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("02/02/2002 02:02:02.000"));
        // 1 task for gonzo
        Task task = taskService.newTask();
        task.setName("gonzoTask");
        task.setDescription("gonzo description");
        task.setPriority(4);
        task.setCategory("testCategory");
        taskService.saveTask(task);
        taskService.setAssignee(task.getId(),
                                GONZO);
        taskService.setVariableLocal(task.getId(),
                                     "testVar",
                                     "someVariable");
        taskService.setVariableLocal(task.getId(),
                                     "testVarBinary",
                                     "This is a binary variable".getBytes());
        taskService.setVariableLocal(task.getId(),
                                     "testVar2",
                                     123);
        ids.add(task.getId());

        return ids;
    }

    /**
     * Generates 100 test tasks.
     */
    private List<String> generateMultipleTestTasks() throws Exception {
        List<String> ids = new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("01/01/2001 01:01:01.000"));
        for (int i = 0; i < 100; i++) {
            Task task = taskService.newTask();
            task.setName("testTask");
            task.setDescription("testTask description");
            task.setPriority(3);
            taskService.saveTask(task);
            ids.add(task.getId());
            taskService.setVariableLocal(task.getId(),
                                         "test",
                                         "test");
            taskService.setVariableLocal(task.getId(),
                                         "testBinary",
                                         "This is a binary variable".getBytes());
            taskService.addCandidateUser(task.getId(),
                                         KERMIT);
        }
        return ids;
    }
}
