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

package io.elasticjob.lite.api.strategy;

import io.elasticjob.lite.api.strategy.fixture.InvalidJobShardingStrategy;
import io.elasticjob.lite.api.strategy.impl.AverageAllocationJobShardingStrategy;
import io.elasticjob.lite.exception.JobConfigurationException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class JobShardingStrategyFactoryTest {
    
    @Test(expected=JobConfigurationException.class) public void assertGetStrategyFailureWhenStrategyClassInvalid(){JobShardingStrategyFactory.getStrategy(InvalidJobShardingStrategy.class.getName());}
}
