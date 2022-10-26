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

package org.apache.http.impl.nio.codecs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import org.apache.http.Header;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.ReadableByteChannelMock;
import org.apache.http.impl.io.HttpTransportMetricsImpl;
import org.apache.http.impl.nio.reactor.SessionInputBufferImpl;
import org.apache.http.nio.reactor.SessionInputBuffer;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.junit.Assert;
import org.junit.Test;

/**
 * Simple tests for {@link ChunkDecoder}.
 */
public class TestChunkDecoder {

    private static String convert(final ByteBuffer src) {
        src.flip();
        StringBuilder buffer = new StringBuilder(src.remaining());
        while (src.hasRemaining()) {
            buffer.append((char)(src.get() & 0xff));
        }
        return buffer.toString();
    }

    @Test public void testMalformedChunkEndingDecoding() throws Exception{String s="5\r\n01234\r\n5\r\n56789\n\r6\r\nabcdef\r\n0\r\n\r\n";ReadableByteChannel channel=new ReadableByteChannelMock(new String[]{s},"US-ASCII");HttpParams params=new BasicHttpParams();SessionInputBuffer inbuf=new SessionInputBufferImpl(1024,256,params);HttpTransportMetricsImpl metrics=new HttpTransportMetricsImpl();ChunkDecoder decoder=new ChunkDecoder(channel,inbuf,metrics);ByteBuffer dst=ByteBuffer.allocate(1024);try {decoder.read(dst);Assert.fail("MalformedChunkCodingException should have been thrown");} catch (MalformedChunkCodingException ex){}}

}