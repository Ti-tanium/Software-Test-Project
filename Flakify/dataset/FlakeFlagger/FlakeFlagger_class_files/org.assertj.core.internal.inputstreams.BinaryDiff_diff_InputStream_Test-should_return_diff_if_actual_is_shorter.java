/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.core.internal.inputstreams;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.assertj.core.internal.BinaryDiff;
import org.assertj.core.internal.BinaryDiffResult;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Tests for <code>{@link BinaryDiff#diff(java.io.InputStream, java.io.InputStream)}</code>.
 * 
 * @author Olivier Michallat
 */
public class BinaryDiff_diff_InputStream_Test {

  private static BinaryDiff binaryDiff;

  @BeforeClass
  public static void setUpOnce() {
    binaryDiff = new BinaryDiff();
  }

  private InputStream actual;
  private InputStream expected;

  @Test
  public void should_return_diff_if_actual_is_shorter() throws IOException {
    actual = stream(0xCA, 0xFE, 0xBA);
    expected = stream(0xCA, 0xFE, 0xBA, 0xBE);
    BinaryDiffResult result = binaryDiff.diff(actual, expected);
    assertEquals(3, result.offset);
    assertEquals("EOF", result.actual);
    assertEquals("0xBE", result.expected);
  }
  
  private InputStream stream(int... contents) {
    byte[] byteContents = new byte[contents.length];
    for (int i = 0; i < contents.length; i++) {
      byteContents[i] = (byte) contents[i];
    }
    return new ByteArrayInputStream(byteContents);
  }
}
