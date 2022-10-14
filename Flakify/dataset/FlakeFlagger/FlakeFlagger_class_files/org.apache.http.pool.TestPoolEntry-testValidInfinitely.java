/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.http.pool;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.apache.http.HttpConnection;
import org.junit.Test;
import org.mockito.Mockito;

public class TestPoolEntry {

    static class MockPoolEntry extends PoolEntry<String, HttpConnection> {

        public MockPoolEntry(final String route,
                long timeToLive, final TimeUnit tunit) {
            super(null, route, Mockito.mock(HttpConnection.class), timeToLive, tunit);
        }

        public MockPoolEntry(final String route, final HttpConnection conn,
                long timeToLive, final TimeUnit tunit) {
            super(null, route, conn, timeToLive, tunit);
        }

    }

    @Test public void testValidInfinitely() throws Exception{MockPoolEntry entry1=new MockPoolEntry("route1",0L,TimeUnit.MILLISECONDS);Assert.assertEquals(Long.MAX_VALUE,entry1.getValidUnit());Assert.assertEquals(entry1.getValidUnit(),entry1.getExpiry());}

}
