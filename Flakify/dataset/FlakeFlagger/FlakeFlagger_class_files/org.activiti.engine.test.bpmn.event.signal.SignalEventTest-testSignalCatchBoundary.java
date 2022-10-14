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

package org.activiti.engine.test.bpmn.event.signal;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.EventSubscriptionQueryImpl;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti.validation.validator.Problems;

/**

 */
public class SignalEventTest extends PluggableActivitiTestCase {

  @Deployment(resources={"org/activiti/engine/test/bpmn/event/signal/SignalEventTests.catchAlertSignalBoundary.bpmn20.xml","org/activiti/engine/test/bpmn/event/signal/SignalEventTests.throwAlertSignal.bpmn20.xml"}) public void testSignalCatchBoundary(){runtimeService.startProcessInstanceByKey("catchSignal");assertEquals(1,createEventSubscriptionQuery().count());assertEquals(1,runtimeService.createProcessInstanceQuery().count());runtimeService.startProcessInstanceByKey("throwSignal");assertEquals(0,createEventSubscriptionQuery().count());assertEquals(0,runtimeService.createProcessInstanceQuery().count());}

  private EventSubscriptionQueryImpl createEventSubscriptionQuery() {
    return new EventSubscriptionQueryImpl(processEngineConfiguration.getCommandExecutor());
  }

  private void validateTaskCounts(long taskACount, long taskBCount, long taskCCount) {
    assertEquals(taskACount, taskService.createTaskQuery().taskName("Task A").count());
    assertEquals(taskBCount, taskService.createTaskQuery().taskName("Task B").count());
    assertEquals(taskCCount, taskService.createTaskQuery().taskName("Task C").count());
  }

}
