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
package org.assertj.core.util.xml;

import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.assertj.core.util.xml.XmlStringPrettyFormatter.xmlPrettyFormat;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXParseException;

/**
 * Tests for <code>{@link XmlStringPrettyFormatter#xmlPrettyFormat(String)}</code>.
 * 
 * @author Joel Costigliola
 */
public class XmlStringPrettyFormatter_prettyFormat_Test {
	
  @Before
  public void before() {
	// Set locale to be able to check exception message in English.
	Locale.setDefault(ENGLISH);
  }

  private static final String EXPECTED_FORMATTED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<rss version=\"2.0\">\n    <channel>\n"
      + "        <title>Java Tutorials and Examples 1</title>\n"
      + "        <language>en-us</language>\n"
      + "    </channel>\n</rss>\n";

  @Test public void should_format_xml_string_with_space_and_newline_prettily(){String xmlString="<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\"><channel>  <title>Java Tutorials and Examples 1</title>  \n\n<language>en-us</language>  </channel></rss>";assertThat(xmlPrettyFormat(xmlString)).isEqualTo(EXPECTED_FORMATTED_XML);}

}
