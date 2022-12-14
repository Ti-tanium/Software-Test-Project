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
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.HBaseTestCase;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.KeyValueTestUtil;
import org.apache.hadoop.hbase.KeyValue.KeyComparator;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.regionserver.QueryMatcher.MatchCode;
import org.apache.hadoop.hbase.util.Bytes;


public class TestQueryMatcher extends HBaseTestCase
implements HConstants {
  private static final boolean PRINT = false;

  private byte [] row1;
  private byte [] row2;
  private byte [] fam1;
  private byte [] fam2;
  private byte [] col1;
  private byte [] col2;
  private byte [] col3;
  private byte [] col4;
  private byte [] col5;

  private byte [] data;

  private Get get;

  long ttl = Long.MAX_VALUE;
  KeyComparator rowComparator;

  /**
 * Verify that   {@link QueryMatcher}  only skips expired KeyValueinstances and does not exit early from the row (skipping later non-expired KeyValues).  This version mimics a Get with wildcard-inferred column qualifiers.
 * @throws IOException
 */public void testMatch_ExpiredWildcard() throws IOException{long testTTL=1000;MatchCode[] expected=new MatchCode[]{MatchCode.INCLUDE,MatchCode.INCLUDE,MatchCode.SKIP,MatchCode.INCLUDE,MatchCode.SKIP,MatchCode.NEXT};QueryMatcher qm=new QueryMatcher(get,fam2,null,testTTL,rowComparator,1);long now=System.currentTimeMillis();KeyValue[] kvs=new KeyValue[]{new KeyValue(row1,fam2,col1,now - 100,data),new KeyValue(row1,fam2,col2,now - 50,data),new KeyValue(row1,fam2,col3,now - 5000,data),new KeyValue(row1,fam2,col4,now - 500,data),new KeyValue(row1,fam2,col5,now - 10000,data),new KeyValue(row2,fam1,col1,now - 10,data)};List<MatchCode> actual=new ArrayList<MatchCode>(kvs.length);for (KeyValue kv:kvs){actual.add(qm.match(kv));}assertEquals(expected.length,actual.size());for (int i=0;i < expected.length;i++){if (PRINT){System.out.println("expected " + expected[i] + ", actual " + actual.get(i));}assertEquals(expected[i],actual.get(i));}}
}
