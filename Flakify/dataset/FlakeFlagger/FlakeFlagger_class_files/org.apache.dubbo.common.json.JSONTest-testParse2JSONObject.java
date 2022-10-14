/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.common.json;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Deprecated
public class JSONTest {
    static byte[] DEFAULT_BYTES = {3, 12, 14, 41, 12, 2, 3, 12, 4, 67, 23};
    static int DEFAULT_$$ = 152;

    @Test public void testParse2JSONObject() throws Exception{JSONObject jo=(JSONObject)JSON.parse("{name:'qianlei',array:[1,2,3,4,98.123],b1:TRUE,$1:NULL,$2:FALSE,__3:NULL}");assertEquals(jo.getString("name"),"qianlei");assertEquals(jo.getArray("array").length(),5);assertEquals(jo.get("$2"),Boolean.FALSE);assertEquals(jo.get("__3"),null);for (int i=0;i < 10000;i++)JSON.parse("{\"name\":\"qianlei\",\"array\":[1,2,3,4,98.123],\"displayName\":\"钱磊\"}");long now=System.currentTimeMillis();for (int i=0;i < 10000;i++)JSON.parse("{\"name\":\"qianlei\",\"array\":[1,2,3,4,98.123],\"displayName\":\"钱磊\"}");System.out.println("parse to JSONObject 10000 times in: " + (System.currentTimeMillis() - now));}

    public static class Bean1 {
        public int[] array;
        private String name, displayName;

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Bean {
        public int[] array;
        public boolean $b;
        public int $$ = DEFAULT_$$;
        public byte[] bytes = DEFAULT_BYTES;
        private String name, displayName = "钱磊";

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}