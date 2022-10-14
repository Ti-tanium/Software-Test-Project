/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.as.ejb3.timer.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.ScheduleExpression;

import org.jboss.as.ejb3.timerservice.schedule.CalendarBasedTimeout;
import org.junit.Assert;
import org.junit.Test;

/**
 * CalendarBasedTimeoutTestCase
 *
 * @author Jaikiran Pai
 * @author Eduardo Martins
 * @author "<a href=\"mailto:wfink@redhat.com\">Wolf-Dieter Fink</a>"
 */
public class CalendarBasedTimeoutTestCase {

    /**
     * Logger
     */
//    private static Logger logger = Logger.getLogger(CalendarBasedTimeoutTestCase.class);

    /**
     * The timezone which is in use
     */
    private TimeZone timezone;
    private String timeZoneDisplayName;

    /**
     * This method returns a collection of all available timezones in the system.
     * The tests in this {@link CalendarBasedTimeoutTestCase} will then be run
     * against each of these timezones
     */
    private static List<TimeZone> getTimezones() {
        String[] candidates = TimeZone.getAvailableIDs();
        List<TimeZone> timeZones = new ArrayList<TimeZone>(candidates.length);
        for (String timezoneID : candidates) {
            TimeZone timeZone = TimeZone.getTimeZone(timezoneID);
            boolean different = true;
            for (int i = timeZones.size() - 1; i >= 0; i--)  {
                TimeZone testee = timeZones.get(i);
                if (testee.hasSameRules(timeZone)) {
                    different = false;
                    break;
                }
            }
            if (different) {
                timeZones.add(timeZone);
            }
        }
        return timeZones;
    }

    /**
	 * Change CEST summer time to CEST winter time. The timer should be fired every 15 minutes (absolutely). The calendar time will jump from 3:00CEST back to 2:00CET but the timer must run within 2:00-3:00 CEST and 2:00-3:00CET! The test should be run similar in any OS/JVM default timezone This is a test for WFLY-9537
	 */@Test public void testChangeCEST2CET(){Calendar start=new GregorianCalendar(TimeZone.getTimeZone("Europe/Berlin"));start.clear();start.set(2017,Calendar.OCTOBER,29,1,30,0);ScheduleExpression schedule=new ScheduleExpression();schedule.hour("*").minute("5/15").second("0").timezone("Europe/Berlin").start(start.getTime());CalendarBasedTimeout calendarTimeout=new CalendarBasedTimeout(schedule);Calendar firstTimeout=calendarTimeout.getFirstTimeout();Assert.assertNotNull(firstTimeout);if (firstTimeout.get(Calendar.YEAR) != 2017 || firstTimeout.get(Calendar.MONTH) != Calendar.OCTOBER || firstTimeout.get(Calendar.DAY_OF_MONTH) != 29 || firstTimeout.get(Calendar.HOUR_OF_DAY) != 1 || firstTimeout.get(Calendar.MINUTE) != 35 || firstTimeout.get(Calendar.SECOND) != 0 || firstTimeout.get(Calendar.DST_OFFSET) != 3600000){Assert.fail("Start time unexpected : " + firstTimeout.toString());}Calendar current=firstTimeout;for (int i=0;i < 7;i++){Calendar next=calendarTimeout.getNextTimeout(current);if (current.getTimeInMillis() != (next.getTimeInMillis() - 900000)){Assert.fail("Schedule is more than 15 minutes from " + current.getTime() + " to " + next.getTime());}current=next;}if (current.get(Calendar.YEAR) != 2017 || current.get(Calendar.MONTH) != Calendar.OCTOBER || current.get(Calendar.DAY_OF_MONTH) != 29 || current.get(Calendar.HOUR_OF_DAY) != 2 || current.get(Calendar.MINUTE) != 20 || current.get(Calendar.DST_OFFSET) != 0){Assert.fail("End time unexpected : " + current.toString());}}

    private ScheduleExpression getTimezoneSpecificScheduleExpression() {
        ScheduleExpression scheduleExpression = new ScheduleExpression().timezone(this.timezone.getID());
        GregorianCalendar start = new GregorianCalendar(this.timezone);
        start.clear();
        start.set(2014,0,1,1,0,0);
        return scheduleExpression.start(start.getTime());
    }

    private boolean isLeapYear(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        return year % 4 == 0;
    }

    private boolean isWeekDay(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        switch (dayOfWeek) {
            case Calendar.SATURDAY:
            case Calendar.SUNDAY:
                return false;
            default:
                return true;
        }
    }

}
