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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.core.status.StatusChecker;
import org.apache.log4j.MDC;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.db.DriverManagerConnectionSource;
import ch.qos.logback.core.testUtil.RandomUtil;
import ch.qos.logback.core.util.StatusPrinter;

public class DBAppenderH2Test {

  LoggerContext loggerContext = new LoggerContext();;
  Logger logger;
  DBAppender appender;
  DriverManagerConnectionSource connectionSource;
  DBAppenderH2TestFixture dbAppenderH2TestFixture;
  int diff = RandomUtil.getPositiveInt();
  StatusChecker checker = new StatusChecker(loggerContext);

  @Before
  public void setUp() throws SQLException {
    dbAppenderH2TestFixture = new DBAppenderH2TestFixture();
    dbAppenderH2TestFixture.setUp();
    loggerContext.setName("default");
    logger = loggerContext.getLogger("root");
    appender = new DBAppender();
    appender.setName("DB");
    appender.setContext(loggerContext);
    connectionSource = new DriverManagerConnectionSource();
    connectionSource.setContext(loggerContext);
    connectionSource.setDriverClass(DBAppenderH2TestFixture.H2_DRIVER_CLASS);
    connectionSource.setUrl(dbAppenderH2TestFixture.url);
    System.out.println("cs.url=" + dbAppenderH2TestFixture.url);
    connectionSource.setUser(dbAppenderH2TestFixture.user);
    connectionSource.setPassword(dbAppenderH2TestFixture.password);


    connectionSource.start();
    appender.setConnectionSource(connectionSource);
    appender.start();
  }

  @Test public void testAppendLoggingEvent() throws SQLException{ILoggingEvent event=createLoggingEvent();appender.append(event);StatusPrinter.print(loggerContext);Statement stmt=connectionSource.getConnection().createStatement();ResultSet rs=null;rs=stmt.executeQuery("SELECT * FROM logging_event");if (rs.next()){assertEquals(event.getTimeStamp(),rs.getLong(DBAppender.TIMESTMP_INDEX));assertEquals(event.getFormattedMessage(),rs.getString(DBAppender.FORMATTED_MESSAGE_INDEX));assertEquals(event.getLoggerName(),rs.getString(DBAppender.LOGGER_NAME_INDEX));assertEquals(event.getLevel().toString(),rs.getString(DBAppender.LEVEL_STRING_INDEX));assertEquals(event.getThreadName(),rs.getString(DBAppender.THREAD_NAME_INDEX));assertEquals(DBHelper.computeReferenceMask(event),rs.getShort(DBAppender.REFERENCE_FLAG_INDEX));assertEquals(String.valueOf(diff),rs.getString(DBAppender.ARG0_INDEX));StackTraceElement callerData=event.getCallerData()[0];assertEquals(callerData.getFileName(),rs.getString(DBAppender.CALLER_FILENAME_INDEX));assertEquals(callerData.getClassName(),rs.getString(DBAppender.CALLER_CLASS_INDEX));assertEquals(callerData.getMethodName(),rs.getString(DBAppender.CALLER_METHOD_INDEX));} else {fail("No row was inserted in the database");}rs.close();stmt.close();}

  private LoggingEvent createLoggingEvent(String msg, Object[] args) {
    return new LoggingEvent(this.getClass().getName(), logger,
            Level.DEBUG, msg, new Exception("test Ex"), args);
  }


  private LoggingEvent createLoggingEvent() {
    return createLoggingEvent("test message", new Integer[]{diff});
  }


}
