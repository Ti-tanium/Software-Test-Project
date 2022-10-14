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
import java.util.List;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

/**
 * Tests for cub-tasks querying
 * @see TaskQueryTest
 */
public class SubTaskQueryTest extends PluggableActivitiTestCase {

    private List<String> taskIds;

    private static final String KERMIT = "kermit";

    private static final String GONZO = "gonzo";

    public void testTaskQueryParentTask() throws Exception {
		Task rootTask = taskService.newTask();
		taskService.saveTask(rootTask);
		taskIds.add(rootTask.getId());
		Task subTask = taskService.newTask();
		subTask.setParentTaskId(rootTask.getId());
		taskService.saveTask(subTask);
		taskIds.add(subTask.getId());
		subTask = taskService.newTask();
		subTask.setAssignee("gonzo");
		subTask.setParentTaskId(rootTask.getId());
		taskService.saveTask(subTask);
		taskIds.add(subTask.getId());
		TaskQuery query = taskService.createTaskQuery().taskParentTaskId(rootTask.getId());
		assertEquals(2, query.count());
		query = taskService.createTaskQuery().taskAssignee("gonzo").taskParentTaskId(rootTask.getId());
		assertEquals(1, query.count());
		query = taskService.createTaskQuery().taskAssignee("kermit").taskParentTaskId(rootTask.getId());
		assertEquals(0, query.count());
		query = taskService.createTaskQuery().taskAssignee("gonzo").or().taskName("A sub task")
				.taskParentTaskId(rootTask.getId()).endOr();
		assertEquals(1, query.count());
	}

    /**
     * Generates some test sub-tasks to the tasks generated by {@link #generateTestTasks()}.<br/>
     * - 1 root task where kermit is a candidate with 2 subtasks (both with kermit as candidate) <br/>
     * - 2 root task where gonzo is assignee with 3 + 2 subtasks assigned to gonzo
     */
    private List<String> generateTestSubTasks() throws Exception {
        List<String> ids = new ArrayList<String>();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
        // 1 parent task for kermit
        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("01/01/2008 01:01:01.000"));
        Task rootTask1 = taskService.newTask();
        rootTask1.setName("rootTestTask");
        rootTask1.setDescription("rootTestTask description");
        taskService.saveTask(rootTask1);
        ids.add(rootTask1.getId());
        taskService.addCandidateUser(rootTask1.getId(),
                                     KERMIT);
        // 2 sub-tasks for the task above
        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("01/01/2009 01:01:01.000"));
        for (int i = 1; i <= 2; i++) {
            Task subtask = taskService.newTask();
            subtask.setName("kermitSubTask" + i);
            subtask.setParentTaskId(rootTask1.getId());
            subtask.setDescription("description for kermit sub-task" + i);
            taskService.saveTask(subtask);
            taskService.addCandidateUser(subtask.getId(),
                                         KERMIT);
            ids.add(subtask.getId());
        }

        // 2 parent tasks for gonzo
        // first parent task for gonzo
        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("01/02/2008 02:02:02.000"));
        Task rootTask2 = taskService.newTask();
        rootTask2.setName("gonzoRootTask1");
        rootTask2.setDescription("gonzo Root task1 description");
        taskService.saveTask(rootTask2);
        taskService.setAssignee(rootTask2.getId(),
                                GONZO);
        ids.add(rootTask2.getId());
        // second parent task for gonzo
        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("05/02/2008 02:02:02.000"));
        Task rootTask3 = taskService.newTask();
        rootTask3.setName("gonzoRootTask2");
        rootTask3.setDescription("gonzo Root task2 description");
        taskService.saveTask(rootTask3);
        taskService.setAssignee(rootTask3.getId(),
                                GONZO);
        ids.add(rootTask3.getId());
        // 3 sub-tasks for the first parent task
        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("01/01/2009 01:01:01.000"));
        for (int i = 1; i <= 3; i++) {
            Task subtask = taskService.newTask();
            subtask.setName("gonzoSubTask1_" + i);
            subtask.setParentTaskId(rootTask2.getId());
            subtask.setDescription("description for gonzo sub-task1_" + i);
            taskService.saveTask(subtask);
            taskService.setAssignee(subtask.getId(),
                                    GONZO);
            ids.add(subtask.getId());
        }
        // 2 sub-tasks for the second parent task
        processEngineConfiguration.getClock().setCurrentTime(sdf.parse("02/01/2009 01:01:01.000"));
        for (int i = 1; i <= 2; i++) {
            Task subtask = taskService.newTask();
            subtask.setName("gonzoSubTask2_" + i);
            subtask.setParentTaskId(rootTask3.getId());
            subtask.setDescription("description for gonzo sub-task2_" + i);
            taskService.saveTask(subtask);
            taskService.setAssignee(subtask.getId(),
                                    GONZO);
            ids.add(subtask.getId());
        }
        return ids;
    }
}
