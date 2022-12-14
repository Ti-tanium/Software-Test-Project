/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ninja.utils;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

public class DateUtilImplTest {

    @Test public void testFormatForHttpHeaderDate(){assertEquals("Thu, 01 Jan 1970 00:00:00 GMT",DateUtil.formatForHttpHeader(new Date(0L)));assertEquals("Wed, 05 Sep 2012 09:57:57 GMT",DateUtil.formatForHttpHeader(new Date(1346839077523L)));}
    
    
}
