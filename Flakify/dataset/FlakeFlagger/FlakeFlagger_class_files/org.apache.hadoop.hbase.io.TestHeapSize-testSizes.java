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

package org.apache.hadoop.hbase.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.hfile.CachedBlock;
import org.apache.hadoop.hbase.io.hfile.LruBlockCache;
import org.apache.hadoop.hbase.regionserver.HRegion;
import org.apache.hadoop.hbase.regionserver.MemStore;
import org.apache.hadoop.hbase.regionserver.Store;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.ClassSize;

/**
 * Testing the sizing that HeapSize offers and compares to the size given by
 * ClassSize.
 */
public class TestHeapSize extends TestCase {
  static final Log LOG = LogFactory.getLog(TestHeapSize.class);
  // List of classes implementing HeapSize
  // BatchOperation, BatchUpdate, BlockIndex, Entry, Entry<K,V>, HStoreKey
  // KeyValue, LruBlockCache, LruHashMap<K,V>, Put, HLogKey

  /**
 * Testing the classes that implements HeapSize and are a part of 0.20. Some are not tested here for example BlockIndex which is tested in TestHFile since it is a non public class
 * @throws IOException
 */@SuppressWarnings("unchecked") public void testSizes() throws IOException{Class cl=null;long expected=0L;long actual=0L;cl=KeyValue.class;expected=ClassSize.estimateBase(cl,false);KeyValue kv=new KeyValue();actual=kv.heapSize();if (expected != actual){ClassSize.estimateBase(cl,true);assertEquals(expected,actual);}cl=Put.class;expected=ClassSize.estimateBase(cl,false);expected+=ClassSize.TREEMAP;Put put=new Put(Bytes.toBytes(""));actual=put.heapSize();if (expected != actual){ClassSize.estimateBase(cl,true);assertEquals(expected,actual);}cl=LruBlockCache.class;actual=LruBlockCache.CACHE_FIXED_OVERHEAD;expected=ClassSize.estimateBase(cl,false);if (expected != actual){ClassSize.estimateBase(cl,true);assertEquals(expected,actual);}cl=CachedBlock.class;actual=CachedBlock.PER_BLOCK_OVERHEAD;expected=ClassSize.estimateBase(cl,false);expected+=ClassSize.estimateBase(String.class,false);expected+=ClassSize.estimateBase(ByteBuffer.class,false);if (expected != actual){ClassSize.estimateBase(cl,true);ClassSize.estimateBase(String.class,true);ClassSize.estimateBase(ByteBuffer.class,true);assertEquals(expected,actual);}cl=MemStore.class;actual=MemStore.FIXED_OVERHEAD;expected=ClassSize.estimateBase(cl,false);if (expected != actual){ClassSize.estimateBase(cl,true);assertEquals(expected,actual);}actual=MemStore.DEEP_OVERHEAD;expected=ClassSize.estimateBase(cl,false);expected+=ClassSize.estimateBase(ReentrantReadWriteLock.class,false);expected+=ClassSize.estimateBase(AtomicLong.class,false);expected+=ClassSize.estimateBase(ConcurrentSkipListMap.class,false);expected+=ClassSize.estimateBase(ConcurrentSkipListMap.class,false);expected+=ClassSize.estimateBase(CopyOnWriteArraySet.class,false);expected+=ClassSize.estimateBase(CopyOnWriteArrayList.class,false);if (expected != actual){ClassSize.estimateBase(cl,true);ClassSize.estimateBase(ReentrantReadWriteLock.class,true);ClassSize.estimateBase(AtomicLong.class,true);ClassSize.estimateBase(ConcurrentSkipListMap.class,true);ClassSize.estimateBase(CopyOnWriteArraySet.class,true);ClassSize.estimateBase(CopyOnWriteArrayList.class,true);assertEquals(expected,actual);}cl=Store.class;actual=Store.FIXED_OVERHEAD;expected=ClassSize.estimateBase(cl,false);if (expected != actual){ClassSize.estimateBase(cl,true);assertEquals(expected,actual);}cl=HRegion.class;actual=HRegion.FIXED_OVERHEAD;expected=ClassSize.estimateBase(cl,false);if (expected != actual){ClassSize.estimateBase(cl,true);assertEquals(expected,actual);}}

}
