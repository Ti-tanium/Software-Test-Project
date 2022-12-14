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

  @Test public void testExecute__collection_nonNullInternalPredicate_nonNullUserPredicate() throws Exception{Result result=createNiceMock(Result.class);ResourceInstance componentResourceInstance=createNiceMock(ResourceInstance.class);ResourceDefinition componentResourceDefinition=createNiceMock(ResourceDefinition.class);Schema componentSchema=createNiceMock(Schema.class);Resource componentResource=createNiceMock(Resource.class);String componentPropertyId="componentId";String servicePropertyId="serviceId";String clusterPropertyId="clusterId";Set<String> setPropertyIds=new HashSet<String>();setPropertyIds.add(clusterPropertyId);setPropertyIds.add(servicePropertyId);setPropertyIds.add(componentPropertyId);Map<String, Set<String>> mapProperties=new HashMap<String, Set<String>>();Set<String> setAllProps=new HashSet<String>();setAllProps.add("clusterId");setAllProps.add("serviceId");setAllProps.add("componentId");mapProperties.put(null,setAllProps);TreeNode<Resource> tree=new TreeNodeImpl<Resource>(null,null,null);List<Resource> listResources=Collections.singletonList(componentResource);Map<Resource.Type, String> mapResourceIds=new HashMap<Resource.Type, String>();mapResourceIds.put(Resource.Type.Cluster,"clusterName");mapResourceIds.put(Resource.Type.Service,"serviceName");mapResourceIds.put(Resource.Type.Component,null);PredicateBuilder pb=new PredicateBuilder();Predicate internalPredicate=pb.property("clusterId").equals("clusterName").and().property("serviceId").equals("serviceName").toPredicate();pb=new PredicateBuilder();Predicate userPredicate=pb.property("foo").equals("bar").toPredicate();Predicate predicate=new AndPredicate((BasePredicate)internalPredicate,(BasePredicate)userPredicate);expect(componentResource.getType()).andReturn(Resource.Type.Component).anyTimes();expect(componentResourceInstance.getIds()).andReturn(mapResourceIds).anyTimes();expect(componentResourceInstance.getResourceDefinition()).andReturn(componentResourceDefinition).anyTimes();expect(componentResourceDefinition.getType()).andReturn(Resource.Type.Component).anyTimes();expect(m_controller.getSchema(Resource.Type.Component)).andReturn(componentSchema).anyTimes();expect(componentSchema.getCategoryProperties()).andReturn(mapProperties).anyTimes();expect(componentSchema.getKeyPropertyId(Resource.Type.Component)).andReturn(componentPropertyId).atLeastOnce();expect(componentSchema.getKeyPropertyId(Resource.Type.Cluster)).andReturn("clusterId").anyTimes();expect(componentSchema.getKeyPropertyId(Resource.Type.Service)).andReturn("serviceId").anyTimes();expect(result.getResultTree()).andReturn(tree).anyTimes();expect(m_controller.getResources(eq(Resource.Type.Component),eq(PropertyHelper.getReadRequest(setPropertyIds)),eq(predicate))).andReturn(listResources);replay(m_controller,result,componentResourceInstance,componentResourceDefinition,componentSchema,componentResource);QueryImpl query=new TestQuery(componentResourceInstance,result);query.setUserPredicate(userPredicate);query.execute();verify(m_controller,result,componentResourceInstance,componentResourceDefinition,componentSchema,componentResource);assertEquals("true",tree.getProperty("isCollection"));assertEquals(1,tree.getChildren().size());TreeNode<Resource> componentNode=tree.getChild("Component:1");assertSame(componentResource,componentNode.getObject());assertEquals(0,componentNode.getChildren().size());}

  

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
