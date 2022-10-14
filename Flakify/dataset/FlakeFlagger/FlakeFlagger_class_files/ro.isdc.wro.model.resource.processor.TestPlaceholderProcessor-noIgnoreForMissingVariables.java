/**
 * Copyright wro4j@2011
 */
package ro.isdc.wro.model.resource.processor;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.processor.impl.PlaceholderProcessor;
import ro.isdc.wro.util.WroTestUtils;
import ro.isdc.wro.util.WroUtil;

/**
 * @author Alex Objelean
 */
public class TestPlaceholderProcessor {
  @Test(expected=WroRuntimeException.class) public void noIgnoreForMissingVariables() throws Exception{final ResourcePreProcessor processor=new PlaceholderProcessor().setIgnoreMissingVariables(false);final URL url=getClass().getResource("placeholder");final File testFolder=new File(url.getFile(),"test");final File expectedFolder=new File(url.getFile(),"expected");WroTestUtils.compareFromDifferentFoldersByExtension(testFolder,expectedFolder,"css",processor);}
}
