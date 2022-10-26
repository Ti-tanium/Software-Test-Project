/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
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

package org.jboss.as.test.smoke.mgmt.datasource;


import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.OP_ADDR;
import static org.jboss.as.test.integration.management.jca.ComplexPropertiesParseUtils.addExtensionProperties;
import static org.jboss.as.test.integration.management.jca.ComplexPropertiesParseUtils.checkModelParams;
import static org.jboss.as.test.integration.management.jca.ComplexPropertiesParseUtils.nonXaDsProperties;
import static org.jboss.as.test.integration.management.jca.ComplexPropertiesParseUtils.setOperationParams;
import static org.jboss.as.test.integration.management.jca.ComplexPropertiesParseUtils.xaDsProperties;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Properties;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.as.test.integration.management.jca.ConnectionSecurityType;
import org.jboss.as.test.integration.management.jca.DsMgmtTestBase;
import org.jboss.dmr.ModelNode;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Datasource operation unit test.
 *
 * @author <a href="mailto:stefano.maestri@redhat.com">Stefano Maestri</a>
 * @author <a href="mailto:jeff.zhang@jboss.org">Jeff Zhang</a>
 * @author <a href="mailto:vrastsel@redhat.com">Vladimir Rastseluev</a>
 * @author Flavia Rainone
 */
@RunWith(Arquillian.class)
@RunAsClient
@ServerSetup(DataSourceOperationsUnitTestCase.ServerSetup.class)
public class DataSourceOperationsUnitTestCase extends DsMgmtTestBase {

    public static class ServerSetup implements ServerSetupTask {

        @Override
        public void setup(ManagementClient managementClient, String containerId) throws Exception {
            ModelNode authContextAdd = Util.createAddOperation(PathAddress.pathAddress("subsystem", "elytron").append("authentication-context", "HsqlAuthCtxt"));
            ModelNode response = managementClient.getControllerClient().execute(authContextAdd);
            Assert.assertEquals(response.toString(), "success", response.get("outcome").asString());
        }

        @Override
        public void tearDown(ManagementClient managementClient, String containerId) throws Exception {
            ModelNode authContextRemove = Util.createRemoveOperation(PathAddress.pathAddress("subsystem", "elytron").append("authentication-context", "HsqlAuthCtxt"));
            ModelNode response = managementClient.getControllerClient().execute(authContextRemove);
            Assert.assertEquals(response.toString(), "success", response.get("outcome").asString());
        }
    }

    /**
	 * AS7-1200 test case for xa datasource persistence to xml
	 * @throws Exception
	 */@Test public void testMarshallUnmarshallXaDs() throws Exception{final String dsName="XaDsName2";final String jndiDsName="XaJndiDsName2";final ModelNode address=new ModelNode();address.add("subsystem","datasources");address.add("xa-data-source",dsName);address.protect();final ModelNode operation=new ModelNode();operation.get(OP).set("add");operation.get(OP_ADDR).set(address);operation.get("name").set(dsName);operation.get("jndi-name").set("java:jboss/datasources/" + jndiDsName);operation.get("enabled").set(false);operation.get("driver-name").set("h2");operation.get("pool-name").set(dsName + "_Pool");operation.get("user-name").set("sa");operation.get("password").set("sa");executeOperation(operation);final ModelNode xaDatasourcePropertiesAddress=address.clone();xaDatasourcePropertiesAddress.add("xa-datasource-properties","URL");xaDatasourcePropertiesAddress.protect();final ModelNode xaDatasourcePropertyOperation=new ModelNode();xaDatasourcePropertyOperation.get(OP).set("add");xaDatasourcePropertyOperation.get(OP_ADDR).set(xaDatasourcePropertiesAddress);xaDatasourcePropertyOperation.get("value").set("jdbc:h2:mem:test");executeOperation(xaDatasourcePropertyOperation);final ModelNode operation2=new ModelNode();operation2.get(OP).set("write-attribute");operation2.get("name").set("enabled");operation2.get("value").set(true);operation2.get(OP_ADDR).set(address);executeOperation(operation2);List<ModelNode> newList=marshalAndReparseDsResources("xa-data-source");remove(address);Assert.assertNotNull("Reparsing failed:",newList);marshalAndReparseDsResources("xa-data-source");Assert.assertNotNull(findNodeWithProperty(newList,"jndi-name","java:jboss/datasources/" + jndiDsName));}
}