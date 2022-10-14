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

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.spring.autodeployment.DefaultAutoDeploymentStrategy;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultAutoDeploymentStrategyTest extends AbstractAutoDeploymentStrategyTest {

    private DefaultAutoDeploymentStrategy deploymentStrategy;

    @Before
    public void before() throws Exception {
        super.before();
        deploymentStrategy = new DefaultAutoDeploymentStrategy();
    }

    @Test public void testDetermineResourceNameWithExceptionFailsGracefully() throws Exception{when(resourceMock3.getFile()).thenThrow(new IOException());when(resourceMock3.getFilename()).thenReturn(resourceName3);final Resource[] resources=new Resource[]{resourceMock3};deploymentStrategy.deployResources(deploymentNameHint,resources,repositoryServiceMock);}
}