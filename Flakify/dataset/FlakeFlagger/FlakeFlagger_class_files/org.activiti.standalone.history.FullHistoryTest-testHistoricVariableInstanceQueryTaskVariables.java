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

package org.activiti.standalone.history;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.test.ResourceActivitiTestCase;
import org.activiti.engine.impl.variable.EntityManagerSession;
import org.activiti.engine.impl.variable.EntityManagerSessionFactory;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.api.runtime.DummySerializable;
import org.activiti.engine.test.history.SerializableVariable;
import org.activiti.standalone.jpa.FieldAccessJPAEntity;

public class FullHistoryTest extends ResourceActivitiTestCase {

    @Deployment(resources={"org/activiti/engine/test/history/oneTaskProcess.bpmn20.xml"}) public void testHistoricVariableInstanceQueryTaskVariables(){Map<String, Object> variables=new HashMap<String, Object>();variables.put("variable","setFromProcess");ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("oneTaskProcess",variables);assertEquals(1,historyService.createHistoricVariableInstanceQuery().count());Task activeTask=taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();assertNotNull(activeTask);taskService.setVariableLocal(activeTask.getId(),"variable","setFromTask");assertEquals(2,historyService.createHistoricVariableInstanceQuery().count());assertEquals(1,historyService.createHistoricVariableInstanceQuery().taskId(activeTask.getId()).count());assertEquals("setFromTask",historyService.createHistoricVariableInstanceQuery().taskId(activeTask.getId()).singleResult().getValue());assertEquals(activeTask.getId(),historyService.createHistoricVariableInstanceQuery().taskId(activeTask.getId()).singleResult().getTaskId());assertEquals(1,historyService.createHistoricVariableInstanceQuery().excludeTaskVariables().count());try {historyService.createHistoricVariableInstanceQuery().taskId(null).singleResult();fail("Exception expected");} catch (ActivitiIllegalArgumentException ae){assertEquals("taskId is null",ae.getMessage());}try {historyService.createHistoricVariableInstanceQuery().taskId("123").excludeTaskVariables().singleResult();fail("Exception expected");} catch (ActivitiIllegalArgumentException ae){assertEquals("Cannot use taskId together with excludeTaskVariables",ae.getMessage());}try {historyService.createHistoricVariableInstanceQuery().excludeTaskVariables().taskId("123").singleResult();fail("Exception expected");} catch (ActivitiIllegalArgumentException ae){assertEquals("Cannot use taskId together with excludeTaskVariables",ae.getMessage());}}
}
