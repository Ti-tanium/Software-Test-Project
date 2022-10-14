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
package org.activiti.standalone.testing;

import org.activiti.engine.test.ActivitiTestCase;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.mock.MockServiceTask;
import org.activiti.engine.test.mock.MockServiceTasks;
import org.activiti.engine.test.mock.NoOpServiceTasks;
import org.activiti.standalone.testing.helpers.ServiceTaskTestMock;

/**

 */
public class MockSupportWithActivitiTestCaseTest extends ActivitiTestCase {

  @Deployment
  public void testClassDelegateStringMockSupport() {
    assertEquals(0, ServiceTaskTestMock.CALL_COUNT.get());
    runtimeService.startProcessInstanceByKey("mockSupportTest");
    assertEquals(1, ServiceTaskTestMock.CALL_COUNT.get());
  }

}
