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
package org.activiti.standalone.escapeclause;

import org.activiti.engine.repository.DeploymentQuery;

public class DeploymentQueryEscapeClauseTest extends AbstractEscapeClauseTestCase {

  private String deploymentOneId;
  
  private String deploymentTwoId;
  
  public void testQueryByTenantIdLike() {
	DeploymentQuery query = repositoryService.createDeploymentQuery().deploymentTenantIdLike("%\\%%");
	assertEquals("One%", query.singleResult().getTenantId());
	assertEquals(1, query.list().size());
	assertEquals(1, query.count());
	query = repositoryService.createDeploymentQuery().deploymentTenantIdLike("%\\_%");
	assertEquals("Two_", query.singleResult().getTenantId());
	assertEquals(1, query.list().size());
	assertEquals(1, query.count());
}
  
}