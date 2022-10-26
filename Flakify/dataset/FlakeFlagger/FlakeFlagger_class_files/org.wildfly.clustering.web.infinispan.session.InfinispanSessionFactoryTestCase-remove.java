/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
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

package org.wildfly.clustering.web.infinispan.session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.wildfly.clustering.web.LocalContextFactory;
import org.wildfly.clustering.web.session.ImmutableSession;
import org.wildfly.clustering.web.session.ImmutableSessionAttributes;
import org.wildfly.clustering.web.session.ImmutableSessionMetaData;
import org.wildfly.clustering.web.session.Session;

/**
 * Unit test for {@link InfinispanSessionFactory}.
 *
 * @author Paul Ferraro
 */
public class InfinispanSessionFactoryTestCase {
    private final SessionMetaDataFactory<InfinispanSessionMetaData<Object>, Object> metaDataFactory = mock(SessionMetaDataFactory.class);
    private final SessionAttributesFactory<Object> attributesFactory = mock(SessionAttributesFactory.class);
    private final LocalContextFactory<Object> localContextFactory = mock(LocalContextFactory.class);

    private final SessionFactory<InfinispanSessionMetaData<Object>, Object, Object> factory = new InfinispanSessionFactory<>(this.metaDataFactory, this.attributesFactory, this.localContextFactory);

    @Test
    public void remove() {
        String id = "id";

        when(this.metaDataFactory.remove(id)).thenReturn(false);

        this.factory.remove(id);

        verify(this.attributesFactory, never()).remove(id);

        when(this.metaDataFactory.remove(id)).thenReturn(true);

        this.factory.remove(id);

        verify(this.attributesFactory).remove(id);
    }
}