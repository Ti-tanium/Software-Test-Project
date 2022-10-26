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

package org.apache.ambari.server.orm;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import org.apache.ambari.server.Role;
import org.apache.ambari.server.actionmanager.HostRoleStatus;
import org.apache.ambari.server.orm.dao.*;
import org.apache.ambari.server.orm.entities.*;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TestOrmImpl extends Assert {
  private static final Logger log = LoggerFactory.getLogger(TestOrmImpl.class);

  private static Injector injector;

  @Before
  public void setup() {
    injector = Guice.createInjector(new InMemoryDefaultTestModule());
    injector.getInstance(GuiceJpaInitializer.class);
    injector.getInstance(OrmTestHelper.class).createDefaultData();
  }

  private void createService(Date currentTime, String serviceName, String clusterName) {
    ClusterServiceDAO clusterServiceDAO = injector.getInstance(ClusterServiceDAO.class);
    ClusterDAO clusterDAO = injector.getInstance(ClusterDAO.class);
    ClusterEntity cluster = clusterDAO.findByName(clusterName);

    ClusterServiceEntity clusterServiceEntity = new ClusterServiceEntity();
    clusterServiceEntity.setClusterEntity(cluster);
    clusterServiceEntity.setServiceName(serviceName);

    cluster.getClusterServiceEntities().add(clusterServiceEntity);

    clusterServiceDAO.create(clusterServiceEntity);
    clusterDAO.merge(cluster);

    clusterServiceEntity = clusterServiceDAO.findByClusterAndServiceNames(clusterName, serviceName);
    assertNotNull(clusterServiceEntity);

    clusterServiceDAO.merge(clusterServiceEntity);
  }

  @Test public void testConcurrentModification() throws InterruptedException{final ClusterDAO clusterDAO=injector.getInstance(ClusterDAO.class);ClusterEntity clusterEntity=new ClusterEntity();clusterEntity.setClusterName("cluster1");clusterDAO.create(clusterEntity);clusterEntity=clusterDAO.findById(clusterEntity.getClusterId());assertEquals("cluster1",clusterEntity.getClusterName());Thread thread=new Thread(){@Override public void run(){ClusterEntity clusterEntity1=clusterDAO.findByName("cluster1");clusterEntity1.setClusterName("anotherName");clusterDAO.merge(clusterEntity1);}};thread.start();thread.join();clusterEntity=clusterDAO.findById(clusterEntity.getClusterId());assertEquals("anotherName",clusterEntity.getClusterName());thread=new Thread(){@Override public void run(){clusterDAO.removeByName("anotherName");}};thread.start();thread.join();assertNull(clusterDAO.findById(clusterEntity.getClusterId()));List<ClusterEntity> result=clusterDAO.findAll();thread=new Thread(){@Override public void run(){ClusterEntity temp=new ClusterEntity();temp.setClusterName("temp_cluster");clusterDAO.create(temp);}};thread.start();thread.join();assertEquals(result.size() + 1,(result=clusterDAO.findAll()).size());thread=new Thread(){@Override public void run(){ClusterEntity temp=new ClusterEntity();temp.setClusterName("temp_cluster2");clusterDAO.create(temp);}};thread.start();thread.join();assertEquals(result.size() + 1,(clusterDAO.findAll()).size());}

}