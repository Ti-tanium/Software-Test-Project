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

package com.ea.orbit.actors.mongodb.test;


import com.ea.orbit.actors.OrbitStage;
import com.ea.orbit.actors.providers.mongodb.MongoDBStorageProvider;
import com.ea.orbit.actors.test.FakeClusterPeer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import static org.junit.Assert.assertEquals;

public class MongodbPersistenceTest
{
    private String clusterName = "cluster." + Math.random();
    private DB database;

    @Test public void checkWritesTest() throws Exception{OrbitStage stage1=createStage();assertEquals(0,database.getCollection("ISomeMatch").count());ISomeMatch someMatch=stage1.getReference(ISomeMatch.class,"300");ISomePlayer somePlayer=stage1.getReference(ISomePlayer.class,"101");someMatch.addPlayer(somePlayer).get();assertEquals(1,database.getCollection("ISomeMatch").count());}

    @Before
    public void setup() throws Exception
    {
        MongoClient client = new MongoClient();
        String databaseName = "test_" + MongodbPersistenceTest.class.getSimpleName() + "_" + System.currentTimeMillis() + "_" + System.nanoTime();
        database = client.getDB(databaseName);
    }
}
