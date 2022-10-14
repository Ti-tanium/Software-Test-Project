package me.prettyprint.hom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;
import me.prettyprint.hom.HectorObjectMapper;
import me.prettyprint.hom.beans.MyCustomIdBean;
import me.prettyprint.hom.beans.MyTestBean;

import org.junit.Before;
import org.junit.Test;

import com.mycompany.MySerial;


public class HectorObjectMapperTest {
  ClassCacheMgr cacheMgr = new ClassCacheMgr();

  @Test public void testIsSerializable(){assertTrue(HectorObjectMapper.isSerializable(UUID.class));}
  
  

  // --------------------

  @Before
  public void setupTest() {
    cacheMgr.initializeCacheForClass(MyTestBean.class);
    cacheMgr.initializeCacheForClass(MyCustomIdBean.class);
  }
}
