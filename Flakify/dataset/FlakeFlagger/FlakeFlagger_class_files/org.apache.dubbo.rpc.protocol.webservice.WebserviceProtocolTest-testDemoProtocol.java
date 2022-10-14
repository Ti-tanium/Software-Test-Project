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
package org.apache.dubbo.rpc.protocol.webservice;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.service.EchoService;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author kimmking
 */

public class WebserviceProtocolTest {
    private Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
    private ProxyFactory proxy = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    @Test
    public void testDemoProtocol() throws Exception {
        DemoService service = new DemoServiceImpl();
        protocol.export(proxy.getInvoker(service, DemoService.class, URL.valueOf("webservice://127.0.0.1:9019/" + DemoService.class.getName() + "?codec=exchange")));
        service = proxy.getProxy(protocol.refer(DemoService.class, URL.valueOf("webservice://127.0.0.1:9019/" + DemoService.class.getName() + "?codec=exchange&timeout=3000")));
        assertEquals(service.getSize(new String[]{"", "", ""}), 3);
    }


}