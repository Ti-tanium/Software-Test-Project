/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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
package org.wildfly.clustering.ejb.infinispan.group;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.wildfly.clustering.ee.Mutator;
import org.wildfly.clustering.ee.Remover;
import org.wildfly.clustering.ejb.PassivationListener;
import org.wildfly.clustering.ejb.infinispan.BeanGroup;
import org.wildfly.clustering.ejb.infinispan.BeanGroupEntry;
import org.wildfly.clustering.marshalling.jboss.MarshallingContext;
import org.wildfly.clustering.marshalling.spi.MarshalledValue;

public class InfinispanBeanGroupTestCase {
    private String id;
    private BeanGroupEntry<String, Object> entry = mock(BeanGroupEntry.class);
    private MarshallingContext context = mock(MarshallingContext.class);
    private Mutator mutator = mock(Mutator.class);
    private Remover<String> remover = mock(Remover.class);

    private BeanGroup<String, Object> group = new InfinispanBeanGroup<>(this.id, this.entry, this.context, this.mutator, this.remover);

    @Test
    public void close() throws ClassNotFoundException, IOException {
        MarshalledValue<Map<String, Object>, MarshallingContext> value = mock(MarshalledValue.class);

        when(this.entry.getBeans()).thenReturn(value);
        when(value.get(this.context)).thenReturn(Collections.<String, Object>emptyMap());

        this.group.close();

        verify(this.remover).remove(this.id);
        verify(this.mutator, never()).mutate();

        reset(this.remover, this.mutator);

        when(value.get(this.context)).thenReturn(Collections.singletonMap("id", new Object()));

        this.group.close();

        verify(this.mutator).mutate();
        verify(this.remover, never()).remove(this.id);
    }
}
