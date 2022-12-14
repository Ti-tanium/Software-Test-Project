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

package io.elasticjob.lite.executor.type;

import io.elasticjob.lite.executor.JobFacade;
import io.elasticjob.lite.executor.ShardingContexts;
import io.elasticjob.lite.fixture.ShardingContextsBuilder;
import io.elasticjob.lite.fixture.config.TestDataflowJobConfiguration;
import io.elasticjob.lite.fixture.job.JobCaller;
import io.elasticjob.lite.fixture.job.TestDataflowJob;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class DataflowJobExecutorTest {
    
    @Mock
    private JobCaller jobCaller;
    
    @Mock
    private JobFacade jobFacade;
    
    private ShardingContexts shardingContexts;
    
    private DataflowJobExecutor dataflowJobExecutor;
    
    @SuppressWarnings("unchecked") @Test public void assertExecuteWhenFetchDataIsNotEmptyAndIsEligibleForJobRunningForStreamingProcess(){setUp(true,ShardingContextsBuilder.getMultipleShardingContexts());when(jobFacade.isEligibleForJobRunning()).thenReturn(true);when(jobCaller.fetchData(0)).thenReturn(Arrays.<Object>asList(1,2),Collections.emptyList());when(jobCaller.fetchData(1)).thenReturn(Arrays.<Object>asList(3,4),Collections.emptyList());doThrow(new IllegalStateException()).when(jobCaller).processData(4);dataflowJobExecutor.execute();verify(jobCaller,times(2)).fetchData(0);verify(jobCaller,times(1)).fetchData(1);verify(jobCaller).processData(1);verify(jobCaller).processData(2);verify(jobCaller).processData(3);verify(jobCaller).processData(4);}
    
    private void setUp(final boolean isStreamingProcess, final ShardingContexts shardingContexts) {
        this.shardingContexts = shardingContexts;
        when(jobFacade.loadJobRootConfiguration(true)).thenReturn(new TestDataflowJobConfiguration(isStreamingProcess));
        when(jobFacade.getShardingContexts()).thenReturn(shardingContexts);
        dataflowJobExecutor = new DataflowJobExecutor(new TestDataflowJob(jobCaller), jobFacade);
        ElasticJobVerify.prepareForIsNotMisfire(jobFacade, shardingContexts);
    }
}
