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
package info.archinnov.achilles.query.slice;

import static info.archinnov.achilles.schemabuilder.Create.Options.ClusteringOrder;
import static info.archinnov.achilles.schemabuilder.Create.Options.ClusteringOrder.Sorting;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;
import info.archinnov.achilles.internal.metadata.holder.EntityMeta;
import info.archinnov.achilles.internal.metadata.holder.PropertyMeta;
import info.archinnov.achilles.exception.AchillesException;
import info.archinnov.achilles.type.BoundingMode;
import info.archinnov.achilles.type.OrderingMode;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class SliceQueryValidatorTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private SliceQueryValidator validator = new SliceQueryValidator();

	@Mock
	private PropertyMeta idMeta;

	@Mock
	private EntityMeta meta;

	private SliceQuery<Object> sliceQuery;

	@Before
	public void setUp() {
		when(meta.getIdMeta()).thenReturn(idMeta);
		when(idMeta.encodeToComponents(anyListOf(Object.class))).thenAnswer(new Answer<List<Object>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<Object> answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (List<Object>) args[0];
			}
		});
        when(idMeta.getClusteringOrders()).thenReturn(Arrays.asList(new ClusteringOrder("clust", Sorting.DESC)));
	}

    @Test public void should_exception_when_components_not_in_correct_order_for_descending() throws Exception{List<Object> clusteringsFrom=Arrays.<Object>asList(11);List<Object> clusteringsTo=Arrays.<Object>asList(12);OrderingMode ordering=OrderingMode.ASCENDING;sliceQuery=new SliceQuery<>(Object.class,meta,Arrays.<Object>asList(10L),clusteringsFrom,clusteringsTo,ordering,BoundingMode.INCLUSIVE_BOUNDS,null,100,100,false);exception.expect(AchillesException.class);exception.expectMessage("For slice query with descending order, start clustering last component should be 'greater or equal' to end clustering last component: [[11],[12]");validator.validateComponentsForSliceQuery(sliceQuery);}

}
