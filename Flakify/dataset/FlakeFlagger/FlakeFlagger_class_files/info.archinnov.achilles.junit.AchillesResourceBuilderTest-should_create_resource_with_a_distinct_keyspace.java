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

package info.archinnov.achilles.junit;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Map;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import info.archinnov.achilles.persistence.PersistenceManager;
import info.archinnov.achilles.persistence.PersistenceManagerFactory;
import info.archinnov.achilles.test.integration.entity.User;

public class AchillesResourceBuilderTest {

	@Rule
	public AchillesResource resource = AchillesResourceBuilder
			.withEntityPackages("info.archinnov.achilles.test.integration.entity").tablesToTruncate("User")
			.truncateAfterTest().build();

	private PersistenceManagerFactory pmf = resource.getPersistenceManagerFactory();
	private PersistenceManager manager = resource.getPersistenceManager();
	private Session session = resource.getNativeSession();

	@Test public void should_create_resource_with_a_distinct_keyspace() throws Exception{AchillesResource resource=AchillesResourceBuilder.noEntityPackages("test_keyspace");final PersistenceManager manager=resource.getPersistenceManager();final Map<String, Object> map=manager.nativeQuery("SELECT count(1) FROM system.schema_keyspaces WHERE keyspace_name='test_keyspace'").first();assertThat(map.get("count")).isEqualTo(1L);}
}
