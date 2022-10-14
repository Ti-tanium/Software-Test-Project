package tachyon.command;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tachyon.Constants;
import tachyon.LocalTachyonCluster;
import tachyon.TestUtils;
import tachyon.client.InStream;
import tachyon.client.ReadType;
import tachyon.client.TachyonFS;
import tachyon.client.TachyonFile;
import tachyon.client.WriteType;
import tachyon.thrift.ClientBlockInfo;

/**
 * Unit tests on TFsShell.
 */
public class TFsShellTest {
  private final int mSizeBytes = Constants.MB * 10;
  private LocalTachyonCluster mLocalTachyonCluster = null;
  private TachyonFS mTfs = null;
  private TFsShell mFsShell = null;
  private ByteArrayOutputStream mOutput = null;
  private PrintStream mNewOutput = null;
  private PrintStream mOldOutput = null;

  private String getCommandOutput(String[] command) {
    String cmd = command[0];
    if (command.length == 2) {
      if (cmd.equals("ls")) {
        // Not sure how to handle this one.
        return null; 
      } else if (cmd.equals("mkdir")) {
        return "Successfully created directory " + command[1] + "\n";
      } else if (cmd.equals("rm")) {
        return command[1] + " has been removed" + "\n";
      }
    } else if (command.length == 3) {
      if (cmd.equals("mv")) {
        return "Renamed " + command[1] + " to " + command[2] + "\n";
      } else if (cmd.equals("copyFromLocal")) {
        return "Copied " + command[1] + " to " + command[2] + "\n";
      } else if (cmd.equals("copyToLocal")) {
        return "Copied " + command[1] + " to " + command[2] + "\n";
      }
    } else if (command.length > 3) {
      if (cmd.equals("location")) {
        StringBuilder ret = new StringBuilder();
        ret.append(command[1] + " with file id " + command[2] + " are on nodes: \n");
        for (int i = 3; i < command.length; i ++) {
          ret.append(command[i] + "\n");
        }
        return ret.toString();
      }else if (cmd.equals("fileinfo")) {
        StringBuilder ret = new StringBuilder();
        ret.append(command[1] + " with file id " + command[2] + " have following blocks: \n");
        for (int i = 3; i < command.length; i ++) {
          ret.append(command[i] + "\n");
        }
        return ret.toString();
      }
    }
    return null;
  }


  @Before
  public final void before() throws IOException {
    System.setProperty("tachyon.user.quota.unit.bytes", "1000");
    mLocalTachyonCluster = new LocalTachyonCluster(mSizeBytes);
    mLocalTachyonCluster.start();
    mTfs = mLocalTachyonCluster.getClient();
    mFsShell = new TFsShell();
    mOutput = new ByteArrayOutputStream();
    mNewOutput = new PrintStream(mOutput);
    mOldOutput = System.out;
    System.setOut(mNewOutput);
  }

  @Test public void fileinfoTest() throws IOException{int fileId=TestUtils.createByteFile(mTfs,"/testFile",WriteType.MUST_CACHE,10);mFsShell.fileinfo(new String[]{"fileinfo","/testFile"});TachyonFile tFile=mTfs.getFile("/testFile");Assert.assertNotNull(tFile);List<ClientBlockInfo> blocks=mTfs.getFileBlocks(fileId);String[] commandParameters=new String[3 + blocks.size()];commandParameters[0]="fileinfo";commandParameters[1]="/testFile";commandParameters[2]=String.valueOf(fileId);Iterator<ClientBlockInfo> iter=blocks.iterator();int i=3;while (iter.hasNext()){commandParameters[i++]=iter.next().toString();}Assert.assertEquals(getCommandOutput(commandParameters),mOutput.toString());}
}
