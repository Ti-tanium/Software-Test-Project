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

package ninja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ninja.utils.NoHttpBody;

import org.junit.Test;

public class ResultsTest {

    @Test public void testResultsRedirectTemporary(){Result result=Results.redirectTemporary("http://example.com");assertEquals(Result.SC_307_TEMPORARY_REDIRECT,result.getStatusCode());assertEquals("http://example.com",result.getHeaders().get(Result.LOCATION));assertTrue(result.getRenderable() instanceof NoHttpBody);}

    /**
     * Simple helper to test if objects get copied to result.
     * 
     */
    public class TestObject {
    }

}
