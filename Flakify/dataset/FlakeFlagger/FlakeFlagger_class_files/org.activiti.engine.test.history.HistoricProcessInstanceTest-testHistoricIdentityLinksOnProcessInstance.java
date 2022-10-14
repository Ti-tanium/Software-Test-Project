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

package org.activiti.engine.test.history;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**


 */
public class HistoricProcessInstanceTest extends PluggableActivitiTestCase {

  

  /*
   * @Deployment(resources = {"org/activiti/engine/test/history/oneTaskProcess.bpmn20.xml"}) public void testHistoricProcessInstanceVariables() { Map<String,Object> vars = new
   * HashMap<String,Object>(); vars.put("foo", "bar"); vars.put("baz", "boo");
   * 
   * runtimeService.startProcessInstanceByKey("oneTaskProcess", vars);
   * 
   * assertEquals(1, historyService.createHistoricProcessInstanceQuery().processVariableEquals ("foo", "bar").count()); assertEquals(1, historyService.createHistoricProcessInstanceQuery
   * ().processVariableEquals("baz", "boo").count()); assertEquals(1, historyService .createHistoricProcessInstanceQuery().processVariableEquals("foo", "bar").processVariableEquals("baz",
   * "boo").count()); }
   */

  @Deployment(resources={"org/activiti/engine/test/history/oneTaskProcess.bpmn20.xml"}) public void testHistoricIdentityLinksOnProcessInstance(){if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.AUDIT)){ProcessInstance pi=runtimeService.startProcessInstanceByKey("oneTaskProcess");runtimeService.addUserIdentityLink(pi.getId(),"kermit","myType");List<HistoricIdentityLink> historicLinks=historyService.getHistoricIdentityLinksForProcessInstance(pi.getId());assertEquals(1,historicLinks.size());assertEquals("myType",historicLinks.get(0).getType());assertEquals("kermit",historicLinks.get(0).getUserId());assertNull(historicLinks.get(0).getGroupId());assertEquals(pi.getId(),historicLinks.get(0).getProcessInstanceId());taskService.complete(taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult().getId());assertNull(runtimeService.createProcessInstanceQuery().processInstanceId(pi.getId()).singleResult());assertEquals(1,historyService.getHistoricIdentityLinksForProcessInstance(pi.getId()).size());historyService.deleteHistoricProcessInstance(pi.getId());assertEquals(0,historyService.getHistoricIdentityLinksForProcessInstance(pi.getId()).size());}}

}
