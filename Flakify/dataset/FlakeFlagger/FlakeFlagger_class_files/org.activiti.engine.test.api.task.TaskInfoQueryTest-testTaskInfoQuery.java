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

import java.util.Date;
import java.util.List;

import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskInfoQueryWrapper;

/**

 */
public class TaskInfoQueryTest extends PluggableActivitiTestCase {

  protected void tearDown() throws Exception {
    for (Task task : taskService.createTaskQuery().list()) {
      taskService.deleteTask(task.getId(), true);
    }
  }

  public void testTaskInfoQuery(){Date now=processEngineConfiguration.getClock().getCurrentTime();createTask("task0",new Date(now.getTime() + (4L * 7L * 24L * 60L * 60L * 1000L)));createTask("task1",new Date(now.getTime() + (2 * 24L * 60L * 60L * 1000L)));createTask("task2",new Date(now.getTime() + (7L * 24L * 60L * 60L * 1000L)));createTask("task3",new Date(now.getTime() + (24L * 60L * 60L * 1000L)));createTask("task4",null);createTask("task5",null);TaskInfoQueryWrapper taskInfoQueryWrapper=new TaskInfoQueryWrapper(taskService.createTaskQuery());List<? extends TaskInfo> taskInfos=taskInfoQueryWrapper.getTaskInfoQuery().or().taskNameLike("%k1%").taskDueAfter(new Date(now.getTime() + (3 * 24L * 60L * 60L * 1000L))).endOr().list();assertEquals(3,taskInfos.size());if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.AUDIT)){taskInfoQueryWrapper=new TaskInfoQueryWrapper(historyService.createHistoricTaskInstanceQuery());taskInfos=taskInfoQueryWrapper.getTaskInfoQuery().or().taskNameLike("%k1%").taskDueAfter(new Date(now.getTime() + (3 * 24L * 60L * 60L * 1000L))).endOr().list();assertEquals(3,taskInfos.size());}}

  private Task createTask(String name, Date dueDate) {
    Task task = taskService.newTask();
    task.setName(name);
    task.setDueDate(dueDate);
    taskService.saveTask(task);
    return task;
  }

}
