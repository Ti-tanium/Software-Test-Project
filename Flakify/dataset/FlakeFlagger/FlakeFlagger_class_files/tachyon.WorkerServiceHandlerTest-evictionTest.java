package tachyon;

import tachyon.client.TachyonFS;
import tachyon.client.WriteType;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import org.apache.thrift.TException;
import tachyon.thrift.FileAlreadyExistException;
import tachyon.thrift.FileDoesNotExistException;
import tachyon.thrift.InvalidPathException;
import tachyon.thrift.ClientFileInfo;

/**
 * Unit tests for tachyon.WorkerServiceHandler
 */
public class WorkerServiceHandlerTest {
  private LocalTachyonCluster mLocalTachyonCluster = null;
  private MasterInfo mMasterInfo = null;
  private WorkerServiceHandler mWorkerServiceHandler = null;
  private TachyonFS mTfs = null;
  private final long WORKER_CAPACITY_BYTES = 10000;
  private final int WORKER_TO_MASTER_HEARTBEAT_INTERVAL_MS = 5;

  @Before
  public final void before() throws IOException {
    System.setProperty("tachyon.user.quota.unit.bytes", WORKER_TO_MASTER_HEARTBEAT_INTERVAL_MS + "");
    System.setProperty("tachyon.worker.to.master.heartbeat.interval.ms",
        WORKER_TO_MASTER_HEARTBEAT_INTERVAL_MS + "");
    mLocalTachyonCluster = new LocalTachyonCluster(WORKER_CAPACITY_BYTES);
    mLocalTachyonCluster.start();
    mWorkerServiceHandler = mLocalTachyonCluster.getWorkerServiceHandler();
    mMasterInfo = mLocalTachyonCluster.getMasterInfo();
    mTfs = mLocalTachyonCluster.getClient();
  }

  @Test public void evictionTest() throws InvalidPathException,FileAlreadyExistException,IOException,FileDoesNotExistException,TException{int fileId1=TestUtils.createByteFile(mTfs,"/file1",WriteType.MUST_CACHE,(int)WORKER_CAPACITY_BYTES / 3);Assert.assertTrue(fileId1 >= 0);ClientFileInfo fileInfo1=mMasterInfo.getClientFileInfo("/file1");Assert.assertTrue(fileInfo1.isInMemory());int fileId2=TestUtils.createByteFile(mTfs,"/file2",WriteType.MUST_CACHE,(int)WORKER_CAPACITY_BYTES / 3);Assert.assertTrue(fileId2 >= 0);fileInfo1=mMasterInfo.getClientFileInfo("/file1");ClientFileInfo fileInfo2=mMasterInfo.getClientFileInfo("/file2");Assert.assertTrue(fileInfo1.isInMemory());Assert.assertTrue(fileInfo2.isInMemory());int fileId3=TestUtils.createByteFile(mTfs,"/file3",WriteType.MUST_CACHE,(int)WORKER_CAPACITY_BYTES / 2);CommonUtils.sleepMs(null,WORKER_TO_MASTER_HEARTBEAT_INTERVAL_MS);fileInfo1=mMasterInfo.getClientFileInfo("/file1");fileInfo2=mMasterInfo.getClientFileInfo("/file2");ClientFileInfo fileInfo3=mMasterInfo.getClientFileInfo("/file3");Assert.assertTrue(fileId3 >= 0);Assert.assertFalse(fileInfo1.isInMemory());Assert.assertTrue(fileInfo2.isInMemory());Assert.assertTrue(fileInfo3.isInMemory());}
}