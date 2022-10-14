package com.github.jknack.handlebars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class JsonNodeValueResolverTest {

  @Test
  public void resolveArrayNode() throws IOException {
    Handlebars handlebars = new Handlebars();

    Object item = new Object() {
      @SuppressWarnings("unused")
      public String getKey() {
        return "pojo";
      }
    };

    Map<String, Object> root = new HashMap<String, Object>();
    root.put("array", new Object[]{item });

    assertEquals("pojo", handlebars.compile("{{array.[0].key}}").apply(context(root)));
    assertEquals("pojo", handlebars.compile("{{#array}}{{key}}{{/array}}").apply(context(root)));
  }
}
