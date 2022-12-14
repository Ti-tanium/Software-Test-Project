/*
 * Copyright 2018 Alfresco, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.activiti.engine.impl.repository;

import java.io.IOException;
import java.io.InputStream;

import org.activiti.engine.ActivitiException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class DeploymentBuilderImplTest {

    @Spy
    @InjectMocks
    private DeploymentBuilderImpl deploymentBuilder;

    @Mock(answer = Answers.RETURNS_MOCKS)
    private Resource resource;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test public void addInputStreamShouldAddZipInputStreamWhenItsAZipLike(){doReturn(deploymentBuilder).when(deploymentBuilder).addZipInputStream(any());deploymentBuilder.addInputStream("my.bar",resource);verify(deploymentBuilder).addZipInputStream(any());}
}