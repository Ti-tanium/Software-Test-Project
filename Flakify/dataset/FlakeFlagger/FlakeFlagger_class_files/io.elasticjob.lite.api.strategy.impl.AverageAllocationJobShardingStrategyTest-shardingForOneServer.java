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

package io.elasticjob.lite.api.strategy.impl;

import io.elasticjob.lite.api.strategy.JobInstance;
import io.elasticjob.lite.api.strategy.JobShardingStrategy;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class AverageAllocationJobShardingStrategyTest {
    
    private final JobShardingStrategy jobShardingStrategy = new AverageAllocationJobShardingStrategy();
    
    @Test public void shardingForOneServer(){Map<JobInstance, List<Integer>> expected=new LinkedHashMap<>(1,1);expected.put(new JobInstance("host0@-@0"),Arrays.asList(0,1,2));assertThat(jobShardingStrategy.sharding(Collections.singletonList(new JobInstance("host0@-@0")),"test_job",3),is(expected));}
}
