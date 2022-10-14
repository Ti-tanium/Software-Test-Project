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

  public void testResult() throws Exception {
	byte[] rowA = Bytes.toBytes("rowA");
	byte[] famA = Bytes.toBytes("famA");
	byte[] qfA = Bytes.toBytes("qfA");
	byte[] valueA = Bytes.toBytes("valueA");
	byte[] rowB = Bytes.toBytes("rowB");
	byte[] famB = Bytes.toBytes("famB");
	byte[] qfB = Bytes.toBytes("qfB");
	byte[] valueB = Bytes.toBytes("valueB");
	KeyValue kvA = new KeyValue(rowA, famA, qfA, valueA);
	KeyValue kvB = new KeyValue(rowB, famB, qfB, valueB);
	Result result = new Result(new KeyValue[] { kvA, kvB });
	byte[] rb = Writables.getBytes(result);
	Result deResult = (Result) Writables.getWritable(rb, new Result());
	assertTrue("results are not equivalent, first key mismatch", result.sorted()[0].equals(deResult.sorted()[0]));
	assertTrue("results are not equivalent, second key mismatch", result.sorted()[1].equals(deResult.sorted()[1]));
	Result r = new Result();
	byte[] b = Writables.getBytes(r);
	Result deserialized = (Result) Writables.getWritable(b, new Result());
	assertEquals(r.size(), deserialized.size());
}
}