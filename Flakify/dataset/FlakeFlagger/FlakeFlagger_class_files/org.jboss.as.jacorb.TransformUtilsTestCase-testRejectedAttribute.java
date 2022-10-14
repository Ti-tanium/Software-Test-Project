package org.jboss.as.jacorb;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ValueExpression;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href=mailto:tadamski@redhat.com>Tomasz Adamski</a>
 * */
public class TransformUtilsTestCase {

    @Test public void testRejectedAttribute() throws Exception{ModelNode model=new ModelNode();model.get("queue-min").set(5);List<String> result=TransformUtils.validateDeprecatedProperites(model);Assert.assertFalse(result.isEmpty());}

}
