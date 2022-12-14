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

package com.google.zxing.datamatrix.decoder;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author bbrown@google.com (Brian Brown)
 */
public final class DecodedBitStreamParserTestCase extends Assert {

  @Test public void testAsciiDoubleDigitDecode() throws Exception{byte[] bytes={(byte)130,(byte)(1 + 130),(byte)(98 + 130),(byte)(99 + 130)};String decodedString=DecodedBitStreamParser.decode(bytes).getText();assertEquals("00019899",decodedString);}
  
  // TODO(bbrown): Add test cases for each encoding type
  // TODO(bbrown): Add test cases for switching encoding types
}