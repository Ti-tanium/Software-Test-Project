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
package org.apache.dubbo.config.spring.context.annotation;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ModuleConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

/**
 * {@link DubboConfigConfiguration} Test
 *
 * @since 2.5.8
 */
public class DubboConfigConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @Before
    public void before() throws IOException {

        context = new AnnotationConfigApplicationContext();
        ResourcePropertySource propertySource = new ResourcePropertySource("META-INF/config.properties");
        context.getEnvironment().getPropertySources().addFirst(propertySource);

    }

    @Test public void testSingle() throws IOException{context.register(DubboConfigConfiguration.Single.class);context.refresh();ApplicationConfig applicationConfig=context.getBean("applicationBean",ApplicationConfig.class);Assert.assertEquals("dubbo-demo-application",applicationConfig.getName());ModuleConfig moduleConfig=context.getBean("moduleBean",ModuleConfig.class);Assert.assertEquals("dubbo-demo-module",moduleConfig.getName());RegistryConfig registryConfig=context.getBean(RegistryConfig.class);Assert.assertEquals("zookeeper://192.168.99.100:32770",registryConfig.getAddress());ProtocolConfig protocolConfig=context.getBean(ProtocolConfig.class);Assert.assertEquals("dubbo",protocolConfig.getName());Assert.assertEquals(Integer.valueOf(20880),protocolConfig.getPort());}

}
