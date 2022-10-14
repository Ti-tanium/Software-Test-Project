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
package org.activiti.engine.test.api.v6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * These are the first tests ever written for Activiti 6.
 * Keeping them here for nostalgic reasons.
 */
public class Activiti6Test extends PluggableActivitiTestCase {

    @Test @org.activiti.engine.test.Deployment public void testLongServiceTaskLoop(){int maxCount=3210;Map<String, Object> vars=new HashMap<String, Object>();vars.put("counter",new Integer(0));vars.put("maxCount",maxCount);ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("testLongServiceTaskLoop",vars);assertNotNull(processInstance);assertTrue(processInstance.isEnded());assertEquals(maxCount,CountingServiceTaskTestDelegate.CALL_COUNT.get());assertEquals(0,runtimeService.createExecutionQuery().count());if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.AUDIT)){assertEquals(maxCount,historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstance.getId()).activityId("serviceTask").count());}}
}
