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
package okio;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public final class OkBufferTest {
  private List<Integer> moveBytesBetweenBuffers(String... contents) {
    StringBuilder expected = new StringBuilder();
    OkBuffer buffer = new OkBuffer();
    for (String s : contents) {
      OkBuffer source = new OkBuffer();
      source.writeUtf8(s);
      buffer.write(source, source.size());
      expected.append(s);
    }
    List<Integer> segmentSizes = buffer.segmentSizes();
    assertEquals(expected.toString(), buffer.readUtf8(expected.length()));
    return segmentSizes;
  }

  @Test public void indexOf() throws Exception{OkBuffer buffer=new OkBuffer();assertEquals(-1,buffer.indexOf((byte)'a'));buffer.writeUtf8("a");assertEquals(0,buffer.indexOf((byte)'a'));assertEquals(-1,buffer.indexOf((byte)'b'));buffer.writeUtf8(repeat('b',Segment.SIZE - 2));assertEquals(0,buffer.indexOf((byte)'a'));assertEquals(1,buffer.indexOf((byte)'b'));assertEquals(-1,buffer.indexOf((byte)'c'));buffer.readUtf8(2);assertEquals(-1,buffer.indexOf((byte)'a'));assertEquals(0,buffer.indexOf((byte)'b'));assertEquals(-1,buffer.indexOf((byte)'c'));buffer.writeUtf8("c");assertEquals(-1,buffer.indexOf((byte)'a'));assertEquals(0,buffer.indexOf((byte)'b'));assertEquals(Segment.SIZE - 3,buffer.indexOf((byte)'c'));buffer.readUtf8(2);assertEquals(-1,buffer.indexOf((byte)'a'));assertEquals(0,buffer.indexOf((byte)'b'));assertEquals(Segment.SIZE - 5,buffer.indexOf((byte)'c'));buffer.writeUtf8("d");assertEquals(asList(Segment.SIZE - 4,1),buffer.segmentSizes());assertEquals(Segment.SIZE - 4,buffer.indexOf((byte)'d'));assertEquals(-1,buffer.indexOf((byte)'e'));}

  /**
   * Returns a new buffer containing the data in {@code data}, and a segment
   * layout determined by {@code dice}.
   */
  private OkBuffer bufferWithRandomSegmentLayout(Random dice, byte[] data) {
    OkBuffer result = new OkBuffer();

    // Writing to result directly will yield packed segments. Instead, write to
    // other buffers, then write those buffers to result.
    for (int pos = 0, byteCount; pos < data.length; pos += byteCount) {
      byteCount = (Segment.SIZE / 2) + dice.nextInt(Segment.SIZE / 2);
      if (byteCount > data.length - pos) byteCount = data.length - pos;
      int offset = dice.nextInt(Segment.SIZE - byteCount);

      OkBuffer segment = new OkBuffer();
      segment.write(new byte[offset]);
      segment.write(data, pos, byteCount);
      segment.skip(offset);

      result.write(segment, byteCount);
    }

    return result;
  }

  private String repeat(char c, int count) {
    char[] array = new char[count];
    Arrays.fill(array, c);
    return new String(array);
  }
}
