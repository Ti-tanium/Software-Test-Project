package org.activiti.standalone.cfg;

import java.util.List;

import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.test.ResourceActivitiTestCase;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;

/**

 */
public class CustomMybatisXMLMapperTest extends ResourceActivitiTestCase {

  public void testSelectOneTask(){for (int i=0;i < 4;i++){createTask(i + "",null,null,0);}final String taskId=createTask("4",null,null,0);CustomTask customTask=managementService.executeCommand(new Command<CustomTask>(){@Override public CustomTask execute(CommandContext commandContext){return (CustomTask)commandContext.getDbSqlSession().selectOne("selectOneCustomTask",taskId);}});assertEquals("4",customTask.getName());List<Task> tasks=taskService.createTaskQuery().list();assertEquals(5,tasks.size());Task task=taskService.createTaskQuery().taskName("2").singleResult();assertEquals("2",task.getName());deleteTasks(taskService.createTaskQuery().list());}

  protected String createTask(String name, String owner, String assignee, int priority) {
    Task task = taskService.newTask();
    task.setName(name);
    task.setOwner(owner);
    task.setAssignee(assignee);
    task.setPriority(priority);
    taskService.saveTask(task);
    return task.getId();
  }

  protected void deleteTask(String taskId) {
    taskService.deleteTask(taskId);
    historyService.deleteHistoricTaskInstance(taskId);
  }

  protected void deleteTasks(List<Task> tasks) {
    for (Task task : tasks)
      deleteTask(task.getId());
  }

  protected void deleteCustomTasks(List<CustomTask> tasks) {
    for (CustomTask task : tasks)
      deleteTask(task.getId());
  }
}
