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


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.RowLock;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.io.HbaseMapWritable;
import org.apache.hadoop.hbase.io.TimeRange;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Writables;
import org.apache.hadoop.io.DataInputBuffer;

/**
 * Test HBase Writables serializations
 */
public class TestSerialization extends HBaseTestCase {

  public void testPut() throws Exception{
    byte[] row = "row".getBytes();
    byte[] fam = "fam".getBytes();
    byte[] qf1 = "qf1".getBytes();
    byte[] qf2 = "qf2".getBytes();
    byte[] qf3 = "qf3".getBytes();
    byte[] qf4 = "qf4".getBytes();
    byte[] qf5 = "qf5".getBytes();
    byte[] qf6 = "qf6".getBytes();
    byte[] qf7 = "qf7".getBytes();
    byte[] qf8 = "qf8".getBytes();

    long ts = System.currentTimeMillis();
    byte[] val = "val".getBytes();

    Put put = new Put(row);
    put.add(fam, qf1, ts, val);
    put.add(fam, qf2, ts, val);
    put.add(fam, qf3, ts, val);
    put.add(fam, qf4, ts, val);
    put.add(fam, qf5, ts, val);
    put.add(fam, qf6, ts, val);
    put.add(fam, qf7, ts, val);
    put.add(fam, qf8, ts, val);

    byte[] sb = Writables.getBytes(put);
    Put desPut = (Put)Writables.getWritable(sb, new Put());

    //Timing test
//    long start = System.nanoTime();
//    desPut = (Put)Writables.getWritable(sb, new Put());
//    long stop = System.nanoTime();
//    System.out.println("timer " +(stop-start));

    assertTrue(Bytes.equals(put.getRow(), desPut.getRow()));
    List<KeyValue> list = null;
    List<KeyValue> desList = null;
    for(Map.Entry<byte[], List<KeyValue>> entry : put.getFamilyMap().entrySet()){
      assertTrue(desPut.getFamilyMap().containsKey(entry.getKey()));
      list = entry.getValue();
      desList = desPut.getFamilyMap().get(entry.getKey());
      for(int i=0; i<list.size(); i++){
        assertTrue(list.get(i).equals(desList.get(i)));
      }
    }
  }
}