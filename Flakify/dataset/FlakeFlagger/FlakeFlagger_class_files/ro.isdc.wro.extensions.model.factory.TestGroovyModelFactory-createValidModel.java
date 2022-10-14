/*
 * Copyright 2011 Wro4j Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package ro.isdc.wro.extensions.model.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.config.Context;
import ro.isdc.wro.config.support.ContextPropagatingCallable;
import ro.isdc.wro.model.WroModel;
import ro.isdc.wro.model.WroModelInspector;
import ro.isdc.wro.model.factory.DefaultWroModelFactoryDecorator;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.group.Group;
import ro.isdc.wro.model.group.RecursiveGroupDefinitionException;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.transformer.WildcardExpanderModelTransformer;
import ro.isdc.wro.util.Transformer;
import ro.isdc.wro.util.WroTestUtils;


/**
 * Test {@link GroovyModelFactory}
 *
 * @author Romain Philibert
 * @created 19 Jul 2011
 */
public class TestGroovyModelFactory {
  private static final Logger LOG = LoggerFactory.getLogger(TestGroovyModelFactory.class);
  private WroModelFactory factory;

  @Before
  public void setUp() {
    Context.set(Context.standaloneContext());
  }

  @Test public void createValidModel(){factory=new GroovyModelFactory(){@Override protected InputStream getModelResourceAsStream() throws IOException{return TestGroovyModelFactory.class.getResourceAsStream("wro.groovy");}};final WroModel model=factory.create();Assert.assertNotNull(model);Assert.assertEquals(Arrays.asList("g1","g2"),new WroModelInspector(model).getGroupNames());Assert.assertEquals(2,model.getGroupByName("g1").getResources().size());Assert.assertTrue(model.getGroupByName("g1").getResources().get(0).isMinimize());Assert.assertEquals("/static/app.js",model.getGroupByName("g1").getResources().get(0).getUri());Assert.assertEquals(ResourceType.JS,model.getGroupByName("g1").getResources().get(0).getType());Assert.assertTrue(model.getGroupByName("g1").getResources().get(1).isMinimize());Assert.assertEquals("/static/app.css",model.getGroupByName("g1").getResources().get(1).getUri());Assert.assertEquals(ResourceType.CSS,model.getGroupByName("g1").getResources().get(1).getType());Assert.assertEquals(2,model.getGroupByName("g2").getResources().size());Assert.assertFalse(model.getGroupByName("g2").getResources().get(1).isMinimize());LOG.debug("model: ",model);}
}
