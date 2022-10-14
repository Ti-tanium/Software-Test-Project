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
package info.archinnov.achilles.internal.statement;

import static com.datastax.driver.core.querybuilder.Update.Conditions;
import static info.archinnov.achilles.internal.metadata.holder.PropertyType.ID;
import static info.archinnov.achilles.internal.metadata.holder.PropertyType.SIMPLE;
import static info.archinnov.achilles.internal.persistence.operations.CollectionAndMapChangeType.REMOVE_FROM_LIST_AT_INDEX;
import static info.archinnov.achilles.internal.persistence.operations.CollectionAndMapChangeType.SET_TO_LIST_AT_INDEX;
import static info.archinnov.achilles.test.builders.CompleteBeanTestBuilder.builder;
import static info.archinnov.achilles.test.builders.PropertyMetaTestBuilder.completeBean;
import static info.archinnov.achilles.type.Options.CASCondition;
import static java.util.Arrays.asList;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.querybuilder.Update;
import com.datastax.driver.core.querybuilder.Update.Where;
import com.google.common.collect.ImmutableMap;
import info.archinnov.achilles.internal.context.DaoContext;
import info.archinnov.achilles.internal.context.PersistenceContext;
import info.archinnov.achilles.internal.metadata.holder.EntityMeta;
import info.archinnov.achilles.internal.metadata.holder.PropertyMeta;
import info.archinnov.achilles.internal.metadata.holder.PropertyType;
import info.archinnov.achilles.internal.proxy.dirtycheck.DirtyCheckChangeSet;
import info.archinnov.achilles.internal.reflection.ReflectionInvoker;
import info.archinnov.achilles.internal.statement.wrapper.RegularStatementWrapper;
import info.archinnov.achilles.query.slice.CQLSliceQuery;
import info.archinnov.achilles.test.mapping.entity.ClusteredEntity;
import info.archinnov.achilles.test.mapping.entity.CompleteBean;
import info.archinnov.achilles.test.parser.entity.EmbeddedKey;
import info.archinnov.achilles.type.Pair;

@RunWith(MockitoJUnitRunner.class)
public class StatementGeneratorTest {

    @InjectMocks
    private StatementGenerator generator;

    @Mock
    private SliceQueryStatementGenerator sliceQueryGenerator;


    @Mock
    private CQLSliceQuery<ClusteredEntity> sliceQuery;

    @Mock
    private DaoContext daoContext;

    @Mock
    private PersistenceContext.StateHolderFacade context;

    @Mock
    private RegularStatementWrapper statementWrapper;

    @Mock
    private DirtyCheckChangeSet dirtyCheckChangeSet;

    @Captor
    private ArgumentCaptor<Select> selectCaptor;

    @Captor
    private ArgumentCaptor<Delete> deleteCaptor;

    private ReflectionInvoker invoker = new ReflectionInvoker();


    @Test public void should_generate_slice_select_query() throws Exception{EntityMeta meta=prepareEntityMeta("id","comp1","comp2");when(sliceQuery.getMeta()).thenReturn(meta);when(sliceQuery.getCQLOrdering()).thenReturn(QueryBuilder.desc("comp1"));when(sliceQuery.getConsistencyLevel()).thenReturn(com.datastax.driver.core.ConsistencyLevel.EACH_QUORUM);when(sliceQueryGenerator.generateWhereClauseForSelectSliceQuery(eq(sliceQuery),selectCaptor.capture())).thenReturn(statementWrapper);when(sliceQuery.getLimit()).thenReturn(98);when(sliceQuery.getBatchSize()).thenReturn(101);RegularStatementWrapper actual=generator.generateSelectSliceQuery(sliceQuery);assertThat(actual).isSameAs(statementWrapper);assertThat(selectCaptor.getValue().getQueryString()).isEqualTo("SELECT id,comp1,comp2,age,name,label FROM table ORDER BY comp1 DESC LIMIT 98;");assertThat(selectCaptor.getValue().getFetchSize()).isEqualTo(101);}

    private EntityMeta prepareEntityMeta(String... componentNames) throws Exception {
        PropertyMeta idMeta;
        if (componentNames.length > 1) {
            idMeta = completeBean(Void.class, EmbeddedKey.class).field("id")
                    .compNames(componentNames).type(PropertyType.EMBEDDED_ID).build();
        } else {
            idMeta = completeBean(Void.class, Long.class).field(componentNames[0]).type(ID)
                    .build();
        }

        PropertyMeta ageMeta = completeBean(Void.class, Long.class).field("age").type(SIMPLE)
                .build();

        PropertyMeta nameMeta = completeBean(Void.class, String.class).field("name")
                .type(SIMPLE).build();

        PropertyMeta labelMeta = completeBean(Void.class, String.class).field("label")
                .type(SIMPLE).build();

        EntityMeta meta = new EntityMeta();
        meta.setTableName("table");
        meta.setAllMetasExceptCounters(asList(idMeta, ageMeta, nameMeta, labelMeta));
        meta.setAllMetasExceptIdAndCounters(asList(ageMeta, nameMeta, labelMeta));
        meta.setIdMeta(idMeta);

        return meta;
    }

    private Update.Assignments update() {
        return QueryBuilder.update("table").with();
    }


}
