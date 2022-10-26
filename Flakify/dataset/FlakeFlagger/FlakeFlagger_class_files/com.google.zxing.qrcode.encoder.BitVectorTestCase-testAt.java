/*
 * Copyright 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.qrcode.encoder;

import com.google.zxing.common.BitArray;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author satorux@google.com (Satoru Takabayashi) - creator
 * @author dswitkin@google.com (Daniel Switkin) - ported from C++
 */
public final class BitVectorTestCase extends Assert {

  private static long getUnsignedInt(BitArray v, int index) {
    long result = 0L;
    for (int i = 0, offset = index << 3; i < 32; i++) {
      if (v.get(offset + i)) {
        result |= 1L << (31 - i);
      }
    }
    return result;
  }

  @Test public void testAt(){BitArray v=new BitArray();v.appendBits(0xdead,16);assertTrue(v.get(0));assertTrue(v.get(1));assertFalse(v.get(2));assertTrue(v.get(3));assertTrue(v.get(4));assertTrue(v.get(5));assertTrue(v.get(6));assertFalse(v.get(7));assertTrue(v.get(8));assertFalse(v.get(9));assertTrue(v.get(10));assertFalse(v.get(11));assertTrue(v.get(12));assertTrue(v.get(13));assertFalse(v.get(14));assertTrue(v.get(15));}

}