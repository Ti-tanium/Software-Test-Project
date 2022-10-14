package ro.isdc.wro.maven.plugin.support;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ro.isdc.wro.model.resource.Resource;

/**
 * @author Alex Objelean
 */
public class TestProgressIndicator {
  private Log log;
  private ProgressIndicator victim;

  @Before
  public void setUp() {
    log = Mockito.spy(new SystemStreamLog());
    //suppress debug level
    Mockito.doAnswer(new Answer<Void>() {
      public Void answer(final InvocationOnMock invocation)
          throws Throwable {
        //do nothing
        return null;
      }
    }).when(log).debug(Mockito.anyString());
    victim = new ProgressIndicator(log);
  }

  @Test public void shouldNotHaveVerboseInfoLog(){final int times=5000;final AtomicInteger counter=new AtomicInteger();Mockito.doAnswer(new Answer<Void>(){public Void answer(final InvocationOnMock invocation) throws Throwable{counter.incrementAndGet();invocation.callRealMethod();return null;}}).when(log).info(Mockito.anyString());victim.setTimeoutDelay(1);for (int i=0;i < times;i++){victim.onProcessingResource(generateRandomResource());}Assert.assertTrue(counter.get() < times);}

  private Resource generateRandomResource() {
    return Resource.create(UUID.randomUUID() + ".js");
  }
}
