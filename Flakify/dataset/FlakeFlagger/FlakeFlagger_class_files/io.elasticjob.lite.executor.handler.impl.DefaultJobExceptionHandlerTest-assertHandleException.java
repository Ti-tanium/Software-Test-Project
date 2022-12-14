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

package io.elasticjob.lite.executor.handler.impl;

import io.elasticjob.lite.event.fixture.JobEventCaller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public final class DefaultJobExceptionHandlerTest {
    
    @Mock
    private JobEventCaller caller;
    
    @Test public void assertHandleException(){new DefaultJobExceptionHandler().handleException("test_job",new RuntimeException("test"));verify(caller,atMost(1)).call();}
}
