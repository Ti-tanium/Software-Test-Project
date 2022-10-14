/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.test.integration.web.formauth;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.test.integration.management.ManagementOperations;
import org.jboss.as.test.shared.util.AssumeTestGroupUtil;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests of form authentication
 *
 * @author Scott.Stark@jboss.org
 * @author lbarreiro@redhat.com
 */
@RunWith(Arquillian.class)
@RunAsClient
public class FormAuthUnitTestCase {

    private static Logger log = Logger.getLogger(FormAuthUnitTestCase.class);

    @ArquillianResource
    private URL baseURLNoAuth;

    @ArquillianResource
    private ManagementClient managementClient;

    private static final String USERNAME = "user2";
    private static final String PASSWORD = "password2";

    DefaultHttpClient httpclient = new DefaultHttpClient();

    /**
	 * Test that a bad login is redirected to the errors.jsp and that the session j_exception is not null.
	 */@Test @OperateOnDeployment("form-auth.war") public void testFormAuthException() throws Exception{log.trace("+++ testFormAuthException");URL url=new URL(baseURLNoAuth + "restricted/SecuredServlet");HttpGet httpget=new HttpGet(url.toURI());log.trace("Executing request " + httpget.getRequestLine());HttpResponse response=httpclient.execute(httpget);int statusCode=response.getStatusLine().getStatusCode();Header[] errorHeaders=response.getHeaders("X-NoJException");assertTrue("Wrong response code: " + statusCode,statusCode == HttpURLConnection.HTTP_OK);assertTrue("X-NoJException(" + Arrays.toString(errorHeaders) + ") is null",errorHeaders.length == 0);HttpEntity entity=response.getEntity();if ((entity != null) && (entity.getContentLength() > 0)){String body=EntityUtils.toString(entity);assertTrue("Redirected to login page",body.indexOf("j_security_check") > 0);} else {fail("Empty body in response");}String sessionID=null;for (Cookie k:httpclient.getCookieStore().getCookies()){if (k.getName().equalsIgnoreCase("JSESSIONID"))sessionID=k.getValue();}log.trace("Saw JSESSIONID=" + sessionID);HttpPost formPost=new HttpPost(baseURLNoAuth + "j_security_check");formPost.addHeader("Referer",baseURLNoAuth + "restricted/login.html");List<NameValuePair> formparams=new ArrayList<NameValuePair>();formparams.add(new BasicNameValuePair("j_username","baduser"));formparams.add(new BasicNameValuePair("j_password","badpass"));formPost.setEntity(new UrlEncodedFormEntity(formparams,"UTF-8"));log.trace("Executing request " + formPost.getRequestLine());HttpResponse postResponse=httpclient.execute(formPost);statusCode=postResponse.getStatusLine().getStatusCode();errorHeaders=postResponse.getHeaders("X-NoJException");assertTrue("Should see HTTP_OK. Got " + statusCode,statusCode == HttpURLConnection.HTTP_OK);assertTrue("X-NoJException(" + Arrays.toString(errorHeaders) + ") is not null",errorHeaders.length != 0);log.debug("Saw X-JException, " + Arrays.toString(errorHeaders));}

    private Set<String> createSetOfPrincipals(final ModelNode list) {
        Set<String> set = new HashSet<>();
        for (ModelNode node : list.asList()) {
            set.add(node.asString());
        }
        return set;
    }
}
