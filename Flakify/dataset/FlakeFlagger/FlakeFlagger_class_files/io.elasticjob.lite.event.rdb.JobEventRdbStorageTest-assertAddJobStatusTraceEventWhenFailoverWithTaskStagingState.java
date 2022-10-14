/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.elasticjob.lite.event.rdb;

import io.elasticjob.lite.context.ExecutionType;
import io.elasticjob.lite.event.type.JobExecutionEvent;
import io.elasticjob.lite.event.type.JobStatusTraceEvent;
import io.elasticjob.lite.event.type.JobStatusTraceEvent.Source;
import io.elasticjob.lite.event.type.JobStatusTraceEvent.State;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class JobEventRdbStorageTest {
    
    private JobEventRdbStorage storage;
    
    @Before
    public void setup() throws SQLException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(org.h2.Driver.class.getName());
        dataSource.setUrl("jdbc:h2:mem:job_event_storage");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        storage = new JobEventRdbStorage(dataSource);
    }
    
    @Test public void assertAddJobStatusTraceEventWhenFailoverWithTaskStagingState() throws SQLException{JobStatusTraceEvent jobStatusTraceEvent=new JobStatusTraceEvent("test_job","fake_failover_task_id","fake_slave_id",Source.LITE_EXECUTOR,ExecutionType.FAILOVER,"0",State.TASK_STAGING,"message is empty.");jobStatusTraceEvent.setOriginalTaskId("original_fake_failover_task_id");assertThat(storage.getJobStatusTraceEvents("fake_failover_task_id").size(),is(0));storage.addJobStatusTraceEvent(jobStatusTraceEvent);assertThat(storage.getJobStatusTraceEvents("fake_failover_task_id").size(),is(1));}
}
