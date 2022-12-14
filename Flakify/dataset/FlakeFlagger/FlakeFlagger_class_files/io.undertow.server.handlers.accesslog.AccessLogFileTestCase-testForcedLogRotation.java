package io.undertow.server.handlers.accesslog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.testutils.DefaultServer;
import io.undertow.util.CompletionLatchHandler;
import io.undertow.util.FileUtils;
import io.undertow.testutils.HttpClientUtils;
import io.undertow.testutils.TestHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests writing the access log to a file
 *
 * @author Stuart Douglas
 */
@RunWith(DefaultServer.class)
public class AccessLogFileTestCase {

    private static final File logDirectory = new File(System.getProperty("java.io.tmpdir") + "/logs");

    private static final int NUM_THREADS = 10;
    private static final int NUM_REQUESTS = 12;

    @Before
    public void before() {
        logDirectory.mkdirs();
    }

    private static final HttpHandler HELLO_HANDLER = new HttpHandler() {
        @Override
        public void handleRequest(final HttpServerExchange exchange) throws Exception {
            exchange.getResponseSender().send("Hello");
        }
    };

    @Test public void testForcedLogRotation() throws IOException,InterruptedException{File logFileName=new File(logDirectory,"server.log");DefaultAccessLogReceiver logReceiver=new DefaultAccessLogReceiver(DefaultServer.getWorker(),logDirectory,"server");CompletionLatchHandler latchHandler;DefaultServer.setRootHandler(latchHandler=new CompletionLatchHandler(new AccessLogHandler(HELLO_HANDLER,logReceiver,"Remote address %a Code %s test-header %{i,test-header}",AccessLogFileTestCase.class.getClassLoader())));TestHttpClient client=new TestHttpClient();try {HttpGet get=new HttpGet(DefaultServer.getDefaultServerURL() + "/path");get.addHeader("test-header","v1");HttpResponse result=client.execute(get);Assert.assertEquals(200,result.getStatusLine().getStatusCode());Assert.assertEquals("Hello",HttpClientUtils.readResponse(result));latchHandler.await();latchHandler.reset();logReceiver.awaitWrittenForTest();Assert.assertEquals("Remote address 127.0.0.1 Code 200 test-header v1\n",FileUtils.readFile(logFileName));logReceiver.rotate();logReceiver.awaitWrittenForTest();Assert.assertFalse(logFileName.exists());File firstLogRotate=new File(logDirectory,"server_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log");Assert.assertEquals("Remote address 127.0.0.1 Code 200 test-header v1\n",FileUtils.readFile(firstLogRotate));get=new HttpGet(DefaultServer.getDefaultServerURL() + "/path");get.addHeader("test-header","v2");result=client.execute(get);Assert.assertEquals(200,result.getStatusLine().getStatusCode());Assert.assertEquals("Hello",HttpClientUtils.readResponse(result));latchHandler.await();latchHandler.reset();logReceiver.awaitWrittenForTest();Assert.assertEquals("Remote address 127.0.0.1 Code 200 test-header v2\n",FileUtils.readFile(logFileName));logReceiver.rotate();logReceiver.awaitWrittenForTest();Assert.assertFalse(logFileName.exists());File secondLogRotate=new File(logDirectory,"server_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "-1.log");Assert.assertEquals("Remote address 127.0.0.1 Code 200 test-header v2\n",FileUtils.readFile(secondLogRotate));}  finally {client.getConnectionManager().shutdown();}}

}
