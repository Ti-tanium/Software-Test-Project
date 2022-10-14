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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import org.apache.http.Header;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link HeaderGroup}.
 *
 */
public class TestHeaderGroup {

    @Test public void testSetHeaders(){HeaderGroup headergroup=new HeaderGroup();Header header1=new BasicHeader("name1","value1");Header header2=new BasicHeader("name2","value2");Header header3=new BasicHeader("name3","value3");headergroup.addHeader(header1);headergroup.setHeaders(new Header[]{header2,header3});Assert.assertEquals(2,headergroup.getAllHeaders().length);Assert.assertEquals(0,headergroup.getHeaders("name1").length);Assert.assertFalse(headergroup.containsHeader("name1"));Assert.assertEquals(1,headergroup.getHeaders("name2").length);Assert.assertTrue(headergroup.containsHeader("name2"));Assert.assertEquals(1,headergroup.getHeaders("name3").length);Assert.assertTrue(headergroup.containsHeader("name3"));headergroup.setHeaders(null);Assert.assertEquals(0,headergroup.getAllHeaders().length);}

}
