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

package io.elasticjob.lite.internal.guarantee;

import io.elasticjob.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import io.elasticjob.lite.api.listener.ElasticJobListener;
import io.elasticjob.lite.internal.listener.AbstractJobListener;
import io.elasticjob.lite.internal.storage.JobNodeStorage;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent.Type;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.unitils.util.ReflectionUtils;

import java.util.Arrays;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public final class GuaranteeListenerManagerTest {
    
    @Mock
    private JobNodeStorage jobNodeStorage;
    
    @Mock
    private ElasticJobListener elasticJobListener;
    
    @Mock
    private AbstractDistributeOnceElasticJobListener distributeOnceElasticJobListener;
    
    private GuaranteeListenerManager guaranteeListenerManager;
    
    @Before
    public void setUp() throws NoSuchFieldException {
        MockitoAnnotations.initMocks(this);
        guaranteeListenerManager = new GuaranteeListenerManager(null, "test_job", Arrays.asList(elasticJobListener, distributeOnceElasticJobListener));
        ReflectionUtils.setFieldValue(guaranteeListenerManager, guaranteeListenerManager.getClass().getSuperclass().getDeclaredField("jobNodeStorage"), jobNodeStorage);
    }
    
    @Test public void assertCompletedNodeRemovedJobListenerWhenIsRemovedAndCompletedNode(){guaranteeListenerManager.new CompletedNodeRemovedJobListener().dataChanged("/test_job/guarantee/completed",Type.NODE_REMOVED,"");verify(distributeOnceElasticJobListener).notifyWaitingTaskComplete();}
}
