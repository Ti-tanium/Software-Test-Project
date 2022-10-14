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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.engine.test.Deployment;


public class HistoricVariableInstanceTest extends PluggableActivitiTestCase {

  @Deployment(resources="org/activiti/engine/test/history/HistoricVariableInstanceTest.testSimple.bpmn20.xml") public void testNativeHistoricVariableInstanceQuery(){if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.FULL)){assertEquals("ACT_HI_VARINST",managementService.getTableName(HistoricVariableInstance.class));assertEquals("ACT_HI_VARINST",managementService.getTableName(HistoricVariableInstanceEntity.class));String tableName=managementService.getTableName(HistoricVariableInstance.class);String baseQuerySql="SELECT * FROM " + tableName;Map<String, Object> variables=new HashMap<String, Object>();variables.put("var1","value1");variables.put("var2","value2");ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("myProc",variables);assertNotNull(processInstance);assertEquals(3,historyService.createNativeHistoricVariableInstanceQuery().sql(baseQuerySql).list().size());String sqlWithConditions=baseQuerySql + " where NAME_ = #{name}";assertEquals("test123",historyService.createNativeHistoricVariableInstanceQuery().sql(sqlWithConditions).parameter("name","myVar").singleResult().getValue());sqlWithConditions=baseQuerySql + " where NAME_ like #{name}";assertEquals(2,historyService.createNativeHistoricVariableInstanceQuery().sql(sqlWithConditions).parameter("name","var%").list().size());assertEquals(3,historyService.createNativeHistoricVariableInstanceQuery().sql(baseQuerySql).listPage(0,3).size());assertEquals(2,historyService.createNativeHistoricVariableInstanceQuery().sql(baseQuerySql).listPage(1,3).size());assertEquals(2,historyService.createNativeHistoricVariableInstanceQuery().sql(sqlWithConditions).parameter("name","var%").listPage(0,2).size());}}

  private HistoricVariableInstance getHistoricVariable(String variableName) {
    return historyService.createHistoricVariableInstanceQuery().variableName(variableName).singleResult();
  }
}
