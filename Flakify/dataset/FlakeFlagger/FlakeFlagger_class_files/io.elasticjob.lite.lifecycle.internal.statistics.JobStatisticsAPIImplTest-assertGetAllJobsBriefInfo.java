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

package io.elasticjob.lite.lifecycle.internal.statistics;

import io.elasticjob.lite.lifecycle.api.JobStatisticsAPI;
import io.elasticjob.lite.lifecycle.domain.JobBriefInfo;
import io.elasticjob.lite.lifecycle.fixture.LifecycleJsonConstants;
import io.elasticjob.lite.reg.base.CoordinatorRegistryCenter;
import com.google.common.collect.Lists;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public final class JobStatisticsAPIImplTest {
    
    private JobStatisticsAPI jobStatisticsAPI;
    
    @Mock
    private CoordinatorRegistryCenter regCenter;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jobStatisticsAPI = new JobStatisticsAPIImpl(regCenter);
    }
    
    @Test public void assertGetAllJobsBriefInfo(){when(regCenter.getChildrenKeys("/")).thenReturn(Arrays.asList("test_job_1","test_job_2"));when(regCenter.get("/test_job_1/config")).thenReturn(LifecycleJsonConstants.getSimpleJobJson("test_job_1","desc1"));when(regCenter.get("/test_job_2/config")).thenReturn(LifecycleJsonConstants.getSimpleJobJson("test_job_2","desc2"));when(regCenter.getChildrenKeys("/test_job_1/servers")).thenReturn(Arrays.asList("ip1","ip2"));when(regCenter.getChildrenKeys("/test_job_2/servers")).thenReturn(Arrays.asList("ip3","ip4"));when(regCenter.getChildrenKeys("/test_job_1/sharding")).thenReturn(Arrays.asList("0","1"));when(regCenter.get("/test_job_1/sharding/0/instance")).thenReturn("ip1@-@defaultInstance");when(regCenter.get("/test_job_1/sharding/1/instance")).thenReturn("ip2@-@defaultInstance");when(regCenter.getChildrenKeys("/test_job_2/sharding")).thenReturn(Arrays.asList("0","1"));when(regCenter.get("/test_job_2/sharding/0/instance")).thenReturn("ip3@-@defaultInstance");when(regCenter.get("/test_job_2/sharding/1/instance")).thenReturn("ip4@-@defaultInstance");when(regCenter.getChildrenKeys("/test_job_1/instances")).thenReturn(Arrays.asList("ip1@-@defaultInstance","ip2@-@defaultInstance"));when(regCenter.getChildrenKeys("/test_job_2/instances")).thenReturn(Arrays.asList("ip3@-@defaultInstance","ip4@-@defaultInstance"));int i=0;for (JobBriefInfo each:jobStatisticsAPI.getAllJobsBriefInfo()){i++;assertThat(each.getJobName(),is("test_job_" + i));assertThat(each.getDescription(),is("desc" + i));assertThat(each.getCron(),is("0/1 * * * * ?"));assertThat(each.getInstanceCount(),is(2));assertThat(each.getShardingTotalCount(),is(3));assertThat(each.getStatus(),Is.is(JobBriefInfo.JobStatus.OK));}}
}
