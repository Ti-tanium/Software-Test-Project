/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.remoting;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.apache.dubbo.remoting.exchange.Exchangers;
import org.apache.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * ChanelHandlerTest
 * <p>
 * mvn clean test -Dtest=*PerformanceClientTest -Dserver=10.20.153.187:9911
 */
public class ChanelHandlerTest extends TestCase {

    private static final Logger logger = LoggerFactory.getLogger(ChanelHandlerTest.class);

    @Test
    public void testClient() throws Throwable {
        // read server info from property
        if (PerformanceUtils.getProperty("server", null) == null) {
            logger.warn("Please set -Dserver=127.0.0.1:9911");
            return;
        }
        final String server = System.getProperty("server", "127.0.0.1:9911");
        final String transporter = PerformanceUtils.getProperty(Constants.TRANSPORTER_KEY, Constants.DEFAULT_TRANSPORTER);
        final String serialization = PerformanceUtils.getProperty(Constants.SERIALIZATION_KEY, Constants.DEFAULT_REMOTING_SERIALIZATION);
        final int timeout = PerformanceUtils.getIntProperty(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
        int sleep = PerformanceUtils.getIntProperty("sleep", 60 * 1000 * 60);

        final String url = "exchange://" + server + "?transporter=" + transporter + "&serialization=" + serialization + "&timeout=" + timeout;
        ExchangeClient exchangeClient = initClient(url);
        Thread.sleep(sleep);
        closeClient(exchangeClient);
    }

    static class PeformanceTestHandler extends ExchangeHandlerAdapter {
        String url = "";

        /**
         * @param url
         */
        public PeformanceTestHandler(String url) {
            this.url = url;
        }

        @Override
        public void connected(Channel channel) throws RemotingException {
            System.out.println("connected event,chanel;" + channel);
        }

        @Override
        public void disconnected(Channel channel) throws RemotingException {
            System.out.println("disconnected event,chanel;" + channel);
            initClient(url);
        }

        /* (non-Javadoc)
         * @see org.apache.dubbo.remoting.transport.support.ChannelHandlerAdapter#caught(org.apache.dubbo.remoting.Channel, java.lang.Throwable)
         */
        @Override
        public void caught(Channel channel, Throwable exception) throws RemotingException {
//            System.out.println("caught event:"+exception);
        }


    }
}
