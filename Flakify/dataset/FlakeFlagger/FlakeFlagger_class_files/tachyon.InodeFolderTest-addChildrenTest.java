package tachyon;

import java.util.Map;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for tachyon.InodeFolder
 */
public class InodeFolderTest {
  @Test public void addChildrenTest(){InodeFolder inodeFolder=new InodeFolder("testFolder1",1,0,System.currentTimeMillis());inodeFolder.addChild(2);inodeFolder.addChild(3);Assert.assertEquals(2,(int)inodeFolder.getChildrenIds().get(0));Assert.assertEquals(3,(int)inodeFolder.getChildrenIds().get(1));} 
}
