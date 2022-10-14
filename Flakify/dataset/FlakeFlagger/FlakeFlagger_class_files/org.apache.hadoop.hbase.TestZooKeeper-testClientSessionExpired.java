/**
 * Copyright 2009 The Apache Software Foundation
 *
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
package org.apache.hadoop.hbase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.zookeeper.ZooKeeperWrapper;
import org.apache.zookeeper.ZooKeeper;
import org.junit.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;

public class TestZooKeeper {
  private final Log LOG = LogFactory.getLog(this.getClass());

  private final static HBaseTestingUtility
      TEST_UTIL = new HBaseTestingUtility();

  private Configuration    conf;

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    TEST_UTIL.getConfiguration().setBoolean("dfs.support.append", true);
    TEST_UTIL.startMiniCluster(1);
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    conf = TEST_UTIL.getConfiguration();
  }

  /**
 * See HBASE-1232 and http://wiki.apache.org/hadoop/ZooKeeper/FAQ#4.
 * @throws IOException
 * @throws InterruptedException
 */@Test public void testClientSessionExpired() throws IOException,InterruptedException{new HTable(conf,HConstants.META_TABLE_NAME);ZooKeeperWrapper zkw=new ZooKeeperWrapper(conf,EmptyWatcher.instance);String quorumServers=zkw.getQuorumServers();int sessionTimeout=5 * 1000;HConnection connection=HConnectionManager.getConnection(conf);ZooKeeperWrapper connectionZK=connection.getZooKeeperWrapper();long sessionID=connectionZK.getSessionID();byte[] password=connectionZK.getSessionPassword();ZooKeeper zk=new ZooKeeper(quorumServers,sessionTimeout,EmptyWatcher.instance,sessionID,password);zk.close();Thread.sleep(sessionTimeout * 3L);System.err.println("ZooKeeper should have timed out");connection.relocateRegion(HConstants.ROOT_TABLE_NAME,HConstants.EMPTY_BYTE_ARRAY);}
}