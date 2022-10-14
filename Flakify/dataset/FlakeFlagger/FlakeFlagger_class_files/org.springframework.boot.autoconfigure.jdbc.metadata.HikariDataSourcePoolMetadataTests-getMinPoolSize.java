package org.springframework.boot.autoconfigure.jdbc.metadata;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class HikariDataSourcePoolMetadataTests {
@Test public void getMinPoolSize(){
  assertEquals(Integer.valueOf(0),getDataSourceMetadata().getMin());
}

}