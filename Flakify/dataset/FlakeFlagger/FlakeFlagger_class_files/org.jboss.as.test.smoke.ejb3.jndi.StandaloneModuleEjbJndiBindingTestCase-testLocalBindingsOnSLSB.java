/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

package org.jboss.as.test.smoke.ejb3.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

/**
 * Tests that the session beans are bound to all the jndi binding names mandated by the EJB3.1 spec, when the EJBs are
 * deployed in a standalone jar file
 *
 * @author Jaikiran Pai
 */
@RunWith(Arquillian.class)
public class StandaloneModuleEjbJndiBindingTestCase {

    /**
     * The module name of the deployment
     */
    private static final String MODULE_NAME = "ejb3-jndi-binding-test";

    /**
     * Complete jar file name including the .jar file extension
     */
    private static final String ARCHIVE_NAME = MODULE_NAME + ".jar";

    /**
     * java:global/ namespace
     */
    private static final String JAVA_GLOBAL_NAMESPACE_PREFIX = "java:global/";

    /**
     * java:app/ namespace
     */
    private static final String JAVA_APP_NAMESPACE_PREFIX = "java:app/";

    /**
     * java:module/ namespace
     */
    private static final String JAVA_MODULE_NAMESPACE_PREFIX = "java:module/";

    /**
     * Tests that all possible local view bindings of a Stateless bean are available.
     *
     * @throws Exception
     */
    @Test
    public void testLocalBindingsOnSLSB() throws Exception {
        Context ctx = new InitialContext();
        String ejbName = SampleSLSB.class.getSimpleName();
        // global bindings
        // 1. local business interface
        Echo localBusinessInterface = (Echo) ctx.lookup(JAVA_GLOBAL_NAMESPACE_PREFIX + MODULE_NAME + "/" + ejbName + "!" + Echo.class.getName());
        Assert.assertNotNull("Null object returned for local business interface lookup in java:global namespace", localBusinessInterface);
        // 2. no-interface view
        SampleSLSB noInterfaceView = (SampleSLSB) ctx.lookup(JAVA_GLOBAL_NAMESPACE_PREFIX + MODULE_NAME + "/" + ejbName + "!" + SampleSLSB.class.getName());
        Assert.assertNotNull("Null object returned for no-interface view lookup in java:global namespace", noInterfaceView);


        // app bindings
        // 1. local business interface
        Echo localBusinessInterfaceInAppNamespace = (Echo) ctx.lookup(JAVA_APP_NAMESPACE_PREFIX + MODULE_NAME + "/" + ejbName + "!" + Echo.class.getName());
        Assert.assertNotNull("Null object returned for local business interface lookup in java:app namespace", localBusinessInterfaceInAppNamespace);
        // 2. no-interface view
        SampleSLSB noInterfaceViewInAppNamespace = (SampleSLSB) ctx.lookup(JAVA_APP_NAMESPACE_PREFIX + MODULE_NAME + "/" + ejbName + "!" + SampleSLSB.class.getName());
        Assert.assertNotNull("Null object returned for no-interface view lookup in java:app namespace", noInterfaceViewInAppNamespace);

        // module bindings
        // 1. local business interface
        Echo localBusinessInterfaceInModuleNamespace = (Echo) ctx.lookup(JAVA_MODULE_NAMESPACE_PREFIX + ejbName + "!" + Echo.class.getName());
        Assert.assertNotNull("Null object returned for local business interface lookup in java:module namespace", localBusinessInterfaceInModuleNamespace);
        // 2. no-interface view
        SampleSLSB noInterfaceViewInModuleNamespace = (SampleSLSB) ctx.lookup(JAVA_MODULE_NAMESPACE_PREFIX + ejbName + "!" + SampleSLSB.class.getName());
        Assert.assertNotNull("Null object returned for no-interface view lookup in java:module namespace", noInterfaceViewInModuleNamespace);

        // additional binding
        {
            final Echo bean = (Echo) ctx.lookup(JAVA_GLOBAL_NAMESPACE_PREFIX + "Additional");
            assertNotNull("Null object returned from java:global/Additional", bean);
        }
    }
}