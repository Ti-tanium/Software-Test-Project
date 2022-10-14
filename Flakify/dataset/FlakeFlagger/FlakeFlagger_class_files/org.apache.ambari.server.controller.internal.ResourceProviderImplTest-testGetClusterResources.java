/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ambari.server.controller.internal;

import org.apache.ambari.server.controller.*;
import org.apache.ambari.server.controller.spi.UnsupportedPropertyException;
import org.apache.ambari.server.controller.utilities.PredicateBuilder;
import org.apache.ambari.server.controller.utilities.PropertyHelper;
import org.apache.ambari.server.controller.RequestStatusResponse;
import org.apache.ambari.server.controller.spi.Predicate;
import org.apache.ambari.server.controller.spi.Request;
import org.apache.ambari.server.controller.spi.Resource;
import org.apache.ambari.server.controller.spi.ResourceProvider;
import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Resource provider tests.
 */
public class ResourceProviderImplTest {

  @Test public void testGetClusterResources() throws Exception{Resource.Type type=Resource.Type.Cluster;AmbariManagementController managementController=createMock(AmbariManagementController.class);Set<ClusterResponse> allResponse=new HashSet<ClusterResponse>();allResponse.add(new ClusterResponse(100L,"Cluster100",null,null));allResponse.add(new ClusterResponse(101L,"Cluster101",null,null));allResponse.add(new ClusterResponse(102L,"Cluster102",null,null));allResponse.add(new ClusterResponse(103L,"Cluster103",null,null));allResponse.add(new ClusterResponse(104L,"Cluster104",null,null));Set<ClusterResponse> nameResponse=new HashSet<ClusterResponse>();nameResponse.add(new ClusterResponse(102L,"Cluster102",null,null));Set<ClusterResponse> idResponse=new HashSet<ClusterResponse>();idResponse.add(new ClusterResponse(103L,"Cluster103",null,null));expect(managementController.getClusters(anyObject(Set.class))).andReturn(allResponse).once();expect(managementController.getClusters(anyObject(Set.class))).andReturn(nameResponse).once();expect(managementController.getClusters(anyObject(Set.class))).andReturn(idResponse).once();replay(managementController);ResourceProvider provider=ResourceProviderImpl.getResourceProvider(type,PropertyHelper.getPropertyIds(type),PropertyHelper.getKeyPropertyIds(type),managementController);Set<String> propertyIds=new HashSet<String>();propertyIds.add(ClusterResourceProvider.CLUSTER_ID_PROPERTY_ID);propertyIds.add(ClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID);Request request=PropertyHelper.getReadRequest(propertyIds);Set<Resource> resources=provider.getResources(request,null);Assert.assertEquals(5,resources.size());for (Resource resource:resources){Long id=(Long)resource.getPropertyValue(ClusterResourceProvider.CLUSTER_ID_PROPERTY_ID);String name=(String)resource.getPropertyValue(ClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID);Assert.assertEquals(name,"Cluster" + id);}Predicate predicate=new PredicateBuilder().property(ClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID).equals("Cluster102").toPredicate();resources=provider.getResources(request,predicate);Assert.assertEquals(1,resources.size());Assert.assertEquals(102L,resources.iterator().next().getPropertyValue(ClusterResourceProvider.CLUSTER_ID_PROPERTY_ID));Assert.assertEquals("Cluster102",resources.iterator().next().getPropertyValue(ClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID));predicate=new PredicateBuilder().property(ClusterResourceProvider.CLUSTER_ID_PROPERTY_ID).equals(103L).toPredicate();resources=provider.getResources(request,predicate);Assert.assertEquals(1,resources.size());Assert.assertEquals(103L,resources.iterator().next().getPropertyValue(ClusterResourceProvider.CLUSTER_ID_PROPERTY_ID));Assert.assertEquals("Cluster103",resources.iterator().next().getPropertyValue(ClusterResourceProvider.CLUSTER_NAME_PROPERTY_ID));verify(managementController);}

  


  // ----- helper methods ----------------------------------------------------

  public static class Matchers
  {
    public static ClusterRequest clusterRequest(Long clusterId, String clusterName, String stackVersion, Set<String> hostNames)
    {
      EasyMock.reportMatcher(new ClusterRequestMatcher(clusterId, clusterName, stackVersion, hostNames));
      return null;
    }

    public static ServiceRequest serviceRequest(String clusterName, String serviceName, Map<String, String> configVersions, String desiredState)
    {
      EasyMock.reportMatcher(new ServiceRequestMatcher(clusterName, serviceName, configVersions, desiredState));
      return null;
    }
  }

  


  // ----- innner classes ----------------------------------------------------

  public static class ClusterRequestMatcher extends ClusterRequest implements IArgumentMatcher {

    public ClusterRequestMatcher(Long clusterId, String clusterName, String stackVersion, Set<String> hostNames) {
      super(clusterId, clusterName, stackVersion, hostNames);
    }

    @Override
    public boolean matches(Object o) {
      return o instanceof ClusterRequest &&
          eq(((ClusterRequest) o).getClusterId(), getClusterId()) &&
          eq(((ClusterRequest) o).getClusterName(), getClusterName()) &&
          eq(((ClusterRequest) o).getStackVersion(), getStackVersion()) &&
          eq(((ClusterRequest) o).getHostNames(), getHostNames());
    }

    @Override
    public void appendTo(StringBuffer stringBuffer) {
      stringBuffer.append("ClusterRequestMatcher(" + "" + ")");
    }
  }

  public static class ServiceRequestMatcher extends ServiceRequest implements IArgumentMatcher {

    public ServiceRequestMatcher(String clusterName, String serviceName, Map<String, String> configVersions, String desiredState) {
      super(clusterName, serviceName, configVersions, desiredState);
    }

    @Override
    public boolean matches(Object o) {
      return o instanceof ServiceRequest &&
          eq(((ServiceRequest) o).getClusterName(), getClusterName()) &&
          eq(((ServiceRequest) o).getServiceName(), getServiceName()) &&
          eq(((ServiceRequest) o).getConfigVersions(), getConfigVersions()) &&
          eq(((ServiceRequest) o).getDesiredState(), getDesiredState());

    }

    @Override
    public void appendTo(StringBuffer stringBuffer) {
      stringBuffer.append("ClusterRequestMatcher(" + "" + ")");
    }
  }

  public class TestObserver implements ResourceProviderObserver {

    ResourceProviderEvent lastEvent = null;

    @Override
    public void update(ResourceProviderEvent event) {
      lastEvent = event;
    }

    public ResourceProviderEvent getLastEvent() {
      return lastEvent;
    }
  }

}
