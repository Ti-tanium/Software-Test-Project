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

package org.activiti.spring.test.autodeployment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.spring.autodeployment.ResourceParentFolderAutoDeploymentStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.core.io.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ResourceParentFolderAutoDeploymentStrategyTest extends AbstractAutoDeploymentStrategyTest {

  private ResourceParentFolderAutoDeploymentStrategy deploymentStrategy;

  @Mock
  private File parentFile1Mock;

  @Mock
  private File parentFile2Mock;

  private final String parentFilename1 = "parentFilename1";
  private final String parentFilename2 = "parentFilename2";

  @Before
  public void before() throws Exception {
    super.before();
    deploymentStrategy = new ResourceParentFolderAutoDeploymentStrategy();
    assertNotNull(deploymentStrategy);

    when(parentFile1Mock.getName()).thenReturn(parentFilename1);
    when(parentFile1Mock.isDirectory()).thenReturn(true);
    when(parentFile2Mock.getName()).thenReturn(parentFilename2);
    when(parentFile2Mock.isDirectory()).thenReturn(true);
  }

  @Test public void testDeployResources_Mixed(){final Resource[] resources=new Resource[]{resourceMock1,resourceMock2,resourceMock3};when(fileMock1.getParentFile()).thenReturn(parentFile1Mock);when(fileMock2.getParentFile()).thenReturn(parentFile2Mock);when(fileMock3.getParentFile()).thenReturn(parentFile1Mock);deploymentStrategy.deployResources(deploymentNameHint,resources,repositoryServiceMock);verify(repositoryServiceMock,times(2)).createDeployment();verify(deploymentBuilderMock,times(2)).enableDuplicateFiltering();verify(deploymentBuilderMock,times(1)).name(deploymentNameHint + "." + parentFilename1);verify(deploymentBuilderMock,times(1)).name(deploymentNameHint + "." + parentFilename2);verify(deploymentBuilderMock,times(1)).addInputStream(eq(resourceName1),isA(Resource.class));verify(deploymentBuilderMock,times(1)).addInputStream(eq(resourceName2),isA(Resource.class));verify(deploymentBuilderMock,times(1)).addInputStream(eq(resourceName3),isA(Resource.class));verify(deploymentBuilderMock,times(2)).deploy();}

}