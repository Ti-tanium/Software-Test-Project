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
package org.activiti.engine.test.bpmn.event.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**

 */
public class BoundaryTimerEventRepeatWithEndTest extends PluggableActivitiTestCase {

  @Deployment public void testRepeatWithEnd() throws Throwable{Calendar calendar=Calendar.getInstance();Date baseTime=calendar.getTime();calendar.add(Calendar.MINUTE,20);DateTimeFormatter fmt=ISODateTimeFormat.dateTime();DateTime dt=new DateTime(calendar.getTime());String dateStr=fmt.print(dt);Calendar nextTimeCal=Calendar.getInstance();nextTimeCal.setTime(baseTime);processEngineConfiguration.getClock().setCurrentTime(nextTimeCal.getTime());ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("repeatWithEnd");runtimeService.setVariable(processInstance.getId(),"EndDateForBoundary",dateStr);List<Task> tasks=taskService.createTaskQuery().list();assertEquals(1,tasks.size());Task task=tasks.get(0);assertEquals("Task A",task.getName());taskService.complete(task.getId());List<Job> jobs=managementService.createTimerJobQuery().list();assertEquals(1,jobs.size());Job executableJob=managementService.moveTimerToExecutableJob(jobs.get(0).getId());managementService.executeJob(executableJob.getId());assertEquals(0,managementService.createJobQuery().list().size());jobs=managementService.createTimerJobQuery().list();assertEquals(1,jobs.size());nextTimeCal.add(Calendar.MINUTE,15);processEngineConfiguration.getClock().setCurrentTime(nextTimeCal.getTime());executableJob=managementService.moveTimerToExecutableJob(jobs.get(0).getId());managementService.executeJob(executableJob.getId());assertEquals(0,managementService.createJobQuery().list().size());jobs=managementService.createTimerJobQuery().list();assertEquals(1,jobs.size());nextTimeCal.add(Calendar.MINUTE,5);nextTimeCal.add(Calendar.SECOND,1);processEngineConfiguration.getClock().setCurrentTime(nextTimeCal.getTime());executableJob=managementService.moveTimerToExecutableJob(jobs.get(0).getId());managementService.executeJob(executableJob.getId());jobs=managementService.createTimerJobQuery().list();assertEquals(0,jobs.size());jobs=managementService.createJobQuery().list();assertEquals(0,jobs.size());tasks=taskService.createTaskQuery().list();task=tasks.get(0);assertEquals("Task B",task.getName());assertEquals(1,tasks.size());taskService.complete(task.getId());jobs=managementService.createTimerJobQuery().list();assertEquals(0,jobs.size());jobs=managementService.createJobQuery().list();assertEquals(0,jobs.size());if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.ACTIVITY)){HistoricProcessInstance historicInstance=historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstance.getId()).singleResult();assertNotNull(historicInstance.getEndTime());}List<ProcessInstance> processInstances=runtimeService.createProcessInstanceQuery().list();assertEquals(0,processInstances.size());jobs=managementService.createJobQuery().list();assertEquals(0,jobs.size());jobs=managementService.createTimerJobQuery().list();assertEquals(0,jobs.size());tasks=taskService.createTaskQuery().list();assertEquals(0,tasks.size());}

}
