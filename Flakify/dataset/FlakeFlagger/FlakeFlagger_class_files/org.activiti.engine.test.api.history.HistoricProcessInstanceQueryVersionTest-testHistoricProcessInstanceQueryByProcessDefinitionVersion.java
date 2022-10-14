package org.activiti.engine.test.api.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;

public class HistoricProcessInstanceQueryVersionTest extends PluggableActivitiTestCase{

  private static final String PROCESS_DEFINITION_KEY = "oneTaskProcess";
  private static final String DEPLOYMENT_FILE_PATH = "org/activiti/engine/test/api/oneTaskProcess.bpmn20.xml";

  private org.activiti.engine.repository.Deployment oldDeployment;
  private org.activiti.engine.repository.Deployment newDeployment;
  private List<String> processInstanceIds;
  
  protected void setUp() throws Exception {
    super.setUp();
    oldDeployment = repositoryService.createDeployment()
      .addClasspathResource(DEPLOYMENT_FILE_PATH)
      .deploy();
    
    processInstanceIds = new ArrayList<String>();
    
    Map<String, Object> startMap = new HashMap<String, Object>();
    startMap.put("test", 123);
    processInstanceIds.add(runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, startMap).getId());
    
    newDeployment = repositoryService.createDeployment()
          .addClasspathResource(DEPLOYMENT_FILE_PATH)
          .deploy();
    
    startMap.clear();
    startMap.put("anothertest", 456);
    processInstanceIds.add(runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, startMap).getId());
  }
  
  protected void tearDown() throws Exception {
    repositoryService.deleteDeployment(oldDeployment.getId(), true);
    repositoryService.deleteDeployment(newDeployment.getId(), true);
  }
  
  public void testHistoricProcessInstanceQueryByProcessDefinitionVersion(){assertEquals(1,historyService.createHistoricProcessInstanceQuery().processDefinitionVersion(1).list().get(0).getProcessDefinitionVersion().intValue());assertEquals(2,historyService.createHistoricProcessInstanceQuery().processDefinitionVersion(2).list().get(0).getProcessDefinitionVersion().intValue());assertEquals(1,historyService.createHistoricProcessInstanceQuery().processDefinitionVersion(1).count());assertEquals(1,historyService.createHistoricProcessInstanceQuery().processDefinitionVersion(2).count());assertEquals(0,historyService.createHistoricProcessInstanceQuery().processDefinitionVersion(3).count());assertEquals(1,historyService.createHistoricProcessInstanceQuery().processDefinitionVersion(1).count());assertEquals(1,historyService.createHistoricProcessInstanceQuery().processDefinitionVersion(2).list().size());assertEquals(0,historyService.createHistoricProcessInstanceQuery().processDefinitionVersion(3).list().size());if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.ACTIVITY)){HistoricProcessInstance processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().variableValueEquals("test",123).processDefinitionVersion(1).singleResult();assertEquals(1,processInstance.getProcessDefinitionVersion().intValue());Map<String, Object> variableMap=processInstance.getProcessVariables();assertEquals(123,variableMap.get("test"));processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().variableValueEquals("anothertest",456).processDefinitionVersion(1).singleResult();assertNull(processInstance);processInstance=historyService.createHistoricProcessInstanceQuery().includeProcessVariables().variableValueEquals("anothertest",456).processDefinitionVersion(2).singleResult();assertEquals(2,processInstance.getProcessDefinitionVersion().intValue());variableMap=processInstance.getProcessVariables();assertEquals(456,variableMap.get("anothertest"));}}
}