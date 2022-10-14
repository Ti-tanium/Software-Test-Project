/**
 * Copyright (c) 2012 Edgar Espina
 * This file is part of Handlebars.java.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jknack.handlebars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PropertyAccessTest {

  @Test public void listIndexOutOfBounds() throws IOException{Handlebars handlebars=new Handlebars();Template template=handlebars.compile("{{list.[10]}}");Map<String, Object> context=new HashMap<String, Object>();context.put("list",new ArrayList<String>(Arrays.asList("s1","s2")));try {assertEquals("s2",template.apply(context));fail("An " + IndexOutOfBoundsException.class.getName() + " is expected.");} catch (HandlebarsException ex){assertTrue(ex.getCause() instanceof IndexOutOfBoundsException);}}
}
