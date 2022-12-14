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
package org.assertj.core.util;

import org.assertj.core.test.ExpectedException;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.test.ExpectedException.none;
import static org.assertj.core.util.Files.linesOf;
import static org.assertj.core.util.Lists.newArrayList;

/**
 * Tests for {@link Files#linesOf(File, Charset)} and {@link Files#linesOf(File, String)}.
 * 
 * @author Mateusz Haligowski
 */
public class Files_linesOf_Test {

  private static final File SAMPLE_UNIX_FILE = new File("src/test/resources/utf8.txt");
  private static final File SAMPLE_WIN_FILE = new File("src/test/resources/utf8_win.txt");
  private static final File SAMPLE_MAC_FILE = new File("src/test/resources/utf8_mac.txt");

  private static final List<String> EXPECTED_CONTENT = newArrayList("A text file encoded in UTF-8, with diacritics:", "é à");
  public static final String UTF_8 = "UTF-8";

  @Rule
  public ExpectedException thrown = none();

  @Test
  public void should_throw_exception_if_charset_name_does_not_exist() {
    thrown.expect(IllegalArgumentException.class);
    linesOf(new File("test"), "Klingon");
  }
}
