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

package ninja.i18n;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import ninja.Context;
import ninja.Cookie;
import ninja.Result;
import ninja.Results;
import ninja.utils.NinjaConstant;
import ninja.utils.NinjaProperties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;

@RunWith(MockitoJUnitRunner.class)
public class LangImplTest {

    @Mock
    private NinjaProperties ninjaProperties;
    
    @Mock
    private Context context;
    
    @Captor
    ArgumentCaptor<Cookie> captor = ArgumentCaptor.forClass(Cookie.class);
    
   

    @Test public void testClearLanguage(){Cookie cookie=Cookie.builder("NINJA_TEST" + NinjaConstant.LANG_COOKIE_SUFFIX,"de").build();when(ninjaProperties.getOrDie(NinjaConstant.applicationCookiePrefix)).thenReturn("NINJA_TEST");Lang lang=new LangImpl(ninjaProperties);Result result=Results.ok();lang.clearLanguage(result);Cookie returnCookie=result.getCookie(cookie.getName());assertEquals("",returnCookie.getValue());assertEquals(0,returnCookie.getMaxAge());}
    
}