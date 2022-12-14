/*
 * Copyright (C) 2012-2014 DuyHai DOAN
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package info.archinnov.achilles.schemabuilder;

import static info.archinnov.achilles.schemabuilder.Create.Options.ClusteringOrder.Sorting.ASC;
import static info.archinnov.achilles.schemabuilder.Create.Options.ClusteringOrder.Sorting.DESC;
import static info.archinnov.achilles.schemabuilder.TableOptions.Caching.ROWS_ONLY;
import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.datastax.driver.core.DataType;
import info.archinnov.achilles.schemabuilder.Create;
import info.archinnov.achilles.schemabuilder.SchemaBuilder;
import info.archinnov.achilles.schemabuilder.TableOptions;

public class CreateTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test public void should_create_table_with_all_options() throws Exception{final String built=SchemaBuilder.createTable("test").addPartitionKey("id",DataType.bigint()).addClusteringKey("col1",DataType.uuid()).addClusteringKey("col2",DataType.uuid()).addColumn("name",DataType.text()).withOptions().clusteringOrder(new Create.Options.ClusteringOrder("col1",ASC),new Create.Options.ClusteringOrder("col2",DESC)).compactStorage(true).bloomFilterFPChance(0.01).caching(ROWS_ONLY).comment("This is a comment").compactionOptions(TableOptions.CompactionOptions.leveledStrategy().ssTableSizeInMB(160)).compressionOptions(TableOptions.CompressionOptions.lz4()).dcLocalReadRepairChance(0.21).defaultTimeToLive(100).gcGraceSeconds(9999L).indexInterval(512).memtableFlushPeriodInMillis(12L).populateIOOnCacheFlush(true).replicateOnWrite(true).speculativeRetry(TableOptions.SpeculativeRetryValue.always()).build();assertThat(built).isEqualTo("\n\tCREATE TABLE test(\n\t\t" + "id bigint,\n\t\t" + "col1 uuid,\n\t\t" + "col2 uuid,\n\t\t" + "name text,\n\t\tPRIMARY KEY(id, col1, col2))\n\t" + "WITH caching = 'rows_only' " + "AND bloom_filter_fp_chance = 0.01 " + "AND comment = 'This is a comment' " + "AND compression = {'sstable_compression' : 'LZ4Compressor'} " + "AND compaction = {'class' : 'LeveledCompactionStrategy', 'sstable_size_in_mb' : 160} " + "AND dclocal_read_repair_chance = 0.21 " + "AND default_time_to_live = 100 " + "AND gc_grace_seconds = 9999 " + "AND index_interval = 512 " + "AND memtable_flush_period_in_ms = 12 " + "AND populate_io_cache_on_flush = true " + "AND replicate_on_write = true " + "AND speculative_retry = 'ALWAYS' AND CLUSTERING ORDER BY(col1 ASC, col2 DESC) AND COMPACT STORAGE");}
}
