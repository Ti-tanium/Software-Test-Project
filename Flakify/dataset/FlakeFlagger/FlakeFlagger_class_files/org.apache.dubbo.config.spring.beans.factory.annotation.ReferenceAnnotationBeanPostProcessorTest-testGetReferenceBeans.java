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
package org.apache.dubbo.config.spring.beans.factory.annotation;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.spring.api.DemoService;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.util.Collection;
import java.util.Map;

import static org.apache.dubbo.config.spring.beans.factory.annotation.ReferenceAnnotationBeanPostProcessor.BEAN_NAME;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * {@link ReferenceAnnotationBeanPostProcessor} Test
 *
 * @since 2.5.7
 */
public class ReferenceAnnotationBeanPostProcessorTest {

    private ConfigurableApplicationContext providerApplicationContext;

    @BeforeClass
    public static void prepare() {
        System.setProperty("provider.version", "1.2");
        System.setProperty("package1", "org.apache.dubbo.config.spring.annotation.provider");
        System.setProperty("packagesToScan", "${package1}");
        System.setProperty("consumer.version", "1.2");
        System.setProperty("consumer.url", "dubbo://127.0.0.1:12345");
    }

    @Before
    public void init() {
        // Starts Provider
        providerApplicationContext = new AnnotationConfigApplicationContext(ServiceAnnotationBeanPostProcessorTest.TestConfiguration.class);
    }

    /**
	 * Test on   {@link ReferenceAnnotationBeanPostProcessor#getReferenceBeans()}
	 */@Test public void testGetReferenceBeans(){AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(TestBean.class);ReferenceAnnotationBeanPostProcessor beanPostProcessor=context.getBean(BEAN_NAME,ReferenceAnnotationBeanPostProcessor.class);Collection<ReferenceBean<?>> referenceBeans=beanPostProcessor.getReferenceBeans();Assert.assertEquals(1,referenceBeans.size());ReferenceBean<?> referenceBean=referenceBeans.iterator().next();TestBean testBean=context.getBean(TestBean.class);Assert.assertEquals(referenceBean.get(),testBean.getDemoServiceFromAncestor());Assert.assertEquals(referenceBean.get(),testBean.getDemoServiceFromParent());Assert.assertEquals(referenceBean.get(),testBean.getDemoService());}

    private static class AncestorBean {


        private DemoService demoServiceFromAncestor;

        @Autowired
        private ApplicationContext applicationContext;

        public DemoService getDemoServiceFromAncestor() {
            return demoServiceFromAncestor;
        }

        @Reference(version = "1.2", url = "dubbo://127.0.0.1:12345")
        public void setDemoServiceFromAncestor(DemoService demoServiceFromAncestor) {
            this.demoServiceFromAncestor = demoServiceFromAncestor;
        }

        public ApplicationContext getApplicationContext() {
            return applicationContext;
        }

    }


    private static class ParentBean extends AncestorBean {

        @Reference(version = "${consumer.version}", url = "${consumer.url}")
        private DemoService demoServiceFromParent;

        public DemoService getDemoServiceFromParent() {
            return demoServiceFromParent;
        }


    }

    @ImportResource("META-INF/spring/dubbo-annotation-consumer.xml")
    @DubboComponentScan(basePackageClasses = ReferenceAnnotationBeanPostProcessorTest.class)
    static class TestBean extends ParentBean {

        private DemoService demoService;

        @Autowired
        private ApplicationContext applicationContext;

        public DemoService getDemoService() {
            return demoService;
        }

        @Reference(version = "1.2", url = "dubbo://127.0.0.1:12345")
        public void setDemoService(DemoService demoService) {
            this.demoService = demoService;
        }
    }

}