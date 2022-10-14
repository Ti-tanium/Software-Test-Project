package org.jboss.as.test.integration.web.handlestypes;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.servlet.ServletContainerInitializer;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Stuart Douglas
 */
@RunWith(Arquillian.class)
public class HandlesTypesWarTestCase {

    @Test
    public void testParentClass() {
        Class<?>[] expeccted = {HandlesTypesChild.class, HandlesTypesImplementor.class, HandlesTypesGandchild.class, HandlesTypesImplementorChild.class};
        Assert.assertEquals(new HashSet<>(Arrays.asList(expeccted)), ParentServletContainerInitializer.HANDLES_TYPES);
    }
}
