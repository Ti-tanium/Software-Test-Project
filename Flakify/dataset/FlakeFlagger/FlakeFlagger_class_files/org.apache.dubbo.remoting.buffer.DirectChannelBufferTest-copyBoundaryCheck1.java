package org.apache.dubbo.remoting.buffer;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class DirectChannelBufferTest {
@Test(expected=IndexOutOfBoundsException.class) public void copyBoundaryCheck1(){
  buffer.copy(-1,0);
}

}