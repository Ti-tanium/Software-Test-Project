/*
 * Copyright 2010 The Apache Software Foundation
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

package org.apache.hadoop.hbase.stargate.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.stargate.MiniClusterTestBase;
import org.apache.hadoop.hbase.stargate.client.Client;
import org.apache.hadoop.hbase.stargate.client.Cluster;
import org.apache.hadoop.hbase.stargate.client.RemoteHTable;
import org.apache.hadoop.hbase.util.Bytes;

public class TestRemoteTable extends MiniClusterTestBase {

  static final String TABLE = "TestRemoteTable";
  static final byte[] ROW_1 = Bytes.toBytes("testrow1");
  static final byte[] ROW_2 = Bytes.toBytes("testrow2");
  static final byte[] ROW_3 = Bytes.toBytes("testrow3");
  static final byte[] ROW_4 = Bytes.toBytes("testrow4");
  static final byte[] COLUMN_1 = Bytes.toBytes("a");
  static final byte[] COLUMN_2 = Bytes.toBytes("b");
  static final byte[] COLUMN_3 = Bytes.toBytes("c");
  static final byte[] QUALIFIER_1 = Bytes.toBytes("1");
  static final byte[] QUALIFIER_2 = Bytes.toBytes("2");
  static final byte[] QUALIFIER_3 = Bytes.toBytes("3");
  static final byte[] VALUE_1 = Bytes.toBytes("testvalue1");
  static final byte[] VALUE_2 = Bytes.toBytes("testvalue2");
  static final byte[] VALUE_3 = Bytes.toBytes("testvalue3");

  static final long ONE_HOUR = 60 * 60 * 1000;
  static final long TS_2 = System.currentTimeMillis();
  static final long TS_1 = TS_2 - ONE_HOUR;

  Client client;
  HBaseAdmin admin;
  RemoteHTable remoteTable;

  public void testScanner() throws IOException{List<Put> puts=new ArrayList<Put>();Put put=new Put(ROW_1);put.add(COLUMN_1,QUALIFIER_1,VALUE_1);puts.add(put);put=new Put(ROW_2);put.add(COLUMN_1,QUALIFIER_1,VALUE_1);puts.add(put);put=new Put(ROW_3);put.add(COLUMN_1,QUALIFIER_1,VALUE_1);puts.add(put);put=new Put(ROW_4);put.add(COLUMN_1,QUALIFIER_1,VALUE_1);puts.add(put);remoteTable.put(puts);ResultScanner scanner=remoteTable.getScanner(new Scan());Result[] results=scanner.next(1);assertNotNull(results);assertEquals(1,results.length);assertTrue(Bytes.equals(ROW_1,results[0].getRow()));results=scanner.next(3);assertNotNull(results);assertEquals(3,results.length);assertTrue(Bytes.equals(ROW_2,results[0].getRow()));assertTrue(Bytes.equals(ROW_3,results[1].getRow()));assertTrue(Bytes.equals(ROW_4,results[2].getRow()));results=scanner.next(1);assertNull(results);scanner.close();}

}