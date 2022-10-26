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
  @Test public void multipleSegmentBuffers() throws Exception{OkBuffer buffer=new OkBuffer();buffer.writeUtf8(repeat('a',1000));buffer.writeUtf8(repeat('b',2500));buffer.writeUtf8(repeat('c',5000));buffer.writeUtf8(repeat('d',10000));buffer.writeUtf8(repeat('e',25000));buffer.writeUtf8(repeat('f',50000));assertEquals(repeat('a',999),buffer.readUtf8(999));assertEquals("a" + repeat('b',2500) + "c",buffer.readUtf8(2502));assertEquals(repeat('c',4998),buffer.readUtf8(4998));assertEquals("c" + repeat('d',10000) + "e",buffer.readUtf8(10002));assertEquals(repeat('e',24998),buffer.readUtf8(24998));assertEquals("e" + repeat('f',50000),buffer.readUtf8(50001));assertEquals(0,buffer.size());}

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