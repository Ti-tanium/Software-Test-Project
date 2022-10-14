/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dubbo.config;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public class ModuleConfigTest {
    @Test
    public void testName2() throws Exception {
        ModuleConfig module = new ModuleConfig();
        module.setName("module-name");
        assertThat(module.getName(), equalTo("module-name"));
        assertThat(module.getId(), equalTo("module-name"));
        Map<String, String> parameters = new HashMap<String, String>();
        ModuleConfig.appendParameters(parameters, module);
        assertThat(parameters, hasEntry("module", "module-name"));
    }
}
