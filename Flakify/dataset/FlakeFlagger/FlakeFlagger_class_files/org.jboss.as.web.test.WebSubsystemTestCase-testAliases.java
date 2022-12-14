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
package org.jboss.as.web.test;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.NAME;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.READ_ATTRIBUTE_OPERATION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.SUBSYSTEM;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.VALUE;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.WRITE_ATTRIBUTE_OPERATION;
import static org.jboss.as.web.Constants.ACCESS_LOG;
import static org.jboss.as.web.Constants.CONFIGURATION;
import static org.jboss.as.web.Constants.CONNECTOR;
import static org.jboss.as.web.Constants.DIRECTORY;
import static org.jboss.as.web.Constants.PREFIX;
import static org.jboss.as.web.Constants.SETTING;
import static org.jboss.as.web.Constants.SSL;
import static org.jboss.as.web.Constants.SSO;
import static org.jboss.as.web.Constants.VIRTUAL_SERVER;
import static org.jboss.as.web.WebExtension.SUBSYSTEM_NAME;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.jboss.as.controller.ModelVersion;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.ProcessType;
import org.jboss.as.controller.RunningMode;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.as.controller.transform.OperationTransformer.TransformedOperation;
import org.jboss.as.model.test.FailedOperationTransformationConfig;
import org.jboss.as.model.test.FailedOperationTransformationConfig.RejectExpressionsConfig;
import org.jboss.as.model.test.ModelFixer;
import org.jboss.as.model.test.ModelTestControllerVersion;
import org.jboss.as.model.test.ModelTestUtils;
import org.jboss.as.subsystem.test.AbstractSubsystemBaseTest;
import org.jboss.as.subsystem.test.AdditionalInitialization;
import org.jboss.as.subsystem.test.KernelServices;
import org.jboss.as.subsystem.test.KernelServicesBuilder;
import org.jboss.as.web.WebExtension;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.dmr.Property;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jean-Frederic Clere
 * @author Kabir Khan
 */
public class WebSubsystemTestCase extends AbstractSubsystemBaseTest {

    static {
        System.setProperty("jboss.server.config.dir", "target/jbossas");
    }

    @Test
    public void testAliases() throws Exception {
        KernelServices services = createKernelServicesBuilder(createAdditionalInitialization())
                .setSubsystemXmlResource("subsystem.xml")
                .build();
        ModelNode noAliasModel = services.readWholeModel();
        ModelNode aliasModel = services.readWholeModel(true);

        testSSLAlias(services, noAliasModel, aliasModel);
        testSSOAlias(services, noAliasModel, aliasModel);
        testAccessLogAlias(services, noAliasModel, aliasModel);
    }

    private void checkUndefinedCipherSuite(KernelServices services, ModelVersion version) throws Exception  {
        final ModelNode success = new ModelNode();
        success.get(ModelDescriptionConstants.OUTCOME).set(ModelDescriptionConstants.SUCCESS);
        success.get(ModelDescriptionConstants.RESULT);
        success.protect();

        PathAddress addr = PathAddress.pathAddress(PathElement.pathElement(SUBSYSTEM, getMainSubsystemName()),
                PathElement.pathElement("connector", "https"), PathElement.pathElement("configuration", "ssl"));

        ModelNode op = Util.createOperation(WRITE_ATTRIBUTE_OPERATION, addr);
        op.get(NAME).set("cipher-suite");
        op.get(VALUE).set(new ModelNode());
        TransformedOperation transOp = services.transformOperation(version, op);
        Assert.assertTrue(transOp.rejectOperation(success));

        op.get(VALUE).set("SSL_RSA_WITH_3DES_EDE_CBC_SHA");
        transOp = services.transformOperation(version, op);
        Assert.assertFalse(transOp.rejectOperation(success));
    }


    private void writeAttribute(KernelServices services, String name, String value, String... address) throws Exception {
        ModelNode op = createOperation(WRITE_ATTRIBUTE_OPERATION, address);
        op.get(NAME).set(name);
        op.get(VALUE).set(value);
        services.executeForResult(op);
    }

    private String readAttribute(KernelServices services, String name, String... address) throws Exception {
        ModelNode op = createOperation(READ_ATTRIBUTE_OPERATION, address);
        op.get(NAME).set(name);
        ModelNode result = services.executeForResult(op);
        if (result.isDefined()) {
            return result.asString();
        }
        return null;
    }

    private ModelNode createOperation(String operationName, String... address) {
        ModelNode operation = new ModelNode();
        operation.get(OP).set(operationName);
        if (address.length > 0) {
            if (address.length % 2 != 0) {
                throw new IllegalArgumentException("Address must be in pairs");
            }
            for (int i = 0; i < address.length; i += 2) {
                operation.get(OP_ADDR).add(address[i], address[i + 1]);
            }
        } else {
            operation.get(OP_ADDR).setEmptyList();
        }

        return operation;
    }

    private String[] getAddress(String... addr) {
        return addr;
    }

    // TODO WFCORE-1353 means this doesn't have to always fail now; consider just deleting this
//    @Override
//    protected void validateDescribeOperation(KernelServices hc, AdditionalInitialization serverInit, ModelNode expectedModel) throws Exception {
//        final ModelNode operation = createDescribeOperation();
//        final ModelNode result = hc.executeOperation(operation);
//        Assert.assertTrue("The subsystem describe operation must fail",
//                result.hasDefined(ModelDescriptionConstants.FAILURE_DESCRIPTION));
//    }

    private static class SSLConfigurationNameFixer implements ModelFixer {
        private static final ModelFixer INSTANCE = new SSLConfigurationNameFixer();

        @Override
        public ModelNode fixModel(ModelNode modelNode) {
            //In the current and legacy models this is handled by a read attribute handler rather than existing in the model
            modelNode.get("connector","https", "configuration", "ssl", "name").set("ssl");
            return modelNode;
        }

    }


    private static class AccessLogPrefixFixer_1_2_0 implements ModelFixer {

        private static final ModelFixer INSTANCE = new AccessLogPrefixFixer_1_2_0();

        @Override
        public ModelNode fixModel(ModelNode modelNode) {
            if (modelNode.hasDefined(VIRTUAL_SERVER)) {
                for (Property property : modelNode.get(VIRTUAL_SERVER).asPropertyList()) {
                    ModelNode virtualServer = property.getValue();
                    if (virtualServer.hasDefined(CONFIGURATION)) {
                        if (virtualServer.get(CONFIGURATION).hasDefined(ACCESS_LOG)) {
                            ModelNode prefix = virtualServer.get(CONFIGURATION, ACCESS_LOG, PREFIX);
                            if (prefix.getType() == ModelType.BOOLEAN) {
                                modelNode.get(VIRTUAL_SERVER, property.getName(), CONFIGURATION, ACCESS_LOG, PREFIX).set("access_log.");
                            }
                        }
                    }
                }
            }
            return modelNode;
        }
    }

    private static final class IntExpressionConfig extends RejectExpressionsConfig {

        public IntExpressionConfig(String... attributes) {
            // FIXME GlobalSessionTimeOutConfig constructor
            super(attributes);
        }

        @Override
        protected ModelNode correctValue(ModelNode toResolve, boolean isWriteAttribute) {
            ModelNode value = super.correctValue(toResolve, isWriteAttribute);
            return new ModelNode(value.asInt());
        }
    }

    private static final class BooleanExpressionConfig extends RejectExpressionsConfig {

        public BooleanExpressionConfig(String... attributes) {
            // FIXME GlobalSessionTimeOutConfig constructor
            super(attributes);
        }

        @Override
        protected ModelNode correctValue(ModelNode toResolve, boolean isWriteAttribute) {
            ModelNode value = super.correctValue(toResolve, isWriteAttribute);
            return new ModelNode(value.asBoolean());
        }
    }

    private static class TestAdditionalInitialization extends AdditionalInitialization implements Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        protected ProcessType getProcessType() {
            return ProcessType.HOST_CONTROLLER;
        }

        @Override
        protected RunningMode getRunningMode() {
            return RunningMode.ADMIN_ONLY;
        }
    }

}
