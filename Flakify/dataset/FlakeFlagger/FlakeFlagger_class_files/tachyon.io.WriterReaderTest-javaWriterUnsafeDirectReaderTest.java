package tachyon.io;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import junit.framework.Assert;

import org.junit.Test;

public class WriterReaderTest {
  private int mDataLength = 122;
  private ByteOrder[] mOrders =
    {ByteOrder.nativeOrder(), ByteOrder.LITTLE_ENDIAN, ByteOrder.BIG_ENDIAN};

  @Test public void javaWriterUnsafeDirectReaderTest() throws IOException{ByteBuffer buf=ByteBuffer.allocateDirect(mDataLength);for (ByteOrder order:mOrders){if (order != ByteOrder.nativeOrder()){continue;}buf.clear();buf.order(order);ByteBufferWriter writer=new JavaByteBufferWriter(buf);generateByteBuffer(writer,order);ByteBufferReader reader=new UnsafeDirectByteBufferReader(writer.getByteBuffer());byteBufferReaderMatcher(reader,order);}}

  private void generateByteBuffer(ByteBufferWriter writer, ByteOrder order) {
    writer.put((byte) -128);
    writer.put((byte) 55);
    writer.put((byte) 127);
    byte[] byteArr = new byte[5];
    byteArr[0] = -11;
    byteArr[1] = 33;
    byteArr[2] = -55;
    byteArr[3] = 99;
    byteArr[4] = -103;
    writer.put(byteArr);
    writer.put(byteArr, 1, 2);
    writer.putChar('\u0000');
    writer.putChar('\uffff');
    writer.putChar('A');
    writer.putChar('a');
    writer.putDouble(1.552);
    writer.putDouble(-1.552);
    writer.putDouble(5555000.5);
    writer.putDouble(-5555000.5);
    writer.putFloat((float) 1.552);
    writer.putFloat((float) -1.552);
    writer.putFloat((float) 5555.5);
    writer.putFloat((float) -5555.5);
    writer.putInt(9999);
    writer.putInt(-9999);
    writer.putInt(2147483647);
    writer.putInt(-2147483648);
    writer.putLong(99999);
    writer.putLong(-99999);
    writer.putLong(9223372036854775807L);
    writer.putLong(-9223372036854775808L);
    writer.putShort((short) 99);
    writer.putShort((short) -99);
    writer.putShort((short) 32767);
    writer.putShort((short) -32768);
  }

  private void byteBufferReaderMatcher(ByteBufferReader reader, ByteOrder order) {
    reader.order(order);
    Assert.assertEquals((byte) -128, reader.get());
    Assert.assertEquals((byte) 55, reader.get());
    Assert.assertEquals((byte) 127, reader.get());
    byte[] byteArr = new byte[5];
    reader.get(byteArr);
    Assert.assertEquals((byte) -11, byteArr[0]);
    Assert.assertEquals((byte) 33, byteArr[1]);
    Assert.assertEquals((byte) -55, byteArr[2]);
    Assert.assertEquals((byte) 99, byteArr[3]);
    Assert.assertEquals((byte) -103, byteArr[4]);
    byte[] dst = new byte[5];
    reader.get(dst, 1, 2);
    Assert.assertEquals((byte) 33, dst[1]);
    Assert.assertEquals((byte) -55, dst[2]);
    Assert.assertEquals('\u0000', reader.getChar());
    Assert.assertEquals('\uffff', reader.getChar());
    Assert.assertEquals('A', reader.getChar());
    Assert.assertEquals('a', reader.getChar());
    Assert.assertEquals(1.552, reader.getDouble());
    Assert.assertEquals(-1.552, reader.getDouble());
    Assert.assertEquals(5555000.5, reader.getDouble());
    Assert.assertEquals(-5555000.5, reader.getDouble());
    Assert.assertEquals((float) 1.552, reader.getFloat());
    Assert.assertEquals((float) -1.552, reader.getFloat());
    Assert.assertEquals((float) 5555.5, reader.getFloat());
    Assert.assertEquals((float) -5555.5, reader.getFloat());
    Assert.assertEquals(9999, reader.getInt());
    Assert.assertEquals(-9999, reader.getInt());
    Assert.assertEquals(2147483647, reader.getInt());
    Assert.assertEquals(-2147483648, reader.getInt());
    Assert.assertEquals(99999, reader.getLong());
    Assert.assertEquals(-99999, reader.getLong());
    Assert.assertEquals(9223372036854775807L, reader.getLong());
    Assert.assertEquals(-9223372036854775808L, reader.getLong());
    Assert.assertEquals((short) 99, reader.getShort());
    Assert.assertEquals((short) -99, reader.getShort());
    Assert.assertEquals((short) 32767, reader.getShort());
    Assert.assertEquals((short) -32768, reader.getShort());
  }
}
