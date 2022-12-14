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

package io.elasticjob.lite.context;

import com.google.common.collect.Lists;
import io.elasticjob.lite.fixture.context.TaskNode;
import org.hamcrest.core.Is;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public final class TaskContextTest {
    
    @Test public void assertSetSlaveId(){TaskContext actual=new TaskContext("test_job",Lists.newArrayList(0),ExecutionType.READY,"slave-S0");assertThat(actual.getSlaveId(),is("slave-S0"));actual.setSlaveId("slave-S1");assertThat(actual.getSlaveId(),is("slave-S1"));}
}
