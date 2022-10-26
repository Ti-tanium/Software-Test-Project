/*
 * Copyright (c) 2010-2018 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package org.java_websocket.util;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

/**
 * JUnit Test for the new ByteBufferUtils class
 */
public class ByteBufferUtilsTest {

    /**
     * A small byte array with some data
     */
    private static byte[] smallArray = {0, -1, -2, -3, -4};

    /**
     * A big byte array with some data
     */
    private static byte[] bigArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test public void testTransferByteBufferBigToSmall(){ByteBuffer small=ByteBuffer.wrap(smallArray);ByteBuffer big=ByteBuffer.wrap(bigArray);ByteBufferUtils.transferByteBuffer(big,small);assertArrayEquals("Big bytebuffer should not change",bigArray,big.array());assertEquals("Small bytebuffer not same to source 0",bigArray[0],small.get(0));assertEquals("Small bytebuffer not same to source 1",bigArray[1],small.get(1));assertEquals("Small bytebuffer not same to source 2",bigArray[2],small.get(2));assertEquals("Small bytebuffer not same to source 3",bigArray[3],small.get(3));assertEquals("Small bytebuffer not same to source 4",bigArray[4],small.get(4));}
}