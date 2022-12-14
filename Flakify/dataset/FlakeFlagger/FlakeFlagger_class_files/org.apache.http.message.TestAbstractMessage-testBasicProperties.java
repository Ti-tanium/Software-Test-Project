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

package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HttpMessage;
import org.apache.http.ProtocolVersion;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Header}.
 *
 */
public class TestAbstractMessage {

    static class TestHttpMessage extends AbstractHttpMessage {

        public TestHttpMessage() {
            super();
        }

        public ProtocolVersion getProtocolVersion() {
            return HttpProtocolParams.getVersion(this.getParams());
        }

    }

    @Test public void testBasicProperties(){HttpMessage message=new TestHttpMessage();Assert.assertNotNull(message.getParams());Assert.assertNotNull(message.headerIterator());Header[] headers=message.getAllHeaders();Assert.assertNotNull(headers);Assert.assertEquals(0,headers.length);}

}

