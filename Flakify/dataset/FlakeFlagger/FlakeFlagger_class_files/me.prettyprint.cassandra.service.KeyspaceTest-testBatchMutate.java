package me.prettyprint.cassandra.service;

import static me.prettyprint.cassandra.utils.StringUtils.bytes;
import static me.prettyprint.cassandra.utils.StringUtils.string;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import me.prettyprint.cassandra.BaseEmbededServerSetupTest;
import me.prettyprint.cassandra.model.QuorumAllConsistencyLevelPolicy;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.exceptions.HNotFoundException;
import me.prettyprint.hector.api.exceptions.HectorException;
import me.prettyprint.hector.api.exceptions.PoolExhaustedException;

import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
//import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.Deletion;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;
import org.junit.Before;
import org.junit.Test;

/**
 * For the tests we assume the following structure:
 *
 * &lt;Keyspaces&gt; &lt;Keyspace Name="Keyspace1"&gt; &lt;ColumnFamily
 * CompareWith="BytesType" Name="Standard1" FlushPeriodInMinutes="60"/&gt;
 * &lt;ColumnFamily CompareWith="UTF8Type" Name="Standard2"/&gt;
 * &lt;ColumnFamily CompareWith="TimeUUIDType" Name="StandardByUUID1"/&gt;
 * &lt;ColumnFamily ColumnType="Super" CompareWith="UTF8Type"
 * CompareSubcolumnsWith="UTF8Type" Name="Super1"/&gt;
 *
 * @author Ran Tavory (rantav@gmail.com)
 * @author zznate (nate@riptano.com)
 */
public class KeyspaceTest extends BaseEmbededServerSetupTest {

  private KeyspaceService keyspace;
  private static final StringSerializer se = new StringSerializer();

  @Before
  public void setupCase() throws IllegalStateException, PoolExhaustedException, Exception {
    super.setupClient();
    
    keyspace = new KeyspaceServiceImpl("Keyspace1", new QuorumAllConsistencyLevelPolicy(), 
        connectionManager, FailoverPolicy.ON_FAIL_TRY_ALL_AVAILABLE);
  }

  @Test public void testBatchMutate() throws HectorException{Map<String, Map<String, List<Mutation>>> outerMutationMap=new HashMap<String, Map<String, List<Mutation>>>();for (int i=0;i < 10;i++){Map<String, List<Mutation>> mutationMap=new HashMap<String, List<Mutation>>();ArrayList<Mutation> mutations=new ArrayList<Mutation>(10);for (int j=0;j < 10;j++){Column col=new Column(StringSerializer.get().toByteBuffer("testBatchMutateColumn_" + j),StringSerializer.get().toByteBuffer("testBatchMutateColumn_value_" + j),connectionManager.createClock());ColumnOrSuperColumn cosc=new ColumnOrSuperColumn();cosc.setColumn(col);Mutation mutation=new Mutation();mutation.setColumn_or_supercolumn(cosc);mutations.add(mutation);}mutationMap.put("Standard1",mutations);outerMutationMap.put("testBatchMutateColumn_" + i,mutationMap);}keyspace.batchMutate(se.toBytesMap(outerMutationMap));outerMutationMap.clear();for (int i=0;i < 10;i++){for (int j=0;j < 10;j++){ColumnPath cp=new ColumnPath("Standard1");cp.setColumn(bytes("testBatchMutateColumn_" + j));Column col=keyspace.getColumn("testBatchMutateColumn_" + i,cp);assertNotNull(col);String value=string(col.getValue());assertEquals("testBatchMutateColumn_value_" + j,value);}}for (int i=0;i < 10;i++){ArrayList<Mutation> mutations=new ArrayList<Mutation>(10);Map<String, List<Mutation>> mutationMap=new HashMap<String, List<Mutation>>();SlicePredicate slicePredicate=new SlicePredicate();for (int j=0;j < 10;j++){slicePredicate.addToColumn_names(StringSerializer.get().toByteBuffer("testBatchMutateColumn_" + j));}Mutation mutation=new Mutation();Deletion deletion=new Deletion(connectionManager.createClock());deletion.setPredicate(slicePredicate);mutation.setDeletion(deletion);mutations.add(mutation);mutationMap.put("Standard1",mutations);outerMutationMap.put("testBatchMutateColumn_" + i,mutationMap);}keyspace.batchMutate(se.toBytesMap(outerMutationMap));for (int i=0;i < 10;i++){for (int j=0;j < 10;j++){ColumnPath cp=new ColumnPath("Standard1");cp.setColumn(bytes("testBatchMutateColumn_" + j));try {keyspace.getColumn("testBatchMutateColumn_" + i,cp);fail();} catch (HNotFoundException e){}}}}



}
