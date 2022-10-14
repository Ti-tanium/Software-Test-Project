package org.apache.dubbo.common.extension;
import java.io.File;
import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.sql.*;
import java.net.*;

public class ExtensionLoader_Adaptive_UseJdkCompiler_Test {
@Test public void test_getAdaptiveExtension_ExceptionWhenNotAdaptiveMethod() throws Exception {
  SimpleExt ext=ExtensionLoader.getExtensionLoader(SimpleExt.class).getAdaptiveExtension();
  Map<String,String> map=new HashMap<String,String>();
  URL url=new URL("p1","1.2.3.4",1010,"path1",map);
  try {
    ext.bang(url,33);
    fail();
  }
 catch (  UnsupportedOperationException expected) {
    assertThat(expected.getMessage(),containsString("method "));
    assertThat(expected.getMessage(),containsString("of interface org.apache.dubbo.common.extension.ext1.SimpleExt is not adaptive method!"));
  }
}

}