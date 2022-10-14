package ro.isdc.wro.http.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.config.jmx.WroConfiguration;


/**
 * Test the behavior of {@link ResponseHeadersConfigurer}
 *
 * @author Alex Objelean
 */
public class TestResponseHeadersConfigurer {
  @Mock
  private HttpServletRequest request;
  @Mock
  private HttpServletResponse response;
  @Mock
  private FilterChain chain;
  private ResponseHeadersConfigurer victim;

  @Before
  public void setUp()
      throws Exception {
    MockitoAnnotations.initMocks(this);
    victim = new ResponseHeadersConfigurer();
  }

  @Test public void shouldHaveNoCacheHeadersInDebugMode(){final WroConfiguration config=new WroConfiguration();config.setDebug(true);victim=ResponseHeadersConfigurer.fromConfig(config);final Map<String, String> map=victim.getHeadersMap();assertEquals(3,map.size());assertEquals("no-cache",map.get(HttpHeader.PRAGMA.getHeaderName()));assertEquals("no-cache",map.get(HttpHeader.CACHE_CONTROL.getHeaderName()));assertEquals("0",map.get(HttpHeader.EXPIRES.getHeaderName()));}
}
