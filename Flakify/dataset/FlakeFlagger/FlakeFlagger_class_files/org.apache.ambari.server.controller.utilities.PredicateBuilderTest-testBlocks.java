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
package org.apache.ambari.server.controller.utilities;

import junit.framework.Assert;
import org.apache.ambari.server.controller.internal.ResourceImpl;
import org.apache.ambari.server.controller.spi.Predicate;
import org.apache.ambari.server.controller.spi.Resource;
import org.junit.Test;

/**
 *
 */
public class PredicateBuilderTest {

  @Test public void testBlocks(){String p1=PropertyHelper.getPropertyId("cat1","prop1");String p2=PropertyHelper.getPropertyId("cat1","prop2");String p3=PropertyHelper.getPropertyId("cat1","prop3");Resource resource=new ResourceImpl(Resource.Type.Cluster);resource.setProperty(p1,"foo");resource.setProperty(p2,"bar");resource.setProperty(p3,"cat");PredicateBuilder pb1=new PredicateBuilder();Predicate predicate1=pb1.begin().property(p1).equals("foo").and().property(p2).equals("bar").end().or().property(p3).equals("cat").toPredicate();Assert.assertTrue(predicate1.evaluate(resource));PredicateBuilder pb2=new PredicateBuilder();Predicate predicate2=pb2.begin().property(p1).equals("foo").and().property(p2).equals("bat").end().or().property(p3).equals("cat").toPredicate();Assert.assertTrue(predicate2.evaluate(resource));PredicateBuilder pb3=new PredicateBuilder();Predicate predicate3=pb3.begin().property(p1).equals("foo").and().property(p2).equals("bar").end().or().property(p3).equals("can").toPredicate();Assert.assertTrue(predicate3.evaluate(resource));PredicateBuilder pb4=new PredicateBuilder();Predicate predicate4=pb4.begin().property(p1).equals("foo").and().property(p2).equals("bat").end().or().property(p3).equals("can").toPredicate();Assert.assertFalse(predicate4.evaluate(resource));PredicateBuilder pb5=new PredicateBuilder();Predicate predicate5=pb5.property(p1).equals("foo").and().begin().property(p2).equals("bar").or().property(p3).equals("cat").end().toPredicate();Assert.assertTrue(predicate5.evaluate(resource));PredicateBuilder pb6=new PredicateBuilder();Predicate predicate6=pb6.property(p1).equals("foo").and().begin().property(p2).equals("bat").or().property(p3).equals("cat").end().toPredicate();Assert.assertTrue(predicate6.evaluate(resource));PredicateBuilder pb7=new PredicateBuilder();Predicate predicate7=pb7.property(p1).equals("foo").and().begin().property(p2).equals("bat").or().property(p3).equals("can").end().toPredicate();Assert.assertFalse(predicate7.evaluate(resource));PredicateBuilder pb8=new PredicateBuilder();Predicate predicate8=pb8.property(p1).equals("fat").and().begin().property(p2).equals("bar").or().property(p3).equals("cat").end().toPredicate();Assert.assertFalse(predicate8.evaluate(resource));PredicateBuilder pb9=new PredicateBuilder();Predicate predicate9=pb9.property(p1).equals("foo").and().not().begin().property(p2).equals("bar").or().property(p3).equals("cat").end().toPredicate();Assert.assertFalse(predicate9.evaluate(resource));PredicateBuilder pb10=new PredicateBuilder();Predicate predicate10=pb10.property(p1).equals("foo").and().not().begin().property(p2).equals("bat").or().property(p3).equals("car").end().toPredicate();Assert.assertTrue(predicate10.evaluate(resource));}
}
