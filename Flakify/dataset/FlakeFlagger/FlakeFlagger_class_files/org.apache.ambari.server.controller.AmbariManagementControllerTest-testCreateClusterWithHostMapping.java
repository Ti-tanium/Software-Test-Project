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

package org.apache.ambari.server.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.apache.ambari.server.AmbariException;
import org.apache.ambari.server.ClusterNotFoundException;
import org.apache.ambari.server.Role;
import org.apache.ambari.server.RoleCommand;
import org.apache.ambari.server.ServiceNotFoundException;
import org.apache.ambari.server.actionmanager.*;
import org.apache.ambari.server.api.services.AmbariMetaInfo;
import org.apache.ambari.server.orm.GuiceJpaInitializer;
import org.apache.ambari.server.orm.InMemoryDefaultTestModule;
import org.apache.ambari.server.orm.dao.RoleDAO;
import org.apache.ambari.server.orm.entities.RoleEntity;
import org.apache.ambari.server.security.authorization.Users;
import org.apache.ambari.server.state.Cluster;
import org.apache.ambari.server.state.Clusters;
import org.apache.ambari.server.state.Config;
import org.apache.ambari.server.state.ConfigImpl;
import org.apache.ambari.server.state.Host;
import org.apache.ambari.server.state.Service;
import org.apache.ambari.server.state.ServiceComponent;
import org.apache.ambari.server.state.ServiceComponentFactory;
import org.apache.ambari.server.state.ServiceComponentHost;
import org.apache.ambari.server.state.ServiceComponentHostFactory;
import org.apache.ambari.server.state.ServiceFactory;
import org.apache.ambari.server.state.StackId;
import org.apache.ambari.server.state.State;
import org.apache.ambari.server.state.svccomphost.ServiceComponentHostStartEvent;
import org.apache.ambari.server.utils.StageUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

public class AmbariManagementControllerTest {

  private static final Logger LOG =
      LoggerFactory.getLogger(AmbariManagementControllerTest.class);

  private AmbariManagementController controller;
  private Clusters clusters;
  private ActionDBAccessor actionDB;
  private Injector injector;
  private ServiceFactory serviceFactory;
  private ServiceComponentFactory serviceComponentFactory;
  private ServiceComponentHostFactory serviceComponentHostFactory;
  private AmbariMetaInfo ambariMetaInfo;
  private Users users;

  @Before
  public void setup() throws Exception {
    injector = Guice.createInjector(new InMemoryDefaultTestModule());
    injector.getInstance(GuiceJpaInitializer.class);
    clusters = injector.getInstance(Clusters.class);
    actionDB = injector.getInstance(ActionDBAccessor.class);
    controller = injector.getInstance(AmbariManagementController.class);
    serviceFactory = injector.getInstance(ServiceFactory.class);
    serviceComponentFactory = injector.getInstance(
        ServiceComponentFactory.class);
    serviceComponentHostFactory = injector.getInstance(
        ServiceComponentHostFactory.class);
    ambariMetaInfo = injector.getInstance(AmbariMetaInfo.class);
    ambariMetaInfo.init();
    users = injector.getInstance(Users.class);
  }

  private void createCluster(String clusterName) throws AmbariException {
    ClusterRequest r = new ClusterRequest(null, clusterName, "HDP-0.1", null);
    controller.createCluster(r);
  }

  private void createService(String clusterName,
      String serviceName, State desiredState) throws AmbariException {
    String dStateStr = null;
    if (desiredState != null) {
      dStateStr = desiredState.toString();
    }
    ServiceRequest r1 = new ServiceRequest(clusterName, serviceName, null,
        dStateStr);
    Set<ServiceRequest> requests = new HashSet<ServiceRequest>();
    requests.add(r1);
    controller.createServices(requests);
  }

  private void createServiceComponent(String clusterName,
      String serviceName, String componentName, State desiredState)
          throws AmbariException {
    String dStateStr = null;
    if (desiredState != null) {
      dStateStr = desiredState.toString();
    }
    ServiceComponentRequest r = new ServiceComponentRequest(clusterName,
        serviceName, componentName, null, dStateStr);
    Set<ServiceComponentRequest> requests =
        new HashSet<ServiceComponentRequest>();
    requests.add(r);
    controller.createComponents(requests);
  }

  private void createServiceComponentHost(String clusterName,
      String serviceName, String componentName, String hostname,
      State desiredState) throws AmbariException {
    String dStateStr = null;
    if (desiredState != null) {
      dStateStr = desiredState.toString();
    }
    ServiceComponentHostRequest r = new ServiceComponentHostRequest(clusterName,
        serviceName, componentName, hostname, null, dStateStr);
    Set<ServiceComponentHostRequest> requests =
        new HashSet<ServiceComponentHostRequest>();
    requests.add(r);
    controller.createHostComponents(requests);
  }

  @Test public void testCreateClusterWithHostMapping() throws AmbariException{Set<String> hostNames=new HashSet<String>();hostNames.add("h1");hostNames.add("h2");ClusterRequest r=new ClusterRequest(null,"c1","HDP-0.1",hostNames);try {controller.createCluster(r);fail("Expected create cluster to fail for invalid hosts");} catch (Exception e){}try {clusters.getCluster("c1");fail("Expected to fail for non created cluster");} catch (ClusterNotFoundException e){}clusters.addHost("h1");clusters.addHost("h2");clusters.getHost("h1").setOsType("redhat6");clusters.getHost("h2").setOsType("redhat6");clusters.getHost("h1").persist();clusters.getHost("h2").persist();controller.createCluster(r);Assert.assertNotNull(clusters.getCluster("c1"));}

  private void createUser(String userName) throws Exception {
    UserRequest request = new UserRequest(userName);
    request.setPassword("password");

    controller.createUsers(new HashSet<UserRequest>(Collections.singleton(request)));
  }

}
