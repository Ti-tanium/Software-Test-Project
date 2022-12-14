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
package org.activiti.examples.processdefinitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.impl.bpmn.deployer.ResourceNameUtil;
import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.repository.ProcessDefinition;

/**


 */
public class ProcessDefinitionsTest extends PluggableActivitiTestCase {

  private static final String NAMESPACE = "xmlns='http://www.omg.org/spec/BPMN/20100524/MODEL'";

  private static final String TARGET_NAMESPACE = "targetNamespace='http://activiti.org/BPMN20'";

  public void testGetProcessDefinitions(){List<String> deploymentIds=new ArrayList<String>();deploymentIds.add(deployProcessString(("<definitions " + NAMESPACE + " " + TARGET_NAMESPACE + ">" + "  <process id='IDR' name='Insurance Damage Report 1' />" + "</definitions>")));deploymentIds.add(deployProcessString(("<definitions " + NAMESPACE + " " + TARGET_NAMESPACE + ">" + "  <process id='IDR' name='Insurance Damage Report 2' />" + "</definitions>")));deploymentIds.add(deployProcessString(("<definitions " + NAMESPACE + " " + TARGET_NAMESPACE + ">" + "  <process id='IDR' name='Insurance Damage Report 3' />" + "</definitions>")));deploymentIds.add(deployProcessString(("<definitions " + NAMESPACE + " " + TARGET_NAMESPACE + ">" + "  <process id='EN' name='Expense Note 1' />" + "</definitions>")));deploymentIds.add(deployProcessString(("<definitions " + NAMESPACE + " " + TARGET_NAMESPACE + ">" + "  <process id='EN' name='Expense Note 2' />" + "</definitions>")));List<ProcessDefinition> processDefinitions=repositoryService.createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().orderByProcessDefinitionVersion().desc().list();assertNotNull(processDefinitions);assertEquals(5,processDefinitions.size());ProcessDefinition processDefinition=processDefinitions.get(0);assertEquals("EN",processDefinition.getKey());assertEquals("Expense Note 2",processDefinition.getName());assertTrue(processDefinition.getId().startsWith("EN:2"));assertEquals(2,processDefinition.getVersion());processDefinition=processDefinitions.get(1);assertEquals("EN",processDefinition.getKey());assertEquals("Expense Note 1",processDefinition.getName());assertTrue(processDefinition.getId().startsWith("EN:1"));assertEquals(1,processDefinition.getVersion());processDefinition=processDefinitions.get(2);assertEquals("IDR",processDefinition.getKey());assertEquals("Insurance Damage Report 3",processDefinition.getName());assertTrue(processDefinition.getId().startsWith("IDR:3"));assertEquals(3,processDefinition.getVersion());processDefinition=processDefinitions.get(3);assertEquals("IDR",processDefinition.getKey());assertEquals("Insurance Damage Report 2",processDefinition.getName());assertTrue(processDefinition.getId().startsWith("IDR:2"));assertEquals(2,processDefinition.getVersion());processDefinition=processDefinitions.get(4);assertEquals("IDR",processDefinition.getKey());assertEquals("Insurance Damage Report 1",processDefinition.getName());assertTrue(processDefinition.getId().startsWith("IDR:1"));assertEquals(1,processDefinition.getVersion());Set<String> queryDeploymentIds=new HashSet<String>();queryDeploymentIds.add(processDefinitions.get(0).getDeploymentId());queryDeploymentIds.add(processDefinitions.get(1).getDeploymentId());List<ProcessDefinition> queryProcessDefinitions=repositoryService.createProcessDefinitionQuery().deploymentIds(queryDeploymentIds).orderByProcessDefinitionKey().asc().orderByProcessDefinitionVersion().desc().list();assertEquals(2,queryProcessDefinitions.size());processDefinition=queryProcessDefinitions.get(0);assertEquals("EN",processDefinition.getKey());assertEquals("Expense Note 2",processDefinition.getName());processDefinition=queryProcessDefinitions.get(1);assertEquals("EN",processDefinition.getKey());assertEquals("Expense Note 1",processDefinition.getName());queryDeploymentIds=new HashSet<String>();queryDeploymentIds.add(processDefinitions.get(0).getDeploymentId());queryDeploymentIds.add(processDefinitions.get(3).getDeploymentId());queryDeploymentIds.add(processDefinitions.get(4).getDeploymentId());queryProcessDefinitions=repositoryService.createProcessDefinitionQuery().deploymentIds(queryDeploymentIds).list();assertEquals(3,queryProcessDefinitions.size());processDefinition=queryProcessDefinitions.get(0);assertEquals("EN",processDefinition.getKey());assertEquals("Expense Note 2",processDefinition.getName());processDefinition=processDefinitions.get(3);assertEquals("IDR",processDefinition.getKey());assertEquals("Insurance Damage Report 2",processDefinition.getName());processDefinition=processDefinitions.get(4);assertEquals("IDR",processDefinition.getKey());assertEquals("Insurance Damage Report 1",processDefinition.getName());deleteDeployments(deploymentIds);}

  private String deployProcessString(String processString) {
    String resourceName = "xmlString." + ResourceNameUtil.BPMN_RESOURCE_SUFFIXES[0];
    return repositoryService.createDeployment().addString(resourceName, processString).deploy().getId();
  }

  private void deleteDeployments(Collection<String> deploymentIds) {
    for (String deploymentId : deploymentIds) {
      repositoryService.deleteDeployment(deploymentId);
    }
  }
}
