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

package ninja.servlet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ninja.*;
import ninja.bodyparser.BodyParserEngine;
import ninja.bodyparser.BodyParserEngineManager;
import ninja.session.FlashCookie;
import ninja.session.SessionCookie;

import ninja.utils.NinjaConstant;
import ninja.utils.ResultHandler;
import ninja.validation.Validation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;

@RunWith(MockitoJUnitRunner.class)
public class ContextImplTest {

    @Mock
    private SessionCookie sessionCookie;

    @Mock
    private FlashCookie flashCookie;

    @Mock
    private BodyParserEngineManager bodyParserEngineManager;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private Route route;

    @Mock
    private ResultHandler resultHandler;

    @Mock
    private Validation validation;

    @Mock
    private BodyParserEngine bodyParserEngine;

    private ContextImpl context;

    @Before
    public void setUp() {
        //default setup for httpServlet request.
        //According to servlet spec the following will be returned:
        when(httpServletRequest.getContextPath()).thenReturn("");
        when(httpServletRequest.getRequestURI()).thenReturn("/");


        context = new ContextImpl(bodyParserEngineManager, flashCookie, sessionCookie,
                resultHandler, validation);
    }

    @Test public void testAddCookieViaResult(){Cookie cookie=Cookie.builder("cookie","yum").setDomain("domain").build();context.init(httpServletRequest,httpServletResponse);Result result=Results.html();result.addCookie(cookie);context.finalizeHeaders(result);ArgumentCaptor<javax.servlet.http.Cookie> cookieCaptor=ArgumentCaptor.forClass(javax.servlet.http.Cookie.class);verify(httpServletResponse).addCookie(cookieCaptor.capture());javax.servlet.http.Cookie resultCookie=cookieCaptor.getValue();assertThat(resultCookie.getName(),equalTo("cookie"));assertThat(resultCookie.getValue(),equalTo("yum"));assertThat(resultCookie.getPath(),equalTo("/"));assertThat(resultCookie.getSecure(),equalTo(false));assertThat(resultCookie.getMaxAge(),equalTo(-1));}

    // Dummy class used for parseBody tests.
    class Dummy {
       public String name;
       public Long count;
    }

}
