package ro.isdc.wro.model.resource.locator.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.model.resource.locator.ClasspathUriLocator;
import ro.isdc.wro.model.resource.locator.ServletContextUriLocator;
import ro.isdc.wro.model.resource.locator.UriLocator;
import ro.isdc.wro.model.resource.locator.UrlUriLocator;
import ro.isdc.wro.model.resource.locator.support.LocatorProvider;
import ro.isdc.wro.util.provider.ProviderFinder;


/**
 * @author Alex Objelean
 */
public class TestConfigurableLocatorFactory {
  @Mock
  private UriLocator mockUriLocator;
  @Mock
  private ProviderFinder<LocatorProvider> mockProviderFinder;
  private ConfigurableLocatorFactory victim;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    victim = new ConfigurableLocatorFactory();
  }

  private Properties createPropsWithLocators(final String locatorsAsString) {
    final Properties props = new Properties();
    props.setProperty(ConfigurableLocatorFactory.PARAM_URI_LOCATORS, locatorsAsString);
    return props;
  }

  @Test public void shouldDetectConfiguredLocators(){final String locatorsAsString=ConfigurableLocatorFactory.createItemsAsString(ServletContextUriLocator.ALIAS_DISPATCHER_FIRST,ServletContextUriLocator.ALIAS_SERVLET_CONTEXT_FIRST,ServletContextUriLocator.ALIAS,ClasspathUriLocator.ALIAS,UrlUriLocator.ALIAS);victim.setProperties(createPropsWithLocators(locatorsAsString));final List<UriLocator> locators=victim.getConfiguredStrategies();assertEquals(5,locators.size());final Iterator<UriLocator> iterator=locators.iterator();assertEquals(ServletContextUriLocator.class,iterator.next().getClass());assertEquals(ServletContextUriLocator.class,iterator.next().getClass());assertEquals(ServletContextUriLocator.class,iterator.next().getClass());assertEquals(ClasspathUriLocator.class,iterator.next().getClass());assertEquals(UrlUriLocator.class,iterator.next().getClass());}
}
