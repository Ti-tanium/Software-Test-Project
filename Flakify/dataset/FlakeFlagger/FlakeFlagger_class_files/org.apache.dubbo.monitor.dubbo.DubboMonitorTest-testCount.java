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
package org.apache.dubbo.monitor.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.monitor.Monitor;
import org.apache.dubbo.monitor.MonitorFactory;
import org.apache.dubbo.monitor.MonitorService;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.hamcrest.CustomMatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * DubboMonitorTest
 */
public class DubboMonitorTest {

    private final Invoker<MonitorService> monitorInvoker = new Invoker<MonitorService>() {
        @Override
        public Class<MonitorService> getInterface() {
            return MonitorService.class;
        }

        public URL getUrl() {
            return URL.valueOf("dubbo://127.0.0.1:7070?interval=20");
        }

        @Override
        public boolean isAvailable() {
            return false;
        }

        public Result invoke(Invocation invocation) throws RpcException {
            return null;
        }

        @Override
        public void destroy() {
        }
    };
    private volatile URL lastStatistics;
    private final MonitorService monitorService = new MonitorService() {

        public void collect(URL statistics) {
            DubboMonitorTest.this.lastStatistics = statistics;
        }

        public List<URL> lookup(URL query) {
            return Arrays.asList(DubboMonitorTest.this.lastStatistics);
        }

    };

    @Test public void testCount() throws Exception{DubboMonitor monitor=new DubboMonitor(monitorInvoker,monitorService);URL statistics=new URL("dubbo","10.20.153.10",0).addParameter(MonitorService.APPLICATION,"morgan").addParameter(MonitorService.INTERFACE,"MemberService").addParameter(MonitorService.METHOD,"findPerson").addParameter(MonitorService.CONSUMER,"10.20.153.11").addParameter(MonitorService.SUCCESS,1).addParameter(MonitorService.FAILURE,0).addParameter(MonitorService.ELAPSED,3).addParameter(MonitorService.MAX_ELAPSED,3).addParameter(MonitorService.CONCURRENT,1).addParameter(MonitorService.MAX_CONCURRENT,1);monitor.collect(statistics);while (lastStatistics == null){Thread.sleep(10);}Assert.assertEquals(lastStatistics.getParameter(MonitorService.APPLICATION),"morgan");Assert.assertEquals(lastStatistics.getProtocol(),"dubbo");Assert.assertEquals(lastStatistics.getHost(),"10.20.153.10");Assert.assertEquals(lastStatistics.getParameter(MonitorService.APPLICATION),"morgan");Assert.assertEquals(lastStatistics.getParameter(MonitorService.INTERFACE),"MemberService");Assert.assertEquals(lastStatistics.getParameter(MonitorService.METHOD),"findPerson");Assert.assertEquals(lastStatistics.getParameter(MonitorService.CONSUMER),"10.20.153.11");Assert.assertEquals(lastStatistics.getParameter(MonitorService.SUCCESS),"1");Assert.assertEquals(lastStatistics.getParameter(MonitorService.FAILURE),"0");Assert.assertEquals(lastStatistics.getParameter(MonitorService.ELAPSED),"3");Assert.assertEquals(lastStatistics.getParameter(MonitorService.MAX_ELAPSED),"3");Assert.assertEquals(lastStatistics.getParameter(MonitorService.CONCURRENT),"1");Assert.assertEquals(lastStatistics.getParameter(MonitorService.MAX_CONCURRENT),"1");monitor.destroy();}
}
