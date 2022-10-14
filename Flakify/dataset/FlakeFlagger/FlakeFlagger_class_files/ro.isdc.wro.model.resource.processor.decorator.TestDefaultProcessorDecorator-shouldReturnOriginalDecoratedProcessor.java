package ro.isdc.wro.model.resource.processor.decorator;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ro.isdc.wro.config.Context;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.processor.ResourcePreProcessor;
import ro.isdc.wro.util.WroTestUtils;


/**
 * @author Alex Objelean
 */
public class TestDefaultProcessorDecorator {
  @Mock
  private MockProcessor mockProcessor;
  private final Resource testResource = Resource.create("/resource.js");
  @Mock
  private Reader mockReader;
  @Mock
  private Writer mockWriter;

  private DefaultProcessorDecorator victim;

  private static interface MockProcessor
      extends ResourcePreProcessor {
  }

  @Before
  public void setUp() {
    Context.set(Context.standaloneContext());
    MockitoAnnotations.initMocks(this);
    victim = new DefaultProcessorDecorator(mockProcessor, true);
    WroTestUtils.createInjector().inject(victim);
  }

  @Test public void shouldReturnOriginalDecoratedProcessor(){Assert.assertSame(mockProcessor,victim.getOriginalDecoratedObject());}
}
