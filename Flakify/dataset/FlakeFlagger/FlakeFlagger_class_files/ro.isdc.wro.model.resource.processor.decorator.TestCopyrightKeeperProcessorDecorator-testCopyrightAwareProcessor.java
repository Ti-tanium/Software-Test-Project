/**
 * Copyright wro4j@2011
 */
package ro.isdc.wro.model.resource.processor.decorator;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.processor.ResourcePreProcessor;
import ro.isdc.wro.model.resource.processor.impl.css.CssMinProcessor;
import ro.isdc.wro.model.resource.processor.impl.css.CssUrlRewritingProcessor;
import ro.isdc.wro.model.resource.processor.impl.js.JSMinProcessor;
import ro.isdc.wro.util.WroTestUtils;

/**
 * @author Alex Objelean
 */
public class TestCopyrightKeeperProcessorDecorator {
  @Test public void testCopyrightAwareProcessor() throws Exception{final ResourcePreProcessor decoratedProcessor=new ResourcePreProcessor(){public void process(final Resource resource,final Reader reader,final Writer writer) throws IOException{IOUtils.copy(reader,writer);}};final ResourcePreProcessor processor=CopyrightKeeperProcessorDecorator.decorate(decoratedProcessor);final URL url=ResourcePreProcessor.class.getResource("copyright");final File testFolder=new File(url.getFile(),"test");final File expectedFolder=new File(url.getFile(),"expectedCopyrightAware");WroTestUtils.compareFromDifferentFoldersByExtension(testFolder,expectedFolder,"css",processor);}
}
