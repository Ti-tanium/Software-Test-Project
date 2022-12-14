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

package org.activiti.standalone.el;

import javax.el.PropertyNotFoundException;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.test.ResourceActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;

/**

 */
public class ExpressionBeanAccessTest extends ResourceActivitiTestCase {

  @Deployment public void testConfigurationBeanAccess(){ProcessInstance pi=runtimeService.startProcessInstanceByKey("expressionBeanAccess");assertEquals("I'm exposed",runtimeService.getVariable(pi.getId(),"exposedBeanResult"));try {runtimeService.trigger(runtimeService.createExecutionQuery().processInstanceId(pi.getId()).onlyChildExecutions().singleResult().getId());fail("Exception expected");} catch (ActivitiException ae){assertNotNull(ae.getCause());assertTrue(ae.getCause() instanceof RuntimeException);RuntimeException runtimeException=(RuntimeException)ae.getCause();assertTrue(runtimeException.getCause() instanceof PropertyNotFoundException);}}
}
