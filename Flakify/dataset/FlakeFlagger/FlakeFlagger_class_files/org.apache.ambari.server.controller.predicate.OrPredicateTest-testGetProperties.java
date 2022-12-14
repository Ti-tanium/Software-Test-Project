/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ambari.server.controller.predicate;

import junit.framework.Assert;
import org.apache.ambari.server.controller.internal.ResourceImpl;
import org.apache.ambari.server.controller.spi.Resource;
import org.apache.ambari.server.controller.utilities.PropertyHelper;
import org.junit.Test;

import java.util.Set;

/**
 *
 */
public class OrPredicateTest {

  @Test
  public void testGetProperties() {
    String propertyId1 = PropertyHelper.getPropertyId("category1", "property1");
    String propertyId2 = PropertyHelper.getPropertyId("category1", "property2");
    String propertyId3 = PropertyHelper.getPropertyId("category1", "property3");

    EqualsPredicate predicate1 = new EqualsPredicate<String>(propertyId1, "v1");
    EqualsPredicate predicate2 = new EqualsPredicate<String>(propertyId2, "v2");
    EqualsPredicate predicate3 = new EqualsPredicate<String>(propertyId3, "v3");

    OrPredicate orPredicate = new OrPredicate(predicate1, predicate2, predicate3);

    Set<String> ids = orPredicate.getPropertyIds();

    Assert.assertEquals(3, ids.size());
    Assert.assertTrue(ids.contains(propertyId1));
    Assert.assertTrue(ids.contains(propertyId2));
    Assert.assertTrue(ids.contains(propertyId3));
  }

}
