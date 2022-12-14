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

package org.activiti.engine.test.bpmn.usertask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.calendar.BusinessCalendar;
import org.activiti.engine.impl.test.ResourceActivitiTestCase;
import org.activiti.engine.runtime.Clock;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.joda.time.Period;

/**

 */
public class TaskDueDateExtensionsTest extends ResourceActivitiTestCase {
  
  @Deployment public void testRelativeDueDateStringExtension() throws Exception{Clock clock=processEngineConfiguration.getClock();clock.setCurrentCalendar(new GregorianCalendar(2015,0,1));Map<String, Object> variables=new HashMap<String, Object>();variables.put("dateVariable","P2DT5H40M");ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("dueDateExtension",variables);Task task=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();assertNotNull(task.getDueDate());Period period=new Period(task.getCreateTime().getTime(),task.getDueDate().getTime());assertEquals(2,period.getDays());assertEquals(5,period.getHours());assertEquals(40,period.getMinutes());clock.reset();}
  
  public static class CustomBusinessCalendar implements BusinessCalendar {

    @Override
    public Date resolveDuedate(String duedateDescription) {
      return new Date(0);
    }

    @Override
    public Date resolveDuedate(String duedateDescription, int maxIterations) {
      return new Date(0);
    }

    @Override
    public Boolean validateDuedate(String duedateDescription, int maxIterations, Date endDate, Date newTimer) {
      return true;
    }

    @Override
    public Date resolveEndDate(String endDateString) {
      return new Date(0);
    }

  }
}
