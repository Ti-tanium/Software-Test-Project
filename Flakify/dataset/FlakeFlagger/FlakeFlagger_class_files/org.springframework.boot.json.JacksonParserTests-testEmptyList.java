package org.springframework.boot.json;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class JacksonParserTests {
@Test public void testEmptyList(){
  List<Object> list=this.parser.parseList("[]");
  assertEquals(0,list.size());
}

}