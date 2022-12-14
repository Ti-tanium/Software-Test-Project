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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**


 */
public class SubTaskTest extends PluggableActivitiTestCase {

  public void testSubTask(){Task gonzoTask=taskService.newTask();gonzoTask.setName("gonzoTask");taskService.saveTask(gonzoTask);Task subTaskOne=taskService.newTask();subTaskOne.setName("subtask one");String gonzoTaskId=gonzoTask.getId();subTaskOne.setParentTaskId(gonzoTaskId);taskService.saveTask(subTaskOne);Task subTaskTwo=taskService.newTask();subTaskTwo.setName("subtask two");subTaskTwo.setParentTaskId(gonzoTaskId);taskService.saveTask(subTaskTwo);String subTaskId=subTaskOne.getId();assertTrue(taskService.getSubTasks(subTaskId).isEmpty());assertTrue(historyService.createHistoricTaskInstanceQuery().taskParentTaskId(subTaskId).list().isEmpty());List<Task> subTasks=taskService.getSubTasks(gonzoTaskId);Set<String> subTaskNames=new HashSet<String>();for (Task subTask:subTasks){subTaskNames.add(subTask.getName());}if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.AUDIT)){Set<String> expectedSubTaskNames=new HashSet<String>();expectedSubTaskNames.add("subtask one");expectedSubTaskNames.add("subtask two");assertEquals(expectedSubTaskNames,subTaskNames);List<HistoricTaskInstance> historicSubTasks=historyService.createHistoricTaskInstanceQuery().taskParentTaskId(gonzoTaskId).list();subTaskNames=new HashSet<String>();for (HistoricTaskInstance historicSubTask:historicSubTasks){subTaskNames.add(historicSubTask.getName());}assertEquals(expectedSubTaskNames,subTaskNames);}taskService.deleteTask(gonzoTaskId,true);}

}
