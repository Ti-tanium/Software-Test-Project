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
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.util.CharArrayBuffer;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link BasicLineFormatter}.
 */
public class TestBasicLineFormatter {

    @Test public void testSLFormatting() throws Exception{StatusLine statusline=new BasicStatusLine(HttpVersion.HTTP_1_1,HttpStatus.SC_OK,"OK");String s=BasicLineFormatter.formatStatusLine(statusline,null);Assert.assertEquals("HTTP/1.1 200 OK",s);statusline=new BasicStatusLine(HttpVersion.HTTP_1_1,HttpStatus.SC_OK,null);s=BasicLineFormatter.formatStatusLine(statusline,null);Assert.assertEquals("HTTP/1.1 200 ",s);}

}
