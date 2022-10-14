/*
 Copyright (C) 2015 Electronic Arts Inc.  All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1.  Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.
 2.  Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the distribution.
 3.  Neither the name of Electronic Arts, Inc. ("EA") nor the names of
     its contributors may be used to endorse or promote products derived
     from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY ELECTRONIC ARTS AND ITS CONTRIBUTORS "AS IS" AND ANY
 EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL ELECTRONIC ARTS OR ITS CONTRIBUTORS BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.ea.orbit.concurrent.test;

import com.ea.orbit.concurrent.Task;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaskTest
{
    private static class CTask<T> extends Task<T>
    {
        @Override
        public boolean complete(T value)
        {
            return super.internalComplete(value);
        }

        @Override
        public boolean completeExceptionally(Throwable ex)
        {
            return super.internalCompleteExceptionally(ex);
        }
    }

    @Test public void testAnyOfVariations(){CTask<Integer> t1=new CTask();CTask t2=new CTask();CTask t3=new CTask();CompletableFuture c4=new CompletableFuture();Task group_regular=CTask.anyOf(t1,t2,t3);Task group_array=CTask.anyOf(new CompletableFuture[]{t1,t2,t3});Task group_array2=CTask.anyOf(new CTask[]{t1,t2,t3});Task group_collection=CTask.anyOf(Arrays.asList(t1,t2,t3));Task group_stream=CTask.anyOf(Arrays.asList(t1,t2,t3).stream());Stream<CTask> stream=Arrays.asList(t1,t2,t3).stream();Task group_stream2=CTask.anyOf(stream);Task group_stream3=CTask.anyOf(Arrays.asList(c4).stream());Stream<CompletableFuture> stream4=Arrays.asList(t1,t2,t3,c4).stream();Task group_stream4=CTask.anyOf(stream4);Task group_stream5=CTask.anyOf(Arrays.asList(t1,t2,t3,c4).stream());t1.complete(1);c4.complete(4);assertTrue(group_regular.isDone());assertTrue(group_array.isDone());assertTrue(group_array2.isDone());assertTrue(group_stream.isDone());assertTrue(group_stream2.isDone());assertTrue(group_stream3.isDone());assertTrue(group_stream4.isDone());assertTrue(group_stream5.isDone());assertTrue(group_collection.isDone());}
}
