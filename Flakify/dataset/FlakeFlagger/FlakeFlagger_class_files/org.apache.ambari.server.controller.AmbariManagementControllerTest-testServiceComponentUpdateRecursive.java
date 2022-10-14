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

  @Test public void testServiceComponentUpdateRecursive() throws AmbariException{String clusterName="foo1";createCluster(clusterName);String serviceName1="HDFS";createService(clusterName,serviceName1,null);String componentName1="NAMENODE";String componentName2="DATANODE";String componentName3="HDFS_CLIENT";createServiceComponent(clusterName,serviceName1,componentName1,State.INIT);createServiceComponent(clusterName,serviceName1,componentName2,State.INIT);createServiceComponent(clusterName,serviceName1,componentName3,State.INIT);String host1="h1";clusters.addHost(host1);clusters.getHost("h1").setOsType("centos5");clusters.getHost("h1").persist();String host2="h2";clusters.addHost(host2);clusters.getHost("h2").setOsType("centos5");clusters.getHost("h2").persist();clusters.mapHostToCluster(host1,clusterName);clusters.mapHostToCluster(host2,clusterName);Set<ServiceComponentHostRequest> set1=new HashSet<ServiceComponentHostRequest>();ServiceComponentHostRequest r1=new ServiceComponentHostRequest(clusterName,serviceName1,componentName1,host1,null,State.INIT.toString());ServiceComponentHostRequest r2=new ServiceComponentHostRequest(clusterName,serviceName1,componentName2,host1,null,State.INIT.toString());ServiceComponentHostRequest r3=new ServiceComponentHostRequest(clusterName,serviceName1,componentName1,host2,null,State.INIT.toString());ServiceComponentHostRequest r4=new ServiceComponentHostRequest(clusterName,serviceName1,componentName2,host2,null,State.INIT.toString());ServiceComponentHostRequest r5=new ServiceComponentHostRequest(clusterName,serviceName1,componentName3,host1,null,State.INIT.toString());set1.add(r1);set1.add(r2);set1.add(r3);set1.add(r4);set1.add(r5);controller.createHostComponents(set1);Cluster c1=clusters.getCluster(clusterName);Service s1=c1.getService(serviceName1);ServiceComponent sc1=s1.getServiceComponent(componentName1);ServiceComponent sc2=s1.getServiceComponent(componentName2);ServiceComponent sc3=s1.getServiceComponent(componentName3);ServiceComponentHost sch1=sc1.getServiceComponentHost(host1);ServiceComponentHost sch2=sc2.getServiceComponentHost(host1);ServiceComponentHost sch3=sc1.getServiceComponentHost(host2);ServiceComponentHost sch4=sc2.getServiceComponentHost(host2);ServiceComponentHost sch5=sc3.getServiceComponentHost(host1);s1.setDesiredState(State.INSTALLED);sc1.setDesiredState(State.INIT);sc2.setDesiredState(State.INIT);sc3.setDesiredState(State.STARTED);sch1.setDesiredState(State.INSTALLED);sch2.setDesiredState(State.INSTALLED);sch3.setDesiredState(State.STARTED);sch4.setDesiredState(State.INSTALLED);sch5.setDesiredState(State.INSTALLED);sch1.setState(State.INSTALLED);sch2.setState(State.INSTALLED);sch3.setState(State.STARTED);sch4.setState(State.INSTALLED);sch5.setState(State.INSTALLED);Set<ServiceComponentRequest> reqs=new HashSet<ServiceComponentRequest>();ServiceComponentRequest req1,req2,req3;try {reqs.clear();req1=new ServiceComponentRequest(clusterName,serviceName1,sc1.getName(),null,State.INIT.toString());reqs.add(req1);controller.updateComponents(reqs);fail("Expected failure for invalid state update");} catch (Exception e){}s1.setDesiredState(State.INSTALLED);sc1.setDesiredState(State.STARTED);sc2.setDesiredState(State.INSTALLED);sc3.setDesiredState(State.STARTED);sch1.setDesiredState(State.INIT);sch2.setDesiredState(State.INIT);sch3.setDesiredState(State.INIT);sch4.setDesiredState(State.INIT);sch5.setDesiredState(State.INIT);sch1.setState(State.INIT);sch2.setState(State.INSTALLED);sch3.setState(State.INIT);sch4.setState(State.INSTALLED);sch5.setState(State.INSTALLED);try {reqs.clear();req1=new ServiceComponentRequest(clusterName,serviceName1,sc1.getName(),null,State.STARTED.toString());reqs.add(req1);controller.updateComponents(reqs);fail("Expected failure for invalid state update");} catch (Exception e){}s1.setDesiredState(State.INSTALLED);sc1.setDesiredState(State.STARTED);sc2.setDesiredState(State.INIT);sc3.setDesiredState(State.STARTED);sch1.setDesiredState(State.INIT);sch2.setDesiredState(State.INIT);sch3.setDesiredState(State.INIT);sch4.setDesiredState(State.INIT);sch5.setDesiredState(State.INIT);sch1.setState(State.STARTED);sch2.setState(State.INIT);sch3.setState(State.INSTALLED);sch4.setState(State.STOP_FAILED);sch5.setState(State.INIT);reqs.clear();req1=new ServiceComponentRequest(clusterName,serviceName1,sc1.getName(),null,State.INSTALLED.toString());req2=new ServiceComponentRequest(clusterName,serviceName1,sc2.getName(),null,State.INSTALLED.toString());req3=new ServiceComponentRequest(clusterName,serviceName1,sc3.getName(),null,State.INSTALLED.toString());reqs.add(req1);reqs.add(req2);reqs.add(req3);RequestStatusResponse trackAction=controller.updateComponents(reqs);Assert.assertEquals(State.INSTALLED,s1.getDesiredState());Assert.assertEquals(State.INSTALLED,sc1.getDesiredState());Assert.assertEquals(State.INSTALLED,sc2.getDesiredState());Assert.assertEquals(State.INSTALLED,sc3.getDesiredState());Assert.assertEquals(State.INSTALLED,sch1.getDesiredState());Assert.assertEquals(State.INSTALLED,sch2.getDesiredState());Assert.assertEquals(State.INSTALLED,sch3.getDesiredState());Assert.assertEquals(State.INSTALLED,sch4.getDesiredState());Assert.assertEquals(State.INSTALLED,sch5.getDesiredState());Assert.assertEquals(State.STARTED,sch1.getState());Assert.assertEquals(State.INIT,sch2.getState());Assert.assertEquals(State.INSTALLED,sch3.getState());Assert.assertEquals(State.STOP_FAILED,sch4.getState());Assert.assertEquals(State.INIT,sch5.getState());long requestId=trackAction.getRequestId();List<Stage> stages=actionDB.getAllStages(requestId);Assert.assertTrue(!stages.isEmpty());for (Stage stage:stages){LOG.debug("Stage dump: " + stage.toString());}sch1.setState(State.INSTALLED);sch2.setState(State.INSTALLED);sch3.setState(State.INSTALLED);sch4.setState(State.INSTALLED);sch5.setState(State.INSTALLED);reqs.clear();req1=new ServiceComponentRequest(clusterName,serviceName1,sc1.getName(),null,State.INSTALLED.toString());req2=new ServiceComponentRequest(clusterName,serviceName1,sc2.getName(),null,State.INSTALLED.toString());reqs.add(req1);reqs.add(req2);trackAction=controller.updateComponents(reqs);Assert.assertNull(trackAction);}

  private void createUser(String userName) throws Exception {
    UserRequest request = new UserRequest(userName);
    request.setPassword("password");

    controller.createUsers(new HashSet<UserRequest>(Collections.singleton(request)));
  }

}
