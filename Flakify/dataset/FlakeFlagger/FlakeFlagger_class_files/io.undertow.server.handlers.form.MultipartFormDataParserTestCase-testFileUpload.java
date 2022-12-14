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

package io.undertow.server.handlers.form;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.testutils.DefaultServer;
import io.undertow.util.FileUtils;
import io.undertow.testutils.HttpClientUtils;
import io.undertow.testutils.TestHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xnio.IoUtils;

/**
 * @author Stuart Douglas
 */
@RunWith(DefaultServer.class)
public class MultipartFormDataParserTestCase {

    @BeforeClass
    public static void setup() {
        HttpHandler fd = new HttpHandler() {
            @Override
            public void handleRequest(final HttpServerExchange exchange) throws Exception {
                final FormDataParser parser = FormParserFactory.builder().build().createParser(exchange);
                try {
                    FormData data = parser.parseBlocking();
                    exchange.setResponseCode(500);
                    if (data.getFirst("formValue").getValue().equals("myValue")) {
                        FormData.FormValue file = data.getFirst("file");
                        if (file.isFile()) {
                            if (file.getFile() != null) {
                                if (FileUtils.readFile(file.getFile()).startsWith("file contents")) {
                                    exchange.setResponseCode(200);
                                }
                            }
                        }
                    }
                    exchange.endExchange();
                } catch (IOException e) {
                    e.printStackTrace();
                    exchange.setResponseCode(500);
                    exchange.endExchange();
                } finally {
                    IoUtils.safeClose(parser);
                }
            }
        };
        DefaultServer.setRootHandler(new BlockingHandler(fd));
    }

    @Test public void testFileUpload() throws Exception{TestHttpClient client=new TestHttpClient();try {HttpPost post=new HttpPost(DefaultServer.getDefaultServerURL() + "/path");MultipartEntity entity=new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);entity.addPart("formValue",new StringBody("myValue","text/plain",Charset.forName("UTF-8")));entity.addPart("file",new FileBody(new File(MultipartFormDataParserTestCase.class.getResource("uploadfile.txt").getFile())));post.setEntity(entity);HttpResponse result=client.execute(post);Assert.assertEquals(200,result.getStatusLine().getStatusCode());HttpClientUtils.readResponse(result);}  finally {client.getConnectionManager().shutdown();}}


}
