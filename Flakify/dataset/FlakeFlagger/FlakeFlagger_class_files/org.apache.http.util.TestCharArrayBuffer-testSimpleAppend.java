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

package org.apache.http.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link CharArrayBuffer}.
 *
 */
public class TestCharArrayBuffer {

    @Test
    public void testSimpleAppend() throws Exception {
        CharArrayBuffer buffer = new CharArrayBuffer(16);
        Assert.assertEquals(16, buffer.capacity());
        Assert.assertEquals(0, buffer.length());
        char[] b1 = buffer.toCharArray();
        Assert.assertNotNull(b1);
        Assert.assertEquals(0, b1.length);
        Assert.assertTrue(buffer.isEmpty());
        Assert.assertFalse(buffer.isFull());

        char[] tmp = new char[] { '1', '2', '3', '4'};
        buffer.append(tmp, 0, tmp.length);
        Assert.assertEquals(16, buffer.capacity());
        Assert.assertEquals(4, buffer.length());
        Assert.assertFalse(buffer.isEmpty());
        Assert.assertFalse(buffer.isFull());

        char[] b2 = buffer.toCharArray();
        Assert.assertNotNull(b2);
        Assert.assertEquals(4, b2.length);
        for (int i = 0; i < tmp.length; i++) {
            Assert.assertEquals(tmp[i], b2[i]);
            Assert.assertEquals(tmp[i], buffer.charAt(i));
        }
        Assert.assertEquals("1234", buffer.toString());

        buffer.clear();
        Assert.assertEquals(16, buffer.capacity());
        Assert.assertEquals(0, buffer.length());
        Assert.assertTrue(buffer.isEmpty());
        Assert.assertFalse(buffer.isFull());
    }

}
