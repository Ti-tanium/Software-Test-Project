/*
 * Copyright (c) 2010. All rights reserved.
 */
package ro.isdc.wro.extensions.processor;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.concurrent.Callable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ro.isdc.wro.config.Context;
import ro.isdc.wro.extensions.processor.css.Less4jProcessor;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.processor.ResourcePreProcessor;
import ro.isdc.wro.model.resource.processor.impl.css.LessCssImportPreProcessor;
import ro.isdc.wro.model.resource.processor.support.ChainedProcessor;
import ro.isdc.wro.util.Function;
import ro.isdc.wro.util.WroTestUtils;


/**
 * Test less css processor based on lessc shell which uses node.
 *
 * @author Alex Objelean
 */
public class TestLess4jProcessor {
  @Before
  public void setUp() {
    Context.set(Context.standaloneContext());
  }

  @Test
  public void shouldBeThreadSafe() throws Exception {
    final ResourcePreProcessor processor = new Less4jProcessor();
    final Callable<Void> task = new Callable<Void>() {
      @Override
      public Void call() {
        try {
          processor.process(null, new StringReader("#id {.class {color: red;}}"), new StringWriter());
        } catch (final Exception e) {
          throw new RuntimeException(e);
        }
        return null;
      }
    };
    WroTestUtils.runConcurrently(task);
  }
}
