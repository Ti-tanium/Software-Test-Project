package com.google.zxing.aztec;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class AztecBlackBox2TestCase {
@Test public void testBlackBox() throws IOException {
  testBlackBoxCountingResults(true);
}

}