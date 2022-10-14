/*
 * Copyright (C) 2011 . All rights reserved.
 */
package ro.isdc.wro.model.factory;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.isdc.wro.config.Context;
import ro.isdc.wro.manager.factory.BaseWroManagerFactory;
import ro.isdc.wro.model.WroModel;
import ro.isdc.wro.model.group.Group;
import ro.isdc.wro.model.group.processor.Injector;
import ro.isdc.wro.model.group.processor.InjectorBuilder;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.locator.ClasspathUriLocator;
import ro.isdc.wro.model.resource.processor.factory.ProcessorsFactory;
import ro.isdc.wro.model.transformer.WildcardExpanderModelTransformer;
import ro.isdc.wro.util.Function;
import ro.isdc.wro.util.WroUtil;


/**
 * @author Alex Objelean
 */
public class TestWildcardExpanderModelTransformer {
  private static final Logger LOG = LoggerFactory.getLogger(TestWildcardExpanderModelTransformer.class);
  private WildcardExpanderModelTransformer transformer;
  @Mock
  private WroModelFactory decoratedFactory;
  @Mock
  private ProcessorsFactory processorsFactory;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    Context.set(Context.standaloneContext());
    transformer = new WildcardExpanderModelTransformer();
    // create manager to force correct initialization.
    final BaseWroManagerFactory factory = new BaseWroManagerFactory();
    factory.setProcessorsFactory(processorsFactory);
    factory.addModelTransformer(transformer);
    final Injector injector = InjectorBuilder.create(factory).build();
    injector.inject(transformer);
  }

  @Test public void testExpandWildcardRootDir() throws Exception{final String uri="/**.js";final Resource resource=Resource.create(uri,ResourceType.JS);final Group group=new Group("group").addResource(resource);final String baseNameFolder=WroUtil.toPackageAsFolder(getClass());final Function<Collection<File>, Void> expanderHandler=transformer.createExpanderHandler(group,resource,baseNameFolder);final File mockFile1=Mockito.mock(File.class);Mockito.when(mockFile1.getPath()).thenReturn(baseNameFolder + "/js1.js");final File mockFile2=Mockito.mock(File.class);Mockito.when(mockFile2.getPath()).thenReturn(baseNameFolder + "/js2.js");expanderHandler.apply(Arrays.asList(mockFile1,mockFile2));LOG.debug("group: {}",group);Assert.assertEquals(2,group.getResources().size());Assert.assertEquals("/js1.js",group.getResources().get(0).getUri());Assert.assertEquals("/js2.js",group.getResources().get(1).getUri());}
}
