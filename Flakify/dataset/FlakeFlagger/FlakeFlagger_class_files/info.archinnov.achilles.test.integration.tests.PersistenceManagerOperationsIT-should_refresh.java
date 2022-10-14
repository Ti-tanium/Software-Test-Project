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
package info.archinnov.achilles.test.integration.tests;

import static org.fest.assertions.api.Assertions.assertThat;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import info.archinnov.achilles.exception.AchillesStaleObjectStateException;
import info.archinnov.achilles.internal.proxy.wrapper.ListWrapper;
import info.archinnov.achilles.junit.AchillesTestResource.Steps;
import info.archinnov.achilles.persistence.PersistenceManager;
import info.archinnov.achilles.test.integration.AchillesInternalCQLResource;
import info.archinnov.achilles.test.integration.entity.CompleteBean;
import info.archinnov.achilles.test.integration.entity.CompleteBeanTestBuilder;
import info.archinnov.achilles.type.CounterBuilder;
import net.sf.cglib.proxy.Factory;

public class PersistenceManagerOperationsIT {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public AchillesInternalCQLResource resource = new AchillesInternalCQLResource(Steps.AFTER_TEST, "CompleteBean");

    private PersistenceManager manager = resource.getPersistenceManager();
    private Session session = manager.getNativeSession();

    @Test
    public void should_refresh() throws Exception {

        CompleteBean entity = CompleteBeanTestBuilder.builder().randomId().name("DuyHai").age(35L)
                .addFriends("foo", "bar").addFollowers("George", "Paul").addPreference(1, "FR")
                .addPreference(2, "Paris").addPreference(3, "75014").buid();

        entity = manager.persist(entity);

        entity.getFriends();

        session.execute("UPDATE completebean SET name='DuyHai_modified' WHERE id=" + entity.getId());
        session.execute("UPDATE completebean SET friends=friends + ['qux'] WHERE id=" + entity.getId());

        manager.refresh(entity);

        assertThat(entity.getName()).isEqualTo("DuyHai_modified");
        assertThat(entity.getFriends()).hasSize(3);
        assertThat(entity.getFriends().get(2)).isEqualTo("qux");
    }
}
