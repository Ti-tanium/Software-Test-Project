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

import java.util.NoSuchElementException;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.junit.Assert;
import org.junit.Test;


/**
 * Tests for {@link BasicHeaderIterator}.
 *
 */
public class TestBasicHeaderIterator {

    @Test public void testInterspersed(){Header[] headers=new Header[]{new BasicHeader("yellow","00"),new BasicHeader("maroon","01"),new BasicHeader("orange","02"),new BasicHeader("orange","03"),new BasicHeader("orange","04"),new BasicHeader("yellow","05"),new BasicHeader("maroon","06"),new BasicHeader("maroon","07"),new BasicHeader("maroon","08"),new BasicHeader("yellow","09"),new BasicHeader("maroon","0a"),new BasicHeader("yellow","0b"),new BasicHeader("orange","0c"),new BasicHeader("yellow","0d"),new BasicHeader("orange","0e")};HeaderIterator hit=new BasicHeaderIterator(headers,null);Assert.assertTrue(hit.hasNext());Assert.assertEquals("0",headers[0],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("1",headers[1],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("2",headers[2],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("3",headers[3],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("4",headers[4],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("5",headers[5],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("6",headers[6],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("7",headers[7],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("8",headers[8],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("9",headers[9],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("a",headers[10],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("b",headers[11],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("c",headers[12],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("d",headers[13],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("e",headers[14],hit.nextHeader());Assert.assertFalse(hit.hasNext());hit=new BasicHeaderIterator(headers,"Yellow");Assert.assertTrue(hit.hasNext());Assert.assertEquals("0",headers[0],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("5",headers[5],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("9",headers[9],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("b",headers[11],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("d",headers[13],hit.nextHeader());Assert.assertFalse(hit.hasNext());hit=new BasicHeaderIterator(headers,"marOOn");Assert.assertTrue(hit.hasNext());Assert.assertEquals("1",headers[1],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("6",headers[6],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("7",headers[7],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("8",headers[8],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("a",headers[10],hit.nextHeader());Assert.assertFalse(hit.hasNext());hit=new BasicHeaderIterator(headers,"OranGe");Assert.assertTrue(hit.hasNext());Assert.assertEquals("2",headers[2],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("3",headers[3],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("4",headers[4],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("b",headers[12],hit.nextHeader());Assert.assertTrue(hit.hasNext());Assert.assertEquals("e",headers[14],hit.nextHeader());Assert.assertFalse(hit.hasNext());}
}
