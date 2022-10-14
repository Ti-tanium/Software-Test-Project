package org.apache.ambari.server.api.query;

import org.apache.ambari.server.api.resources.ResourceDefinition;
import org.apache.ambari.server.api.util.TreeNode;
import org.apache.ambari.server.api.util.TreeNodeImpl;
import org.apache.ambari.server.controller.predicate.AndPredicate;
import org.apache.ambari.server.controller.predicate.BasePredicate;
import org.apache.ambari.server.controller.spi.*;
import org.apache.ambari.server.controller.utilities.PredicateBuilder;
import org.apache.ambari.server.api.resources.ResourceInstance;
import org.apache.ambari.server.api.services.Result;
import org.apache.ambari.server.controller.utilities.PropertyHelper;
import org.junit.After;
import org.junit.Test;

import java.util.*;

import static org.easymock.EasyMock.*;

import static org.easymock.EasyMock.eq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


//todo: add assertions for temporal info
public class QueryImplTest {

  ClusterController m_controller = createNiceMock(ClusterController.class);

  @Test public void testAddProperty__subProperty(){ResourceInstance resource=createNiceMock(ResourceInstance.class);ResourceDefinition resourceDefinition=createNiceMock(ResourceDefinition.class);ResourceInstance subResource=createNiceMock(ResourceInstance.class);Schema schema=createNiceMock(Schema.class);Map<String, Set<String>> mapSchemaProps=new HashMap<String, Set<String>>();mapSchemaProps.put("category",Collections.singleton("property"));mapSchemaProps.put(null,Collections.singleton("property2"));expect(resource.getResourceDefinition()).andReturn(resourceDefinition).anyTimes();expect(resourceDefinition.getType()).andReturn(Resource.Type.Service).anyTimes();expect(m_controller.getSchema(Resource.Type.Service)).andReturn(schema).anyTimes();expect(schema.getCategoryProperties()).andReturn(mapSchemaProps).anyTimes();expect(resource.getSubResources()).andReturn(Collections.singletonMap("components",subResource)).anyTimes();replay(m_controller,resource,resourceDefinition,subResource,schema);Query query=new TestQuery(resource,null);query.addProperty(null,"components",null);verify(m_controller,resource,resourceDefinition,subResource,schema);}

  //todo: sub-resource with property and with sub-path

//  @Test
//  public void testAddProperty__invalidProperty() {
//
//  }

  private class TestQuery extends QueryImpl {

    private Result m_result;

    public TestQuery(ResourceInstance ResourceInstance, Result result) {
      super(ResourceInstance);
      m_result = result;
    }

    @Override
    ClusterController getClusterController() {
      return m_controller;
    }

    @Override
    Result createResult() {
      return m_result;
    }
  }
}
