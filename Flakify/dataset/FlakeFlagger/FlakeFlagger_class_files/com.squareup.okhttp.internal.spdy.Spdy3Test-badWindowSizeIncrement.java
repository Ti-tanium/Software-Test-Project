/*
 * Copyright (C) 2014 Square, Inc.
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
package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import okio.ByteString;
import okio.OkBuffer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class Spdy3Test {
  static final int expectedStreamId = 15;

  @Test public void badWindowSizeIncrement() throws IOException {
    try {
      windowUpdate(0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("windowSizeIncrement must be between 1 and 0x7fffffff: 0", e.getMessage());
    }
    try {
      windowUpdate(0x80000000L);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("windowSizeIncrement must be between 1 and 0x7fffffff: 2147483648",
          e.getMessage());
    }
  }

  private void sendDataFrame(OkBuffer source) throws IOException {
    Spdy3.Writer writer = new Spdy3.Writer(new OkBuffer(), true);
    writer.sendDataFrame(expectedStreamId, 0, source, (int) source.size());
  }

  private void windowUpdate(long increment) throws IOException {
    new Spdy3.Writer(new OkBuffer(), true).windowUpdate(expectedStreamId, increment);
  }

  private OkBuffer sendGoAway(int lastGoodStreamId, ErrorCode errorCode, byte[] debugData)
      throws IOException {
    OkBuffer out = new OkBuffer();
    new Spdy3.Writer(out, true).goAway(lastGoodStreamId, errorCode, debugData);
    return out;
  }
}