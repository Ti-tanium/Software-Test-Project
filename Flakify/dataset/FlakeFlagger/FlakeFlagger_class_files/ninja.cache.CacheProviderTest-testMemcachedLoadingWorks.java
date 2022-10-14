package ninja.cache;

import static org.junit.Assert.assertTrue;
import ninja.Configuration;
import ninja.lifecycle.LifecycleSupport;
import ninja.utils.NinjaConstant;
import ninja.utils.NinjaMode;
import ninja.utils.NinjaPropertiesImpl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;


public class CacheProviderTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    

    @Test public void testMemcachedLoadingWorks(){NinjaPropertiesImpl ninjaProperties=new NinjaPropertiesImpl(NinjaMode.test);ninjaProperties.setProperty(NinjaConstant.CACHE_IMPLEMENTATION,CacheMemcachedImpl.class.getName());ninjaProperties.setProperty(NinjaConstant.MEMCACHED_HOST,"127.0.0.1:1234");Injector injector=Guice.createInjector(new Configuration(ninjaProperties),LifecycleSupport.getModule());Logger logger=injector.getInstance(Logger.class);CacheProvider cacheProvider=new CacheProvider(injector,ninjaProperties,logger);assertTrue(cacheProvider.get() instanceof CacheMemcachedImpl);}

}
