package tachyon;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import tachyon.thrift.BlockInfoException;
import tachyon.thrift.NetAddress;
import tachyon.thrift.SuspectedFileSizeException;

/**
 * Unit tests for tachyon.InodeFile
 */
public class InodeFileTest {
  @Test public void setCacheTest(){InodeFile inodeFile=new InodeFile("testFile1",1,0,1000,System.currentTimeMillis());Assert.assertFalse(inodeFile.isCache());inodeFile.setCache(true);Assert.assertTrue(inodeFile.isCache());inodeFile.setCache(false);Assert.assertFalse(inodeFile.isCache());} 
}