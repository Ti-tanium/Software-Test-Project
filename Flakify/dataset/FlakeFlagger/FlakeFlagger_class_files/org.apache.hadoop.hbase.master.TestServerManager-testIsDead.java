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
package org.apache.hadoop.hbase.master;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;


public class TestServerManager {
  @Test public void testIsDead(){Set<String> deadServers=new HashSet<String>();final String hostname123="one,123,3";assertFalse(ServerManager.isDead(deadServers,hostname123,false));assertFalse(ServerManager.isDead(deadServers,hostname123,true));deadServers.add(hostname123);assertTrue(ServerManager.isDead(deadServers,hostname123,false));assertFalse(ServerManager.isDead(deadServers,"one:1",true));assertFalse(ServerManager.isDead(deadServers,"one:1234",true));assertTrue(ServerManager.isDead(deadServers,"one:123",true));}
}