/*
* JBoss, Home of Professional Open Source.
* Copyright 2011, Red Hat Middleware LLC, and individual contributors
* as indicated by the @author tags. See the copyright.txt file in the
* distribution for a full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.as.connector.subsystems.jca;

import static org.jboss.as.connector.subsystems.jca.Constants.WORKMANAGER_SHORT_RUNNING;
import static org.jboss.as.connector.subsystems.jca.JcaDistributedWorkManagerDefinition.DWmParameters.ELYTRON_ENABLED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.jboss.as.connector.logging.ConnectorLogger;
import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.as.model.test.FailedOperationTransformationConfig;
import org.jboss.as.model.test.ModelFixer;
import org.jboss.as.model.test.ModelTestControllerVersion;
import org.jboss.as.model.test.ModelTestUtils;
import org.jboss.as.model.test.SingleClassFilter;
import org.jboss.as.subsystem.test.AbstractSubsystemBaseTest;
import org.jboss.as.subsystem.test.AdditionalInitialization;
import org.jboss.as.subsystem.test.KernelServices;
import org.jboss.as.subsystem.test.KernelServicesBuilder;
import org.jboss.dmr.ModelNode;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.clustering.spi.ClusteringDefaultRequirement;

/**
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @author <a href="stefano.maestri@redhat.com>Stefano Maestri</a>
 */
public class JcaSubsystemTestCase extends AbstractSubsystemBaseTest {

    /**
	 * WFLY-2640 and WFLY-8141 
	 */@Test public void testCCMHandling() throws Exception{String xml=readResource("jca-minimal.xml");final KernelServices services=createKernelServicesBuilder(createAdditionalInitialization()).setSubsystemXml(xml).build();assertTrue("Subsystem boot failed!",services.isSuccessfulBoot());final ModelNode model=services.readWholeModel();ModelNode ccm=model.get("subsystem","jca","cached-connection-manager","cached-connection-manager");assertTrue(ccm.isDefined());assertTrue(ccm.hasDefined("install"));PathAddress ccmAddress=PathAddress.pathAddress("subsystem","jca").append("cached-connection-manager","cached-connection-manager");ModelNode writeOp=Util.getWriteAttributeOperation(ccmAddress,"install",true);services.executeForResult(writeOp);ModelNode readOp=Util.getReadAttributeOperation(ccmAddress,"install");ModelNode result=services.executeForResult(readOp);assertTrue(result.asBoolean());ModelNode removeOp=Util.createRemoveOperation(ccmAddress);services.executeForResult(removeOp);result=services.executeForResult(readOp);assertFalse(result.asBoolean());services.executeForResult(writeOp);ModelNode addOp=Util.createAddOperation(ccmAddress);addOp.get("debug").set(true);services.executeForFailure(addOp);ModelNode undefineOp=Util.createEmptyOperation("undefine-attribute",ccmAddress);undefineOp.get("name").set("install");services.executeForResult(undefineOp);result=services.executeForResult(readOp);assertFalse(result.asBoolean());services.executeForResult(addOp);ModelNode readOp2=Util.getReadAttributeOperation(ccmAddress,"debug");result=services.executeForResult(readOp2);assertTrue(result.asBoolean());services.executeForFailure(addOp);services.executeForResult(removeOp);result=services.executeForResult(readOp2);assertFalse(result.asBoolean());services.executeForResult(addOp);result=services.executeForResult(readOp2);assertTrue(result.asBoolean());}

    /**
     * Tests transformation of model from 1.2.0 version into 1.1.0 version.
     *
     * @throws Exception
     */
    private void testTransformer(ModelTestControllerVersion controllerVersion, ModelVersion modelVersion, String xmlResourceName) throws Exception {
        // create builder for current subsystem version
        KernelServicesBuilder builder = createKernelServicesBuilder(createAdditionalInitialization());

        // create builder for legacy subsystem version
        builder.createLegacyKernelServicesBuilder(null, controllerVersion, modelVersion)
                .addMavenResourceURL("org.jboss.as:jboss-as-connector:" + controllerVersion.getMavenGavVersion())
                .addMavenResourceURL("org.jboss.as:jboss-as-threads:" + controllerVersion.getMavenGavVersion())
                .setExtensionClassName("org.jboss.as.connector.subsystems.jca.JcaExtension")
                .excludeFromParent(SingleClassFilter.createFilter(ConnectorLogger.class));

        KernelServices mainServices = builder.build();
        KernelServices legacyServices = mainServices.getLegacyServices(modelVersion);

        Assert.assertNotNull(legacyServices);
        assertTrue("main services did not boot", mainServices.isSuccessfulBoot());
        assertTrue(legacyServices.isSuccessfulBoot());

        List<ModelNode> xmlOps = builder.parseXmlResource(xmlResourceName);


        ModelTestUtils.checkFailedTransformedBootOperations(mainServices, modelVersion, xmlOps,
                new FailedOperationTransformationConfig()
                        .addFailedAttribute(PathAddress.pathAddress(JcaSubsystemRootDefinition.PATH_SUBSYSTEM, JcaDistributedWorkManagerDefinition.PATH_DISTRIBUTED_WORK_MANAGER),
                                FailedOperationTransformationConfig.REJECTED_RESOURCE)
                        .addFailedAttribute(PathAddress.pathAddress(JcaSubsystemRootDefinition.PATH_SUBSYSTEM, JcaDistributedWorkManagerDefinition.PATH_DISTRIBUTED_WORK_MANAGER, PathElement.pathElement(WORKMANAGER_SHORT_RUNNING)),
                                FailedOperationTransformationConfig.REJECTED_RESOURCE));
    }


}
