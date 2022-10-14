/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.common.threadlocal;

import org.junit.Assert;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class InternalThreadLocalTest {

    private static final int THREADS = 10;

    private static final int PERFORMANCE_THREAD_COUNT = 1000;

    private static final int GET_COUNT = 1000000;

    @Test public void testRemoveAll() throws InterruptedException{final InternalThreadLocal<Integer> internalThreadLocal=new InternalThreadLocal<Integer>();internalThreadLocal.set(1);Assert.assertTrue("set failed",internalThreadLocal.get() == 1);final InternalThreadLocal<String> internalThreadLocalString=new InternalThreadLocal<String>();internalThreadLocalString.set("value");Assert.assertTrue("set failed","value".equals(internalThreadLocalString.get()));InternalThreadLocal.removeAll();Assert.assertTrue("removeAll failed!",internalThreadLocal.get() == null);Assert.assertTrue("removeAll failed!",internalThreadLocalString.get() == null);}
}