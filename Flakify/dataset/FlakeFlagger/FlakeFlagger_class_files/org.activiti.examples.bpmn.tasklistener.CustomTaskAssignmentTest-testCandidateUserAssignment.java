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
package org.activiti.examples.bpmn.tasklistener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

/**



 */
public class CustomTaskAssignmentTest extends PluggableActivitiTestCase {


  @Deployment public void testCandidateUserAssignment(){runtimeService.startProcessInstanceByKey("customTaskAssignment");assertEquals(1,taskService.createTaskQuery().taskCandidateUser("kermit",null).count());assertEquals(1,taskService.createTaskQuery().taskCandidateUser("fozzie",null).count());assertEquals(0,taskService.createTaskQuery().taskCandidateUser("gonzo",null).count());}

}
