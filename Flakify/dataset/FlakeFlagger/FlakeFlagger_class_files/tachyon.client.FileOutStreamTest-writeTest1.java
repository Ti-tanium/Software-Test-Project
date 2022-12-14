package tachyon.client;

import java.io.IOException;
import java.io.InputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tachyon.LocalTachyonCluster;
import tachyon.TestUtils;
import tachyon.UnderFileSystem;
import tachyon.thrift.FileAlreadyExistException;
import tachyon.thrift.InvalidPathException;

/**
 * Unit tests for <code>tachyon.client.FileOutStream</code>.
 */
public class FileOutStreamTest {
  private final int MIN_LEN = 0;
  private final int MAX_LEN = 255;
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

  private void writeTest1Util(String filePath, WriteType op, int len)
      throws InvalidPathException, FileAlreadyExistException, IOException {
    int fileId = mTfs.createFile(filePath);
    TachyonFile file = mTfs.getFile(fileId);
    OutStream os = file.getOutStream(op);
    Assert.assertTrue(os instanceof FileOutStream);
    for (int k = 0; k < len; k ++) {
      os.write((byte) k);
    }
    os.close();

    for (ReadType rOp : ReadType.values()) {
      file = mTfs.getFile(filePath);
      InStream is = file.getInStream(rOp);
      byte[] res = new byte[(int) file.length()];
      Assert.assertEquals((int) file.length(), is.read(res));
      Assert.assertTrue(TestUtils.equalIncreasingByteArray(len, res));
    }

    if (op.isThrough()) {
      file = mTfs.getFile(filePath);
      String checkpointPath = file.getCheckpointPath();
      UnderFileSystem ufs = UnderFileSystem.get(checkpointPath);

      InputStream is = ufs.open(checkpointPath);
      byte[] res = new byte[(int) file.length()];
      Assert.assertEquals((int) file.length(), is.read(res));
      Assert.assertTrue(TestUtils.equalIncreasingByteArray(len, res));
    }
  }

  /**
 * Test <code>void write(int b)</code>.
 */@Test public void writeTest1() throws IOException,InvalidPathException,FileAlreadyExistException{for (int k=MIN_LEN;k <= MAX_LEN;k+=DELTA){for (WriteType op:WriteType.values()){writeTest1Util("/root/testFile_" + k + "_" + op,op,k);}}}

  private void writeTest2Util(String filePath, WriteType op, int len)
      throws InvalidPathException, FileAlreadyExistException, IOException {
    int fileId = mTfs.createFile(filePath);
    TachyonFile file = mTfs.getFile(fileId);
    OutStream os = file.getOutStream(op);
    Assert.assertTrue(os instanceof FileOutStream);
    os.write(TestUtils.getIncreasingByteArray(len));
    os.close();

    for (ReadType rOp : ReadType.values()) {
      file = mTfs.getFile(filePath);
      InStream is = file.getInStream(rOp);
      byte[] res = new byte[(int) file.length()];
      Assert.assertEquals((int) file.length(), is.read(res));
      Assert.assertTrue(TestUtils.equalIncreasingByteArray(len, res));
    }

    if (op.isThrough()) {
      file = mTfs.getFile(filePath);
      String checkpointPath = file.getCheckpointPath();
      UnderFileSystem ufs = UnderFileSystem.get(checkpointPath);

      InputStream is = ufs.open(checkpointPath);
      byte[] res = new byte[(int) file.length()];
      Assert.assertEquals((int) file.length(), is.read(res));
      Assert.assertTrue(TestUtils.equalIncreasingByteArray(len, res));
    }
  }

  private void writeTest3Util(String filePath, WriteType op, int len)
      throws InvalidPathException, FileAlreadyExistException, IOException {
    int fileId = mTfs.createFile(filePath);
    TachyonFile file = mTfs.getFile(fileId);
    OutStream os = file.getOutStream(op);
    Assert.assertTrue(os instanceof FileOutStream);
    os.write(TestUtils.getIncreasingByteArray(len), 0, len / 2);
    os.close();

    for (ReadType rOp : ReadType.values()) {
      file = mTfs.getFile(filePath);
      InStream is = file.getInStream(rOp);
      byte[] res = new byte[(int) file.length()];
      Assert.assertEquals((int) file.length(), is.read(res));
      Assert.assertTrue(TestUtils.equalIncreasingByteArray(len / 2, res));
    }

    if (op.isThrough()) {
      file = mTfs.getFile(filePath);
      String checkpointPath = file.getCheckpointPath();
      UnderFileSystem ufs = UnderFileSystem.get(checkpointPath);

      InputStream is = ufs.open(checkpointPath);
      byte[] res = new byte[(int) file.length()];
      Assert.assertEquals((int) file.length(), is.read(res));
      Assert.assertTrue(TestUtils.equalIncreasingByteArray(len / 2, res));
    }
  }
}
