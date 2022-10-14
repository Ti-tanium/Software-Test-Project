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
package info.archinnov.achilles.internal.persistence.operations;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.datastax.driver.core.Session;
import info.archinnov.achilles.internal.context.PersistenceContext;
import info.archinnov.achilles.internal.metadata.holder.EntityMeta;
import info.archinnov.achilles.internal.metadata.holder.PropertyMeta;

@RunWith(MockitoJUnitRunner.class)
public class EntityPersisterTest {
    @InjectMocks
    private EntityPersister persister;

    @Mock
    private Session session;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private PersistenceContext.EntityFacade context;

    @Mock
    private CounterPersister counterPersister;

    @Mock
    private EntityMeta entityMeta;

    @Mock
    private PropertyMeta counterMeta;

    private Object entity = new Object();

    @Before
    public void setUp() {
        when(context.getEntityMeta()).thenReturn(entityMeta);
        when(context.getEntity()).thenReturn(entity);
    }

    @Test public void should_persist_clustered_counter() throws Exception{when(entityMeta.isClusteredCounter()).thenReturn(true);when(entityMeta.getAllCounterMetas()).thenReturn(asList(counterMeta));persister.persist(context);verify(counterPersister).persistClusteredCounters(context);}
}
