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

package org.apache.hadoop.hbase.client;

import java.util.NavigableMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.HBaseClusterTestCase;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Test versions.
 * Does shutdown in middle of test to prove versions work across restart.
 */
public class TestGetRowVersions extends HBaseClusterTestCase {
  private static final Log LOG = LogFactory.getLog(TestGetRowVersions.class);

  private static final String TABLE_NAME = "test";
  private static final byte [] CONTENTS = Bytes.toBytes("contents");
  private static final byte [] ROW = Bytes.toBytes("row");
  private static final byte [] VALUE1 = Bytes.toBytes("value1");
  private static final byte [] VALUE2 = Bytes.toBytes("value2");
  private static final long TIMESTAMP1 = 100L;
  private static final long TIMESTAMP2 = 200L;
  private HBaseAdmin admin = null;
  private HTable table = null;

  /**
 * @throws Exception  
 */public void testGetRowMultipleVersions() throws Exception{Put put=new Put(ROW,TIMESTAMP1,null);put.add(CONTENTS,CONTENTS,VALUE1);this.table.put(put);this.cluster.shutdown();this.zooKeeperCluster.shutdown();LOG.debug("HBase cluster shut down -- restarting");this.hBaseClusterSetup();this.table=new HTable(conf,TABLE_NAME);put=new Put(ROW,TIMESTAMP2,null);put.add(CONTENTS,CONTENTS,VALUE2);this.table.put(put);Get get=new Get(ROW);Result r=table.get(get);assertNotNull(r);assertFalse(r.isEmpty());assertTrue(r.size() == 1);byte[] value=r.getValue(CONTENTS,CONTENTS);assertTrue(value.length != 0);assertTrue(Bytes.equals(value,VALUE2));get=new Get(ROW);get.setMaxVersions();r=table.get(get);assertTrue(r.size() == 2);value=r.getValue(CONTENTS,CONTENTS);assertTrue(value.length != 0);assertTrue(Bytes.equals(value,VALUE2));NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map=r.getMap();NavigableMap<byte[], NavigableMap<Long, byte[]>> familyMap=map.get(CONTENTS);NavigableMap<Long, byte[]> versionMap=familyMap.get(CONTENTS);assertTrue(versionMap.size() == 2);assertTrue(Bytes.equals(VALUE1,versionMap.get(TIMESTAMP1)));assertTrue(Bytes.equals(VALUE2,versionMap.get(TIMESTAMP2)));}
}