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
package info.archinnov.achilles.internal.metadata.parsing;

import static info.archinnov.achilles.schemabuilder.Create.Options.ClusteringOrder.Sorting.DESC;
import static org.fest.assertions.api.Assertions.assertThat;
import java.lang.reflect.Method;
import java.util.UUID;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import info.archinnov.achilles.exception.AchillesBeanMappingException;
import info.archinnov.achilles.internal.metadata.holder.EmbeddedIdProperties;
import info.archinnov.achilles.test.parser.entity.CorrectEmbeddedKey;
import info.archinnov.achilles.test.parser.entity.CorrectEmbeddedReversedKey;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyAsCompoundPartitionKey;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyChild1;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyChild3;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyIncorrectType;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyNotInstantiable;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyWithCompoundPartitionKey;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyWithDuplicateOrder;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyWithInconsistentCompoundPartitionKey;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyWithNegativeOrder;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyWithNoAnnotation;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyWithOnlyOneComponent;
import info.archinnov.achilles.test.parser.entity.EmbeddedKeyWithTimeUUID;

@RunWith(MockitoJUnitRunner.class)
public class EmbeddedIdParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @InjectMocks
    private EmbeddedIdParser parser;

    @Test public void should_parse_embedded_id_with_compound_partition_key() throws Exception{EmbeddedIdProperties props=parser.parseEmbeddedId(EmbeddedKeyWithCompoundPartitionKey.class);assertThat(props.isCompositePartitionKey()).isTrue();assertThat(props.getPartitionComponentNames()).containsExactly("id","type");assertThat(props.getPartitionComponentClasses()).containsExactly(Long.class,String.class);assertThat(props.getClusteringComponentNames()).containsExactly("date");assertThat(props.getClusteringComponentClasses()).containsExactly(UUID.class);assertThat(props.getComponentClasses()).containsExactly(Long.class,String.class,UUID.class);assertThat(props.getComponentNames()).containsExactly("id","type","date");assertThat(props.getComponentGetters()).hasSize(3);assertThat(props.getComponentSetters()).hasSize(3);}

}
