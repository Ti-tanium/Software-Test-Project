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
package org.apache.dubbo.common.serialize.fst;

import org.apache.dubbo.common.serialize.fst.model.AnimalEnum;
import org.apache.dubbo.common.serialize.fst.model.FullAddress;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class FstObjectOutputTest {
    private FstObjectOutput fstObjectOutput;
    private FstObjectInput fstObjectInput;
    private ByteArrayOutputStream byteArrayOutputStream;
    private ByteArrayInputStream byteArrayInputStream;

    @Before
    public void setUp() {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.fstObjectOutput = new FstObjectOutput(byteArrayOutputStream);
    }


    @Test
    public void testWriteDouble() throws IOException {
        this.fstObjectOutput.writeDouble(-1.66d);
        this.flushToInput();

        Double result = this.fstObjectInput.readDouble();
        assertThat(result, is(-1.66d));
    }


    private void flushToInput() throws IOException {
        this.fstObjectOutput.flushBuffer();
        this.byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        this.fstObjectInput = new FstObjectInput(byteArrayInputStream);
    }
}