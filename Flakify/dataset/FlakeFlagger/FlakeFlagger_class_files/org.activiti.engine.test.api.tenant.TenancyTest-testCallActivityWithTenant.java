package org.activiti.engine.test.api.tenant;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.impl.util.CollectionUtil;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Assert;

/**
 * A test case for the various implications of the tenancy support (tenant id column to entities + query support)
 */
public class TenancyTest extends PluggableActivitiTestCase {

    private static final String TEST_TENANT_ID = "myTenantId";

    private List<String> autoCleanedUpDeploymentIds = new ArrayList<String>();

    /**
     * Deploys the one task process with the test tenant id.
     * @return The process definition id of the deployed process definition.
     */
    private String deployTestProcessWithTestTenant() {
        return deployTestProcessWithTestTenant(TEST_TENANT_ID);
    }

    private String deployTestProcessWithTestTenant(String tenantId) {
        String id = repositoryService.createDeployment().addBpmnModel("testProcess.bpmn20.xml",
                                                                      createOneTaskTestProcess()).tenantId(tenantId).deploy().getId();

        autoCleanedUpDeploymentIds.add(id);

        return repositoryService.createProcessDefinitionQuery().deploymentId(id).singleResult().getId();
    }

    private String deployTestProcessWithTwoTasksWithTestTenant() {
        String id = repositoryService.createDeployment().addBpmnModel("testProcess.bpmn20.xml",
                                                                      createTwoTasksTestProcess()).tenantId(TEST_TENANT_ID).deploy().getId();

        autoCleanedUpDeploymentIds.add(id);

        return repositoryService.createProcessDefinitionQuery().deploymentId(id).singleResult().getId();
    }

    private String deployTestProcessWithTwoTasksNoTenant() {
        String id = repositoryService.createDeployment().addBpmnModel("testProcess.bpmn20.xml",
                                                                      createTwoTasksTestProcess()).deploy().getId();

        autoCleanedUpDeploymentIds.add(id);

        return repositoryService.createProcessDefinitionQuery().deploymentId(id).singleResult().getId();
    }

    public void testCallActivityWithTenant(){if (processEngineConfiguration.getHistoryLevel().isAtLeast(HistoryLevel.ACTIVITY)){String tenantId="apache";repositoryService.createDeployment().addClasspathResource("org/activiti/engine/test/api/tenant/TenancyTest.testCallActivityWithTenant-process01.bpmn20.xml").tenantId(tenantId).deploy();repositoryService.createDeployment().addClasspathResource("org/activiti/engine/test/api/tenant/TenancyTest.testCallActivityWithTenant-process02.bpmn20.xml").tenantId(tenantId).deploy();ProcessInstance processInstance=runtimeService.startProcessInstanceByKeyAndTenantId("process1",null,CollectionUtil.singletonMap("sendFor","test"),tenantId);Assert.assertNotNull(processInstance);Assert.assertEquals(1,historyService.createHistoricProcessInstanceQuery().processDefinitionKey("process2").processInstanceTenantId(tenantId).count());Assert.assertEquals(1,historyService.createHistoricProcessInstanceQuery().processDefinitionKey("process2").count());try {processInstance=runtimeService.startProcessInstanceByKey("process1");Assert.fail();} catch (Exception e){}for (Deployment deployment:repositoryService.createDeploymentQuery().list()){repositoryService.deleteDeployment(deployment.getId(),true);}}}
}
