package org.apache.dubbo.remoting.transport.codec;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class CodecAdapterTest {
@Test public void testDecode_Person_WithEnter() throws IOException {
  testDecode_PersonWithEnterByte(new byte[]{'\r','\n'},false);
  testDecode_PersonWithEnterByte(new byte[]{'\n','\r'},true);
  testDecode_PersonWithEnterByte(new byte[]{'\n'},false);
  testDecode_PersonWithEnterByte(new byte[]{'\r'},true);
  testDecode_PersonWithEnterByte(new byte[]{'\r',100},true);
}

}