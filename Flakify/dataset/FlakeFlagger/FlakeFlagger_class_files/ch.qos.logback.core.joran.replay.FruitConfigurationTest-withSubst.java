/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.core.joran.replay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

import ch.qos.logback.core.joran.spi.ElementSelector;
import org.junit.Test;

import ch.qos.logback.core.joran.SimpleConfigurator;
import ch.qos.logback.core.joran.action.Action;
import ch.qos.logback.core.joran.action.NOPAction;
import ch.qos.logback.core.util.CoreTestConstants;
import ch.qos.logback.core.util.StatusPrinter;

/** 
 * The Fruit* code is intended to test Joran's replay capability
 * */
public class FruitConfigurationTest  {

  FruitContext fruitContext = new FruitContext();

  @Test public void withSubst() throws Exception{List<FruitShell> fsList=doFirstPart("fruitWithSubst.xml");assertNotNull(fsList);assertEquals(1,fsList.size());FruitShell fs0=fsList.get(0);assertNotNull(fs0);assertEquals("fs0",fs0.getName());int oldCount=FruitFactory.count;Fruit fruit0=fs0.fruitFactory.buildFruit();assertTrue(fruit0 instanceof WeightytFruit);assertEquals("orange-" + oldCount,fruit0.getName());assertEquals(1.2,((WeightytFruit)fruit0).getWeight(),0.01);}

}
