/*
 * Copyright (C) 2012 Square, Inc.
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
package com.squareup.okhttp.internal.http;

import java.io.IOException;
import java.net.ProtocolException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public final class StatusLineTest {
  @Test public void nonThreeDigitCode() throws IOException{assertInvalid("HTTP/1.1  OK");assertInvalid("HTTP/1.1 2 OK");assertInvalid("HTTP/1.1 20 OK");assertInvalid("HTTP/1.1 2000 OK");assertInvalid("HTTP/1.1 two OK");assertInvalid("HTTP/1.1 2");assertInvalid("HTTP/1.1 2000");assertInvalid("HTTP/1.1 two");}

  private void assertInvalid(String statusLine) throws IOException {
    try {
      new StatusLine(statusLine);
      fail();
    } catch (ProtocolException expected) {
    }
  }
}
