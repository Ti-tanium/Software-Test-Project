package org.activiti.engine.test.api.history;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.history.ProcessInstanceHistoryLog;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

/**

 */
public class ProcessInstanceLogQueryTest extends PluggableActivitiTestCase {

  protected String processInstanceId;

  public void testEverything(){if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.FULL)){ProcessInstanceHistoryLog log=historyService.createProcessInstanceHistoryLogQuery(processInstanceId).includeTasks().includeActivities().includeComments().includeVariables().includeVariableUpdates().singleResult();List<HistoricData> events=log.getHistoricData();assertEquals(15,events.size());}}

}
