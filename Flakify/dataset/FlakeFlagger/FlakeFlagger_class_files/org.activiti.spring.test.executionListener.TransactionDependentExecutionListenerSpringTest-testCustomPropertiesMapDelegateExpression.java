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

package org.activiti.spring.test.executionListener;

import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti.spring.impl.test.SpringActivitiTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**

 */
@ContextConfiguration("classpath:org/activiti/spring/test/executionListener/TransactionDependentListenerTest-context.xml")
public class TransactionDependentExecutionListenerSpringTest extends SpringActivitiTestCase {

  @Autowired
  MyTransactionDependentExecutionListener listener;

  @Deployment public void testCustomPropertiesMapDelegateExpression(){runtimeService.startProcessInstanceByKey("transactionDependentExecutionListenerProcess");Task task=taskService.createTaskQuery().singleResult();taskService.complete(task.getId());assertEquals("task3",listener.getCurrentActivities().get(0).getActivityId());assertEquals("task3",listener.getCurrentActivities().get(0).getCustomPropertiesMap().get("customProp1"));task=taskService.createTaskQuery().singleResult();taskService.complete(task.getId());assertEquals("task4",listener.getCurrentActivities().get(1).getActivityId());assertEquals("task4",listener.getCurrentActivities().get(1).getCustomPropertiesMap().get("customProp1"));}

}
