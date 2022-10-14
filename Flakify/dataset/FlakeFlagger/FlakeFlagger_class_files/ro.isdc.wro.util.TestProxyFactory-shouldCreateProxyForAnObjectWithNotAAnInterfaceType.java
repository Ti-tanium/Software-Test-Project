package ro.isdc.wro.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.Validate;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.isdc.wro.config.Context;
import ro.isdc.wro.config.ReadOnlyContext;
import ro.isdc.wro.model.resource.support.DefaultResourceAuthorizationManager;
import ro.isdc.wro.model.resource.support.MutableResourceAuthorizationManager;
import ro.isdc.wro.model.resource.support.ResourceAuthorizationManager;


/**
 * @author Alex Objelean
 */
public class TestProxyFactory {
  private static final Logger LOG = LoggerFactory.getLogger(TestProxyFactory.class);

  @Test public void shouldCreateProxyForAnObjectWithNotAAnInterfaceType(){final Object proxy=ProxyFactory.proxy(new ObjectFactory<Object>(){public Object create(){return new Object();}},Object.class);Validate.notNull(proxy);LOG.debug("Proxy: {}",proxy);}
}
