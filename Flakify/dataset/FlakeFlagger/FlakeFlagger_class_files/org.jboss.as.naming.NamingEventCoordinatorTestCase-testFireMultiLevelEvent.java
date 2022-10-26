/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

package org.jboss.as.naming;

import org.junit.Before;
import org.junit.Test;

import javax.naming.CompositeName;
import javax.naming.event.EventContext;
import javax.naming.event.NamespaceChangeListener;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.ObjectChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Basic naming event coordinator tests.
 *
 * @author John E. Bailey
 */
public class NamingEventCoordinatorTestCase {

    private NamingContext context;

    @Before
    public void setup() throws Exception {
        NamingContext.setActiveNamingStore(new InMemoryNamingStore());
        context = new NamingContext(null);
    }

    @Test public void testFireMultiLevelEvent() throws Exception{final NamingEventCoordinator coordinator=new NamingEventCoordinator();final CollectingListener subtreeListener=new CollectingListener(1);coordinator.addListener("foo",EventContext.SUBTREE_SCOPE,subtreeListener);final CollectingListener subtreeListenerTwo=new CollectingListener(1);coordinator.addListener("foo/bar",EventContext.SUBTREE_SCOPE,subtreeListenerTwo);final CollectingListener subtreeListenerThree=new CollectingListener(1);coordinator.addListener("foo/bar/baz",EventContext.SUBTREE_SCOPE,subtreeListenerThree);coordinator.fireEvent(context,new CompositeName("foo/bar/baz/boo"),null,null,NamingEvent.OBJECT_ADDED,"bind",EventContext.OBJECT_SCOPE,EventContext.ONELEVEL_SCOPE,EventContext.SUBTREE_SCOPE);subtreeListener.latch.await(1,TimeUnit.SECONDS);subtreeListenerTwo.latch.await(1,TimeUnit.SECONDS);subtreeListenerThree.latch.await(1,TimeUnit.SECONDS);assertEquals(1,subtreeListener.capturedEvents.size());assertEquals(1,subtreeListenerTwo.capturedEvents.size());assertEquals(1,subtreeListenerThree.capturedEvents.size());}

    private class CollectingListener implements ObjectChangeListener, NamespaceChangeListener {
        private final List<NamingEvent> capturedEvents = new ArrayList<NamingEvent>();

        private final CountDownLatch latch;

        CollectingListener(int expectedEvents) {
            latch = new CountDownLatch(expectedEvents);
        }

        @Override
        public void objectChanged(NamingEvent evt) {
            captured(evt);
        }

        @Override
        public void objectAdded(NamingEvent evt) {
            captured(evt);
        }

        @Override
        public void objectRemoved(NamingEvent evt) {
            captured(evt);
        }

        @Override
        public void objectRenamed(NamingEvent evt) {
            captured(evt);
        }

        private void captured(final NamingEvent event) {
            capturedEvents.add(event);
            latch.countDown();
        }

        @Override
        public void namingExceptionThrown(NamingExceptionEvent evt) {
        }
    }
}