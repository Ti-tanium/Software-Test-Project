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
package ch.qos.logback.classic.db;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import ch.qos.logback.classic.db.names.DBNameResolver;
import ch.qos.logback.classic.db.names.DefaultDBNameResolver;
import ch.qos.logback.classic.db.names.SimpleDBNameResolver;

/**
 * @author Tomasz Nurkiewicz
 * @since 2010-03-22
 */
public class SQLBuilderTest {

  @Test public void shouldReturnDefaultSqlInsertLoggingPropertyQuery() throws Exception{DBNameResolver nameResolver=new DefaultDBNameResolver();String sql=SQLBuilder.buildInsertPropertiesSQL(nameResolver);final String expected="INSERT INTO logging_event_property (event_id, mapped_key, mapped_value) VALUES (?, ?, ?)";assertThat(sql).isEqualTo(expected);}


  private DBNameResolver createSimpleDBNameResolver() {
    final SimpleDBNameResolver nameResolver = new SimpleDBNameResolver();
    nameResolver.setTableNamePrefix("tp_");
    nameResolver.setTableNameSuffix("_ts");
    nameResolver.setColumnNamePrefix("cp_");
    nameResolver.setColumnNameSuffix("_cs");
    return nameResolver;
  }

}
