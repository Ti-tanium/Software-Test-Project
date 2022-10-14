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

import static info.archinnov.achilles.configuration.ConfigurationParameters.CONSISTENCY_LEVEL_READ_MAP;
import static info.archinnov.achilles.configuration.ConfigurationParameters.CONSISTENCY_LEVEL_WRITE_MAP;
import static info.archinnov.achilles.test.integration.entity.CompleteBeanTestBuilder.builder;
import static info.archinnov.achilles.type.ConsistencyLevel.ALL;
import static info.archinnov.achilles.type.ConsistencyLevel.EACH_QUORUM;
import static info.archinnov.achilles.type.ConsistencyLevel.ONE;
import static info.archinnov.achilles.type.ConsistencyLevel.QUORUM;
import static info.archinnov.achilles.type.OptionsBuilder.withConsistency;
import static org.fest.assertions.api.Assertions.assertThat;
import java.util.Arrays;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.exceptions.UnavailableException;
import com.google.common.collect.ImmutableMap;
import info.archinnov.achilles.configuration.ConfigurationParameters;
import info.archinnov.achilles.embedded.CassandraEmbeddedServerBuilder;
import info.archinnov.achilles.junit.AchillesTestResource.Steps;
import info.archinnov.achilles.persistence.PersistenceManager;
import info.archinnov.achilles.test.integration.AchillesInternalCQLResource;
import info.archinnov.achilles.test.integration.entity.CompleteBean;
import info.archinnov.achilles.test.integration.entity.CompleteBeanTestBuilder;
import info.archinnov.achilles.test.integration.entity.EntityWithTwoConsistency;
import info.archinnov.achilles.test.integration.entity.EntityWithWriteOneAndReadThreeConsistency;
import info.archinnov.achilles.test.integration.utils.CassandraLogAsserter;

public class ConsistencyLevelIT {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Rule
    public AchillesInternalCQLResource resource = new AchillesInternalCQLResource(Steps.AFTER_TEST, "CompleteBean",
            EntityWithTwoConsistency.TABLE_NAME, EntityWithWriteOneAndReadThreeConsistency.TABLE_NAME);

    private PersistenceManager manager = resource.getPersistenceManager();

    private CassandraLogAsserter logAsserter = new CassandraLogAsserter();

    private Long id = RandomUtils.nextLong();

    @Test public void should_override_consistency_by_map() throws Exception{final PersistenceManager pm=CassandraEmbeddedServerBuilder.withEntities(Arrays.<Class<?>>asList(EntityWithTwoConsistency.class)).withKeyspaceName("consistency_map_override").cleanDataFilesAtStartup(true).withAchillesConfigParams(ImmutableMap.<ConfigurationParameters,Object>of(CONSISTENCY_LEVEL_READ_MAP,ImmutableMap.of(EntityWithTwoConsistency.TABLE_NAME,QUORUM),CONSISTENCY_LEVEL_WRITE_MAP,ImmutableMap.of(EntityWithTwoConsistency.TABLE_NAME,ONE))).buildPersistenceManager();logAsserter.prepareLogLevel();final EntityWithTwoConsistency entity=new EntityWithTwoConsistency();entity.setId(RandomUtils.nextLong());pm.persist(entity);assertThat(pm.find(EntityWithTwoConsistency.class,entity.getId())).isNotNull();logAsserter.assertConsistencyLevels(ONE,QUORUM);}

}
