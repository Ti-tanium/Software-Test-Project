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
package info.archinnov.achilles.internal.metadata.parsing.validator;

import static info.archinnov.achilles.type.ConsistencyLevel.ALL;
import static info.archinnov.achilles.type.ConsistencyLevel.ANY;
import static org.mockito.Mockito.when;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import info.archinnov.achilles.annotations.EmbeddedId;
import info.archinnov.achilles.annotations.Id;
import info.archinnov.achilles.annotations.Index;
import info.archinnov.achilles.exception.AchillesBeanMappingException;
import info.archinnov.achilles.internal.metadata.holder.PropertyMeta;
import info.archinnov.achilles.internal.metadata.parsing.context.PropertyParsingContext;
import info.archinnov.achilles.test.mapping.entity.CompleteBean;
import info.archinnov.achilles.test.parser.entity.CorrectEmbeddedKey;
import info.archinnov.achilles.type.ConsistencyLevel;
import info.archinnov.achilles.type.Pair;

@RunWith(MockitoJUnitRunner.class)
public class PropertyParsingValidatorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private PropertyParsingValidator validator = new PropertyParsingValidator();

    @Mock
    private PropertyParsingContext context;

    @Test public void should_exception_when_index_type_not_allowed() throws Exception{class Test {@Index public Map<?, ?> firstName;}Field nameField=Test.class.getField("firstName");Map<String, PropertyMeta> propertyMetas=new HashMap<String, PropertyMeta>();propertyMetas.put("firstName",null);when(context.getCurrentField()).thenReturn(nameField);when(context.getCurrentPropertyName()).thenReturn("firstName");when(context.getPropertyMetas()).thenReturn(propertyMetas);when(context.<Test>getCurrentEntityClass()).thenReturn(Test.class);exception.expect(AchillesBeanMappingException.class);exception.expectMessage("Property 'firstName' of entity 'null' cannot be indexed because the type 'java.util.Map' is not supported");validator.validateIndexIfSet(context);}

    public static enum CustomEnum {
        ONE, TWO, THREE
    }
}