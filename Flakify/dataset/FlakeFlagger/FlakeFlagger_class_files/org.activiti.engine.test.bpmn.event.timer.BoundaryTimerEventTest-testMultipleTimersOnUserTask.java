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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.TimerJobQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**

 */
public class BoundaryTimerEventTest extends PluggableActivitiTestCase {

  private static boolean listenerExecutedStartEvent;
  private static boolean listenerExecutedEndEvent;

  public static class MyExecutionListener implements ExecutionListener {
    private static final long serialVersionUID = 1L;

    public void notify(DelegateExecution execution) {
      if ("end".equals(execution.getEventName())) {
        listenerExecutedEndEvent = true;
      } else if ("start".equals(execution.getEventName())) {
        listenerExecutedStartEvent = true;
      }
    }
  }

  @Deployment public void testMultipleTimersOnUserTask(){Date startTime=new Date();ProcessInstance pi=runtimeService.startProcessInstanceByKey("multipleTimersOnUserTask");TimerJobQuery jobQuery=managementService.createTimerJobQuery().processInstanceId(pi.getId());List<Job> jobs=jobQuery.list();assertEquals(3,jobs.size());processEngineConfiguration.getClock().setCurrentTime(new Date(startTime.getTime() + ((60 * 60 * 1000) + 5000)));waitForJobExecutorToProcessAllJobs(5000L,25L);assertEquals(0L,jobQuery.count());Task task=taskService.createTaskQuery().singleResult();assertEquals("Third Task",task.getName());}

}
