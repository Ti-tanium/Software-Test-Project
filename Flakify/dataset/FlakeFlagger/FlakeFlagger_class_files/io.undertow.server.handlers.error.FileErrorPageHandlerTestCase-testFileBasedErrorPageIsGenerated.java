/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012 Red Hat, Inc., and individual contributors
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

package io.undertow.server.handlers.error;

import java.io.File;
import java.io.IOException;

import io.undertow.testutils.DefaultServer;
import io.undertow.testutils.HttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import io.undertow.testutils.TestHttpClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stuart Douglas
 */
@RunWith(DefaultServer.class)
public class FileErrorPageHandlerTestCase {


    @Test public void testFileBasedErrorPageIsGenerated() throws IOException{TestHttpClient client=new TestHttpClient();try {final FileErrorPageHandler handler=new FileErrorPageHandler(new File(getClass().getResource("errorpage.html").getFile()),404);DefaultServer.setRootHandler(handler);HttpGet get=new HttpGet(DefaultServer.getDefaultServerURL() + "/path");HttpResponse result=client.execute(get);Assert.assertEquals(404,result.getStatusLine().getStatusCode());final String response=HttpClientUtils.readResponse(result);Assert.assertTrue(response,response.contains("Custom Error Page"));}  finally {client.getConnectionManager().shutdown();}}

}
