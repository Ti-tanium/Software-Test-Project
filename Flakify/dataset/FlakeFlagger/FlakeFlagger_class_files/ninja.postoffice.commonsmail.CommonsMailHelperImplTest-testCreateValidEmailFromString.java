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

package ninja.postoffice.commonsmail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

import ninja.postoffice.Mail;
import ninja.postoffice.common.MailImpl;
import ninja.postoffice.common.MailImplTestHelper;
import ninja.postoffice.common.Tuple;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.junit.Before;
import org.junit.Test;

public class CommonsMailHelperImplTest {

    CommonsmailHelper commonsmailHelper;

    @Before
    public void setUp() {

        commonsmailHelper = new CommonsmailHelperImpl();
    }

    @Test public void testCreateValidEmailFromString() throws Exception{String user="The user <the.user@me.com>";Tuple<String, String> tuple=commonsmailHelper.createValidEmailFromString(user);assertEquals("the.user@me.com",tuple.x);assertEquals("The user",tuple.y);user="email@me.com";tuple=commonsmailHelper.createValidEmailFromString(user);assertEquals("email@me.com",tuple.x);assertEquals(null,tuple.y);}




    /**
     * Convenience method as commonsmail does not use generics...
     * 
     * @param list
     * @return
     */
    private List<InternetAddress> doConvertAdressesToInternetAddressList(List<?> list) {

        List<InternetAddress> returnList = new ArrayList<InternetAddress>();

        for (Object object : list) {
            InternetAddress internetAddress = (InternetAddress) object;
            returnList.add(internetAddress);
        }

        return returnList;

    }

}