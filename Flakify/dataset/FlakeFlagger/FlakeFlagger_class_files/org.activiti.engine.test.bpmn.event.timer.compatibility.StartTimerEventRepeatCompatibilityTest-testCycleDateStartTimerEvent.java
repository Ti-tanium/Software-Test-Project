package org.activiti.engine.test.bpmn.event.timer.compatibility;

import java.util.Calendar;
import java.util.List;

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

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.runtime.Clock;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.api.event.TestActivitiEntityEventListener;

public class StartTimerEventRepeatCompatibilityTest extends TimerEventCompatibilityTest {

  private TestActivitiEntityEventListener listener;

  /**
   * Timer repetition
   */
  public void testCycleDateStartTimerEvent() throws Exception {
    Clock previousClock = processEngineConfiguration.getClock();

    Clock testClock = new DefaultClockImpl();

    processEngineConfiguration.setClock(testClock);

    Calendar calendar = Calendar.getInstance();
    calendar.set(2025, Calendar.DECEMBER, 10, 0, 0, 0);
    testClock.setCurrentTime(calendar.getTime());

    // deploy the process
    repositoryService.createDeployment().addClasspathResource("org/activiti/engine/test/bpmn/event/timer/StartTimerEventRepeatWithoutEndDateTest.testCycleDateStartTimerEvent.bpmn20.xml").deploy();
    assertEquals(1, repositoryService.createProcessDefinitionQuery().count());

    // AFTER DEPLOYMENT
    // when the process is deployed there will be created a timerStartEvent job which will wait to be executed.
    List<Job> jobs = managementService.createTimerJobQuery().list();
    assertEquals(1, jobs.size());

    // dueDate should be after 24 hours from the process deployment
    Calendar dueDateCalendar = Calendar.getInstance();
    dueDateCalendar.set(2025, Calendar.DECEMBER, 11, 0, 0, 0);

    // check the due date is inside the 2 seconds range
    assertEquals(true, Math.abs(dueDateCalendar.getTime().getTime() - jobs.get(0).getDuedate().getTime()) < 2000);

    // No process instances
    List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
    assertEquals(0, processInstances.size());

    // No tasks
    List<Task> tasks = taskService.createTaskQuery().list();
    assertEquals(0, tasks.size());

    // ADVANCE THE CLOCK
    // advance the clock after 9 days from starting the process ->
    // the system will execute the pending job and will create a new one (day by day)
    moveByMinutes(9 * 60 * 24);
    executeJobExecutorForTime(10000, 200);
    
    // there must be a pending job because the endDate is not reached yet
    jobs = managementService.createTimerJobQuery().list();
    assertEquals(1, jobs.size());

    // After time advanced 9 days there should be 9 process instance started
    processInstances = runtimeService.createProcessInstanceQuery().list();
    assertEquals(9, processInstances.size());

    // 9 task to be executed (the userTask "Task A")
    tasks = taskService.createTaskQuery().list();
    assertEquals(9, tasks.size());

    // one new job will be created (and the old one will be deleted after execution)
    jobs = managementService.createTimerJobQuery().list();
    assertEquals(1, jobs.size());

    // check if the last job to be executed has the dueDate set correctly
    // (10'th repeat after 10 dec. => dueDate must have DueDate = 20 dec.)
    dueDateCalendar = Calendar.getInstance();
    dueDateCalendar.set(2025, Calendar.DECEMBER, 20, 0, 0, 0);
    assertEquals(true, Math.abs(dueDateCalendar.getTime().getTime() - jobs.get(0).getDuedate().getTime()) < 2000);

    // ADVANCE THE CLOCK SO that all 10 repeats to be executed (last execution)
    moveByMinutes(60 * 24);
    try {
      waitForJobExecutorToProcessAllJobsAndExecutableTimerJobs(2000, 200);
    } catch (Exception e) {
      fail("Because the maximum number of repeats is reached it will not be executed other jobs");
    }
    
    // After the 10nth startEvent Execution should have 10 process instances started
    // (since the first one was not completed)
    processInstances = runtimeService.createProcessInstanceQuery().list();
    assertEquals(10, processInstances.size());

    // the current job will be deleted after execution and a new one will
    // not be created. (all 10 has already executed)
    jobs = managementService.createJobQuery().list();
    assertEquals(0, jobs.size());

    // 10 tasks to be executed (the userTask "Task A")
    // one task for each process instance
    tasks = taskService.createTaskQuery().list();
    assertEquals(10, tasks.size());

    // FINAL CHECK
    // count "timer fired" events
    int timerFiredCount = 0;
    List<ActivitiEvent> eventsReceived = listener.getEventsReceived();
    for (ActivitiEvent eventReceived : eventsReceived) {
      if (ActivitiEventType.TIMER_FIRED.equals(eventReceived.getType())) {
        timerFiredCount++;
      }
    }

    // count "entity created" events
    int eventCreatedCount = 0;
    for (ActivitiEvent eventReceived : eventsReceived) {
      if (ActivitiEventType.ENTITY_CREATED.equals(eventReceived.getType())) {
        eventCreatedCount++;
      }
    }

    // count "entity deleted" events
    int eventDeletedCount = 0;
    for (ActivitiEvent eventReceived : eventsReceived) {
      if (ActivitiEventType.ENTITY_DELETED.equals(eventReceived.getType())) {
        eventDeletedCount++;
      }
    }
    assertEquals(10, timerFiredCount); // 10 timers fired
    assertEquals(20, eventCreatedCount); // 20 job entities created, 2 per job (timer and executable job)
    assertEquals(20, eventDeletedCount); // 20 jobs entities deleted, 2 per job (timer and executable job)

    // for each processInstance
    // let's complete the userTasks where the process is hanging in order to complete the processes.
    for (ProcessInstance processInstance : processInstances) {
      tasks = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).list();
      Task task = tasks.get(0);
      assertEquals("Task A", task.getName());
      assertEquals(1, tasks.size());
      taskService.complete(task.getId());
    }

    // now All the process instances should be completed
    processInstances = runtimeService.createProcessInstanceQuery().list();
    assertEquals(0, processInstances.size());

    // no jobs
    jobs = managementService.createJobQuery().list();
    assertEquals(0, jobs.size());
    
    jobs = managementService.createTimerJobQuery().list();
    assertEquals(0, jobs.size());

    // no tasks
    tasks = taskService.createTaskQuery().list();
    assertEquals(0, tasks.size());

    listener.clearEventsReceived();
    processEngineConfiguration.setClock(previousClock);

    repositoryService.deleteDeployment(repositoryService.createDeploymentQuery().singleResult().getId(), true);

  }

}
