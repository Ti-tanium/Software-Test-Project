/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.web;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.Valve;
import org.apache.catalina.valves.RemoteIpValve;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link ServerProperties}.
 *
 * @author Dave Syer
 * @author Stephane Nicoll
 * @author Andy Wilkinson
 */
public class ServerPropertiesTests {

	private final ServerProperties properties = new ServerProperties();

	@Test public void customTomcatRemoteIpValve() throws Exception{Map<String, String> map=new HashMap<String, String>();map.put("server.tomcat.remote_ip_header","x-my-remote-ip-header");map.put("server.tomcat.protocol_header","x-my-protocol-header");map.put("server.tomcat.internal_proxies","192.168.0.1");map.put("server.tomcat.port-header","x-my-forward-port");bindProperties(map);TomcatEmbeddedServletContainerFactory container=new TomcatEmbeddedServletContainerFactory();this.properties.customize(container);assertEquals(1,container.getValves().size());Valve valve=container.getValves().iterator().next();assertThat(valve,instanceOf(RemoteIpValve.class));RemoteIpValve remoteIpValve=(RemoteIpValve)valve;assertEquals("x-my-protocol-header",remoteIpValve.getProtocolHeader());assertEquals("x-my-remote-ip-header",remoteIpValve.getRemoteIpHeader());assertEquals("x-my-forward-port",remoteIpValve.getPortHeader());assertEquals("192.168.0.1",remoteIpValve.getInternalProxies());}

	private void bindProperties(Map<String, String> map) {
		new RelaxedDataBinder(this.properties, "server").bind(new MutablePropertyValues(
				map));
	}

}