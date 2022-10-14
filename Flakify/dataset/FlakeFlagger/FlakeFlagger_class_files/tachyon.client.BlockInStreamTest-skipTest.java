package tachyon.client;

import java.io.IOException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tachyon.LocalTachyonCluster;
import tachyon.TestUtils;

/**
 * Unit tests for <code>tachyon.client.BlockInStream</code>.
 */
public class BlockInStreamTest {
  private final int MIN_LEN = 0;
  private final int MAX_LEN = 255;
  private final int MEAN = (MIN_LEN + MAX_LEN) / 2;
  private final int DELTA = 33;

  private LocalTachyonCluster mLocalTachyonCluster = null;
  private TachyonFS mTfs = null;

  @Before
  public final void before() throws IOException {
    System.setProperty("tachyon.user.quota.unit.bytes", "1000");
    mLocalTachyonCluster = new LocalTachyonCluster(10000);
    mLocalTachyonCluster.start();
    mTfs = mLocalTachyonCluster.getClient();
  }

  /**
 * Test <code>long skip(long len)</code>.
 */@Test public void skipTest() throws IOException{for (int k=MIN_LEN + DELTA;k <= MAX_LEN;k+=DELTA){for (WriteType op:WriteType.values()){int fileId=TestUtils.createByteFile(mTfs,"/root/testFile_" + k + "_" + op,op,k);TachyonFile file=mTfs.getFile(fileId);InStream is=(k < MEAN?file.getInStream(ReadType.CACHE):file.getInStream(ReadType.NO_CACHE));Assert.assertTrue(is instanceof BlockInStream);Assert.assertEquals(k / 2,is.skip(k / 2));Assert.assertEquals(k / 2,is.read());is.close();is=(k < MEAN?file.getInStream(ReadType.CACHE):file.getInStream(ReadType.NO_CACHE));Assert.assertTrue(is instanceof BlockInStream);int t=k / 3;Assert.assertEquals(t,is.skip(t));Assert.assertEquals(t,is.read());Assert.assertEquals(t,is.skip(t));Assert.assertEquals(2 * t + 1,is.read());is.close();}}}
}
