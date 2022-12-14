/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
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

package org.wildfly.clustering.web.undertow.sso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import io.undertow.security.api.AuthenticatedSessionManager.AuthenticatedSession;
import io.undertow.security.idm.Account;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.session.Session;
import io.undertow.server.session.SessionManager;
import org.junit.Test;
import org.wildfly.clustering.ee.Batch;
import org.wildfly.clustering.ee.BatchContext;
import org.wildfly.clustering.ee.Batcher;
import org.wildfly.clustering.web.sso.SSO;
import org.wildfly.clustering.web.sso.Sessions;

/**
 * Unit test for {@link DistributableSingleSignOn}
 *
 * @author Paul Ferraro
 */
public class DistributableSingleSignOnTestCase {

    private final SSO<AuthenticatedSession, String, String, Void> sso = mock(SSO.class);
    private final SessionManagerRegistry registry = mock(SessionManagerRegistry.class);
    private final Batcher<Batch> batcher = mock(Batcher.class);
    private final Batch batch = mock(Batch.class);
    private final InvalidatableSingleSignOn subject = new DistributableSingleSignOn(this.sso, this.registry, this.batcher, this.batch);

    @Test
    public void iterator() {
        BatchContext context = mock(BatchContext.class);
        Sessions<String, String> sessions = mock(Sessions.class);
        SessionManager manager = mock(SessionManager.class);
        Session session = mock(Session.class);
        String deployment = "deployment";
        String sessionId = "session";

        when(this.batcher.resumeBatch(this.batch)).thenReturn(context);
        when(this.sso.getSessions()).thenReturn(sessions);
        when(sessions.getDeployments()).thenReturn(Collections.singleton(deployment));
        when(sessions.getSession(deployment)).thenReturn(sessionId);
        when(this.registry.getSessionManager(deployment)).thenReturn(manager);
        when(manager.getSession(sessionId)).thenReturn(session);
        when(session.getId()).thenReturn(sessionId);

        Iterator<Session> results = this.subject.iterator();

        assertTrue(results.hasNext());
        Session result = results.next();
        assertEquals(session.getId(), result.getId());
        assertFalse(results.hasNext());

        verifyZeroInteractions(this.batch);
        verify(context).close();

        // Validate that returned sessions can be invalidated
        HttpServerExchange exchange = new HttpServerExchange(null);
        Session mutableSession = mock(Session.class);

        when(session.getSessionManager()).thenReturn(manager);
        when(manager.getSession(same(exchange), any())).thenReturn(mutableSession);

        result.invalidate(exchange);

        verify(mutableSession).invalidate(same(exchange));
        verifyZeroInteractions(this.batch);
        verifyNoMoreInteractions(context);
    }
}
