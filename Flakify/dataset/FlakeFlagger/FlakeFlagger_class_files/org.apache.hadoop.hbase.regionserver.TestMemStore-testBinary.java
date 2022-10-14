/*
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
package org.apache.hadoop.hbase.regionserver;

import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.KeyValueTestUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

/** memstore test case */
public class TestMemStore extends TestCase {
  private final Log LOG = LogFactory.getLog(this.getClass());
  private MemStore memstore;
  private static final int ROW_COUNT = 10;
  private static final int QUALIFIER_COUNT = ROW_COUNT;
  private static final byte [] FAMILY = Bytes.toBytes("column");
  private static final byte [] CONTENTS = Bytes.toBytes("contents");
  private static final byte [] BASIC = Bytes.toBytes("basic");
  private static final String CONTENTSTR = "contentstr";

  public void testBinary() throws IOException{MemStore mc=new MemStore(KeyValue.ROOT_COMPARATOR);final int start=43;final int end=46;for (int k=start;k <= end;k++){byte[] kk=Bytes.toBytes(k);byte[] row=Bytes.toBytes(".META.,table," + Bytes.toString(kk) + ",1," + k);KeyValue key=new KeyValue(row,CONTENTS,BASIC,System.currentTimeMillis(),(CONTENTSTR + k).getBytes(HConstants.UTF8_ENCODING));mc.add(key);System.out.println(key);}int index=start;for (KeyValue kv:mc.kvset){System.out.println(kv);byte[] b=kv.getRow();String str=Bytes.toString(b,13,4);byte[] bb=Bytes.toBytes(index);String bbStr=Bytes.toString(bb);assertEquals(str,bbStr);index++;}}

  //////////////////////////////////////////////////////////////////////////////
  // Get tests
  //////////////////////////////////////////////////////////////////////////////

  //////////////////////////////////////////////////////////////////////////////
  // Helpers
  //////////////////////////////////////////////////////////////////////////////
  private byte [] makeQualifier(final int i1, final int i2){
    return Bytes.toBytes(Integer.toString(i1) + ";" +
        Integer.toString(i2));
  }

  /**
   * Adds {@link #ROW_COUNT} rows and {@link #COLUMNS_COUNT}
   * @param hmc Instance to add rows to.
   * @return How many rows we added.
   * @throws IOException
   */
  private int addRows(final MemStore hmc) {
    return addRows(hmc, HConstants.LATEST_TIMESTAMP);
  }

  /**
   * Adds {@link #ROW_COUNT} rows and {@link #COLUMNS_COUNT}
   * @param hmc Instance to add rows to.
   * @return How many rows we added.
   * @throws IOException
   */
  private int addRows(final MemStore hmc, final long ts) {
    for (int i = 0; i < ROW_COUNT; i++) {
      long timestamp = ts == HConstants.LATEST_TIMESTAMP?
        System.currentTimeMillis(): ts;
      for (int ii = 0; ii < QUALIFIER_COUNT; ii++) {
        byte [] row = Bytes.toBytes(i);
        byte [] qf = makeQualifier(i, ii);
        hmc.add(new KeyValue(row, FAMILY, qf, timestamp, qf));
      }
    }
    return ROW_COUNT;
  }

  private void runSnapshot(final MemStore hmc) throws UnexpectedException {
    // Save off old state.
    int oldHistorySize = hmc.getSnapshot().size();
    hmc.snapshot();
    KeyValueSkipListSet ss = hmc.getSnapshot();
    // Make some assertions about what just happened.
    assertTrue("History size has not increased", oldHistorySize < ss.size());
    hmc.clearSnapshot(ss);
  }

  private void isExpectedRowWithoutTimestamps(final int rowIndex,
      List<KeyValue> kvs) {
    int i = 0;
    for (KeyValue kv: kvs) {
      String expectedColname = Bytes.toString(makeQualifier(rowIndex, i++));
      String colnameStr = Bytes.toString(kv.getQualifier());
      assertEquals("Column name", colnameStr, expectedColname);
      // Value is column name as bytes.  Usually result is
      // 100 bytes in size at least. This is the default size
      // for BytesWriteable.  For comparison, convert bytes to
      // String and trim to remove trailing null bytes.
      String colvalueStr = Bytes.toString(kv.getBuffer(), kv.getValueOffset(),
        kv.getValueLength());
      assertEquals("Content", colnameStr, colvalueStr);
    }
  }

  private KeyValue getDeleteKV(byte [] row) {
    return new KeyValue(row, Bytes.toBytes("test_col"), null,
      HConstants.LATEST_TIMESTAMP, KeyValue.Type.Delete, null);
  }

  private KeyValue getKV(byte [] row, byte [] value) {
    return new KeyValue(row, Bytes.toBytes("test_col"), null,
      HConstants.LATEST_TIMESTAMP, value);
  }
}
