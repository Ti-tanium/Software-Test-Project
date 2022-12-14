/*
 * Copyright 2016 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.extension.batch.jberet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.model.test.FailedOperationTransformationConfig;
import org.jboss.as.model.test.ModelTestControllerVersion;
import org.jboss.as.model.test.ModelTestUtils;
import org.jboss.as.subsystem.test.AdditionalInitialization;
import org.jboss.as.subsystem.test.KernelServices;
import org.jboss.as.subsystem.test.KernelServicesBuilder;
import org.jboss.dmr.ModelNode;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
public class SubsystemTransformerTestCase extends AbstractBatchTestCase {
    @Test public void testTransformersEAP700() throws Exception{final KernelServicesBuilder builder=createKernelServicesBuilder(createAdditionalInitialization()).setSubsystemXmlResource("/default-subsystem_1_0.xml");final ModelVersion legacyVersion=ModelVersion.create(1,1,0);final ModelTestControllerVersion controllerVersion=ModelTestControllerVersion.EAP_7_0_0;builder.createLegacyKernelServicesBuilder(createAdditionalInitialization(),controllerVersion,legacyVersion).addMavenResourceURL(controllerVersion.getMavenGroupId() + ":wildfly-batch-jberet:" + controllerVersion.getMavenGavVersion()).addMavenResourceURL(controllerVersion.getCoreMavenGroupId() + ":wildfly-threads:" + controllerVersion.getCoreVersion());final KernelServices mainServices=builder.build();assertTrue(mainServices.isSuccessfulBoot());final KernelServices legacyServices=mainServices.getLegacyServices(legacyVersion);assertNotNull(legacyServices);assertTrue(legacyServices.isSuccessfulBoot());checkSubsystemModelTransformation(mainServices,legacyVersion,null,false);}
}
