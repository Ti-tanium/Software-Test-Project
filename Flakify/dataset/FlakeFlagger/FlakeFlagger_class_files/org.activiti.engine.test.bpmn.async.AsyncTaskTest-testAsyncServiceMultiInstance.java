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
package org.activiti.engine.test.bpmn.async;

import java.util.Date;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;

/**
 * 

 */
public class AsyncTaskTest extends PluggableActivitiTestCase {

  public static boolean INVOCATION;

  @Deployment public void testAsyncServiceMultiInstance(){INVOCATION=false;runtimeService.startProcessInstanceByKey("asyncService");assertEquals(1,managementService.createJobQuery().count());assertFalse(INVOCATION);waitForJobExecutorToProcessAllJobs(5000L,100L);assertTrue(INVOCATION);assertEquals(0,managementService.createJobQuery().count());}

}
