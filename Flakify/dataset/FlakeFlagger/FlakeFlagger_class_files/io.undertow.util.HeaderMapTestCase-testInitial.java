/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
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

package io.undertow.util;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
public final class HeaderMapTestCase {

    private static final List<HttpString> HTTP_STRING_LIST = Arrays.asList(Headers.CONNECTION, Headers.HOST, Headers.UPGRADE, Headers.CONTENT_MD5, Headers.KEEP_ALIVE, Headers.RESPONSE_AUTH, Headers.CONTENT_DISPOSITION, Headers.DEFLATE, Headers.NEGOTIATE, Headers.USER_AGENT, Headers.REFERER, Headers.TRANSFER_ENCODING, Headers.FROM);

    @Test public void testInitial(){final HeaderMap headerMap=new HeaderMap();assertEquals(0,headerMap.size());assertEquals(-1L,headerMap.fastIterate());assertFalse(headerMap.iterator().hasNext());}
}
