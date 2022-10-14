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
package org.apache.dubbo.rpc.protocol.thrift;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.Channel;
import org.apache.dubbo.remoting.buffer.ChannelBuffer;
import org.apache.dubbo.remoting.buffer.ChannelBuffers;
import org.apache.dubbo.remoting.exchange.Request;
import org.apache.dubbo.remoting.exchange.Response;
import org.apache.dubbo.remoting.exchange.support.DefaultFuture;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.RpcInvocation;
import org.apache.dubbo.rpc.RpcResult;
import org.apache.dubbo.rpc.gen.thrift.Demo;
import org.apache.dubbo.rpc.protocol.thrift.io.RandomAccessByteArrayOutputStream;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMessage;
import org.apache.thrift.protocol.TMessageType;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class ThriftCodecTest {

    private ThriftCodec codec = new ThriftCodec();
    private Channel channel = new MockedChannel(URL.valueOf("thrift://127.0.0.1"));

    static byte[] encodeFrame(byte[] content) {
        byte[] result = new byte[4 + content.length];
        TFramedTransport.encodeFrameSize(content.length, result);
        System.arraycopy(content, 0, result, 4, content.length);
        return result;
    }

    @Test public void testEncodeExceptionResponse() throws Exception{URL url=URL.valueOf(ThriftProtocol.NAME + "://127.0.0.1:40880/" + Demo.Iface.class.getName());Channel channel=new MockedChannel(url);Request request=createRequest();RpcResult rpcResult=new RpcResult();String exceptionMessage="failed";rpcResult.setException(new RuntimeException(exceptionMessage));Response response=new Response();response.setResult(rpcResult);response.setId(request.getId());ChannelBuffer bos=ChannelBuffers.dynamicBuffer(1024);ThriftCodec.RequestData rd=ThriftCodec.RequestData.create(ThriftCodec.getSeqId(),Demo.Iface.class.getName(),"echoString");ThriftCodec.cachedRequest.put(request.getId(),rd);codec.encode(channel,bos,response);byte[] buf=new byte[bos.writerIndex() - 4];System.arraycopy(bos.array(),4,buf,0,bos.writerIndex() - 4);ByteArrayInputStream bis=new ByteArrayInputStream(buf);if (bis.markSupported()){bis.mark(0);}TIOStreamTransport transport=new TIOStreamTransport(bis);TBinaryProtocol protocol=new TBinaryProtocol(transport);Assert.assertEquals(ThriftCodec.MAGIC,protocol.readI16());Assert.assertEquals(protocol.readI32() + 4,bos.writerIndex());int headerLength=protocol.readI16();Assert.assertEquals(ThriftCodec.VERSION,protocol.readByte());Assert.assertEquals(Demo.Iface.class.getName(),protocol.readString());Assert.assertEquals(request.getId(),protocol.readI64());if (bis.markSupported()){bis.reset();bis.skip(headerLength);}TMessage message=protocol.readMessageBegin();Assert.assertEquals("echoString",message.name);Assert.assertEquals(TMessageType.EXCEPTION,message.type);Assert.assertEquals(ThriftCodec.getSeqId(),message.seqid);TApplicationException exception=TApplicationException.read(protocol);protocol.readMessageEnd();Assert.assertEquals(exceptionMessage,exception.getMessage());}

    private Request createRequest() {

        RpcInvocation invocation = new RpcInvocation();

        invocation.setMethodName("echoString");

        invocation.setArguments(new Object[]{"Hello, World!"});

        invocation.setParameterTypes(new Class<?>[]{String.class});

        invocation.setAttachment(Constants.INTERFACE_KEY, Demo.Iface.class.getName());

        Request request = new Request(1L);

        request.setData(invocation);

        return request;

    }

}
