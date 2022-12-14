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

  @Test public void testExecute__collection_nullInternalPredicate_nullUserPredicate() throws Exception{Result result=createNiceMock(Result.class);ResourceInstance clusterResourceInstance=createNiceMock(ResourceInstance.class);ResourceDefinition clusterResourceDefinition=createNiceMock(ResourceDefinition.class);Schema clusterSchema=createNiceMock(Schema.class);Resource clusterResource=createNiceMock(Resource.class);String clusterPropertyId="clusterId";Map<String, Set<String>> mapProperties=new HashMap<String, Set<String>>();mapProperties.put(null,Collections.singleton("clusterId"));TreeNode<Resource> tree=new TreeNodeImpl<Resource>(null,null,null);List<Resource> listResources=Collections.singletonList(clusterResource);Map<Resource.Type, String> mapResourceIds=new HashMap<Resource.Type, String>();expect(clusterResource.getType()).andReturn(Resource.Type.Cluster).anyTimes();expect(clusterResourceInstance.getIds()).andReturn(mapResourceIds).anyTimes();expect(clusterResourceInstance.getResourceDefinition()).andReturn(clusterResourceDefinition).anyTimes();expect(clusterResourceDefinition.getType()).andReturn(Resource.Type.Component).anyTimes();expect(m_controller.getSchema(Resource.Type.Component)).andReturn(clusterSchema).atLeastOnce();expect(clusterSchema.getCategoryProperties()).andReturn(mapProperties);expect(clusterSchema.getKeyPropertyId(Resource.Type.Component)).andReturn(clusterPropertyId).atLeastOnce();expect(result.getResultTree()).andReturn(tree).atLeastOnce();expect(m_controller.getResources(eq(Resource.Type.Component),eq(PropertyHelper.getReadRequest(Collections.singleton(clusterPropertyId))),(Predicate)isNull())).andReturn(listResources);replay(m_controller,result,clusterResourceInstance,clusterResourceDefinition,clusterSchema,clusterResource);QueryImpl query=new TestQuery(clusterResourceInstance,result);query.execute();verify(m_controller,result,clusterResourceInstance,clusterResourceDefinition,clusterSchema,clusterResource);assertEquals("true",tree.getProperty("isCollection"));assertEquals(1,tree.getChildren().size());TreeNode<Resource> clusterNode=tree.getChild("Cluster:1");assertSame(clusterResource,clusterNode.getObject());assertEquals(0,clusterNode.getChildren().size());}

  

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
