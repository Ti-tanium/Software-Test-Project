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

    @Deployment public void testHistoricVariableUpdatesAllTypes() throws Exception{SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss SSS");Map<String, Object> variables=new HashMap<String, Object>();variables.put("aVariable","initial value");Date startedDate=sdf.parse("01/01/2001 01:23:45 000");Date updatedDate=sdf.parse("01/01/2001 01:23:46 000");processEngineConfiguration.getClock().setCurrentTime(startedDate);ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("HistoricVariableUpdateProcess",variables);List<HistoricDetail> details=historyService.createHistoricDetailQuery().variableUpdates().processInstanceId(processInstance.getId()).orderByVariableName().asc().orderByTime().asc().list();assertEquals(9,details.size());HistoricVariableUpdate startVarUpdate=(HistoricVariableUpdate)details.get(0);assertEquals("aVariable",startVarUpdate.getVariableName());assertEquals("initial value",startVarUpdate.getValue());assertEquals(0,startVarUpdate.getRevision());assertEquals(processInstance.getId(),startVarUpdate.getProcessInstanceId());assertEquals(startedDate,startVarUpdate.getTime());HistoricVariableUpdate updatedStringVariable=(HistoricVariableUpdate)details.get(1);assertEquals("aVariable",updatedStringVariable.getVariableName());assertEquals("updated value",updatedStringVariable.getValue());assertEquals(processInstance.getId(),updatedStringVariable.getProcessInstanceId());assertEquals(updatedDate,updatedStringVariable.getTime());HistoricVariableUpdate intVariable=(HistoricVariableUpdate)details.get(2);assertEquals("bVariable",intVariable.getVariableName());assertEquals(123,intVariable.getValue());assertEquals(processInstance.getId(),intVariable.getProcessInstanceId());assertEquals(updatedDate,intVariable.getTime());HistoricVariableUpdate longVariable=(HistoricVariableUpdate)details.get(3);assertEquals("cVariable",longVariable.getVariableName());assertEquals(12345L,longVariable.getValue());assertEquals(processInstance.getId(),longVariable.getProcessInstanceId());assertEquals(updatedDate,longVariable.getTime());HistoricVariableUpdate doubleVariable=(HistoricVariableUpdate)details.get(4);assertEquals("dVariable",doubleVariable.getVariableName());assertEquals(1234.567,doubleVariable.getValue());assertEquals(processInstance.getId(),doubleVariable.getProcessInstanceId());assertEquals(updatedDate,doubleVariable.getTime());HistoricVariableUpdate shortVariable=(HistoricVariableUpdate)details.get(5);assertEquals("eVariable",shortVariable.getVariableName());assertEquals((short)12,shortVariable.getValue());assertEquals(processInstance.getId(),shortVariable.getProcessInstanceId());assertEquals(updatedDate,shortVariable.getTime());HistoricVariableUpdate dateVariable=(HistoricVariableUpdate)details.get(6);assertEquals("fVariable",dateVariable.getVariableName());assertEquals(sdf.parse("01/01/2001 01:23:45 678"),dateVariable.getValue());assertEquals(processInstance.getId(),dateVariable.getProcessInstanceId());assertEquals(updatedDate,dateVariable.getTime());HistoricVariableUpdate serializableVariable=(HistoricVariableUpdate)details.get(7);assertEquals("gVariable",serializableVariable.getVariableName());assertEquals(new SerializableVariable("hello hello"),serializableVariable.getValue());assertEquals(processInstance.getId(),serializableVariable.getProcessInstanceId());assertEquals(updatedDate,serializableVariable.getTime());HistoricVariableUpdate byteArrayVariable=(HistoricVariableUpdate)details.get(8);assertEquals("hVariable",byteArrayVariable.getVariableName());assertEquals(";-)",new String((byte[])byteArrayVariable.getValue()));assertEquals(processInstance.getId(),byteArrayVariable.getProcessInstanceId());assertEquals(updatedDate,byteArrayVariable.getTime());List<Task> tasks=taskService.createTaskQuery().list();assertEquals(1,tasks.size());taskService.complete(tasks.get(0).getId());assertProcessEnded(processInstance.getId());HistoricVariableInstanceQuery historicProcessVariableQuery=historyService.createHistoricVariableInstanceQuery().orderByVariableName().asc();assertEquals(8,historicProcessVariableQuery.count());List<HistoricVariableInstance> historicVariables=historicProcessVariableQuery.list();HistoricVariableInstance historicVariable=historicVariables.get(0);assertEquals("aVariable",historicVariable.getVariableName());assertEquals("updated value",historicVariable.getValue());assertEquals(processInstance.getId(),historicVariable.getProcessInstanceId());historicVariable=historicVariables.get(1);assertEquals("bVariable",historicVariable.getVariableName());assertEquals(123,historicVariable.getValue());assertEquals(processInstance.getId(),historicVariable.getProcessInstanceId());historicVariable=historicVariables.get(2);assertEquals("cVariable",historicVariable.getVariableName());assertEquals(12345L,historicVariable.getValue());assertEquals(processInstance.getId(),historicVariable.getProcessInstanceId());historicVariable=historicVariables.get(3);assertEquals("dVariable",historicVariable.getVariableName());assertEquals(1234.567,historicVariable.getValue());assertEquals(processInstance.getId(),historicVariable.getProcessInstanceId());historicVariable=historicVariables.get(4);assertEquals("eVariable",historicVariable.getVariableName());assertEquals((short)12,historicVariable.getValue());assertEquals(processInstance.getId(),historicVariable.getProcessInstanceId());historicVariable=historicVariables.get(5);assertEquals("fVariable",historicVariable.getVariableName());assertEquals(sdf.parse("01/01/2001 01:23:45 678"),historicVariable.getValue());assertEquals(processInstance.getId(),historicVariable.getProcessInstanceId());historicVariable=historicVariables.get(6);assertEquals("gVariable",historicVariable.getVariableName());assertEquals(new SerializableVariable("hello hello"),historicVariable.getValue());assertEquals(processInstance.getId(),historicVariable.getProcessInstanceId());historicVariable=historicVariables.get(7);assertEquals("hVariable",historicVariable.getVariableName());assertEquals(";-)",";-)",new String((byte[])historicVariable.getValue()));assertEquals(processInstance.getId(),historicVariable.getProcessInstanceId());}
}
