/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.yoko.orb.OCI;

import org.apache.yoko.orb.OB.IORUtil;
import org.apache.yoko.util.HexConverter;
import org.omg.CORBA.NO_MEMORY;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.logging.Logger;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.apache.yoko.orb.OB.Assert._OB_assert;
import static org.apache.yoko.orb.OB.MinorCodes.MinorAllocationFailure;
import static org.apache.yoko.orb.OB.MinorCodes.describeNoMemory;
import static org.apache.yoko.orb.OCI.AlignmentBoundary.EIGHT_BYTE_BOUNDARY;
import static org.apache.yoko.orb.OCI.AlignmentBoundary.FOUR_BYTE_BOUNDARY;
import static org.apache.yoko.orb.OCI.AlignmentBoundary.TWO_BYTE_BOUNDARY;
import static org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE;

public final class Buffer {
    public final BufferReader reader = new Reader();
    public final BufferWriter writer = new Writer();

    private transient byte[] data_; // The octet buffer
    private int len_; // The requested size of the buffer

    public int pos_; // The position counter

    public int length() {
        return len_;
    }

    public int available() {
        return len_ - pos_;
    }

    private void advance(int delta) {
        pos_ += delta;
    }

    public boolean is_full() {
        return pos_ >= len_;
    }

    public boolean dataEquals(Buffer that) {
        if (len_ != that.len_) return false;
        for (int i = 0; i < len_; i++) if(data_[i] != that.data_[i]) return false;
        return true;
    }

    /**
     * Extend the current buffer.
     * @param extra the number of additional bytes required beyond the end of the buffer.
     * @return <code>true</code> iff an existing buffer was insufficient
     */
    public boolean addLength(int extra) {
        _OB_assert(extra >= 0);
        len_ += extra;

        // the existing buffer might be big enough
        if (len_ <= data_.length) {
            return false;
        }
        // ok, we need a bigger buffer
        data_ = copyOf(data_, computeNewBufferSize(len_));
        return true;
    }

    private int computeNewBufferSize(int len) {
        // use an allocation threshold of 4 megabytes
        final int MAX_OVERALLOC = 4 * 1024 * 1024;
        // double the existing capacity, unless over a threshold
        final int minAlloc = data_.length + min(data_.length, MAX_OVERALLOC);
        // allow more if requested length is greater
        return max(len, minAlloc);
    }

    public void consume(Buffer buf) {
        this.data_ = buf.data_;
        this.len_ = buf.len_;
        this.pos_ = buf.pos_;
        buf.data_ = null;
        buf.len_ = 0;
        buf.pos_ = 0;
    }

    /**
     * Create a Buffer with initial length zero.
     */
    public Buffer() {
        // since we expect a write operation to follow, allocate a small buffer up front
        this(newBytes(16), 0, 0);
    }

    private Buffer(byte[] data, int pos, int len) {
        this.data_ = data;
        this.pos_ = pos;
        this.len_ = len;
    }


    public Buffer(byte[] data) {
        this(data, 0, data.length);
    }

    public Buffer(Buffer that) {
        this(copyOf(that.data_), that.pos_, that.len_);
    }

    /**
     * Create a Buffer with <code>len</code> bytes available for writing.
     */
    public Buffer(int len) {
        this(newBytes(len), 0, len);
    }

    private static byte[] copyOf(byte[] data) {
        return copyOf(data, data.length);
    }

    private static byte[] copyOf(byte[] data, int length) {
        try {
            return Arrays.copyOf(data, length);
        } catch (OutOfMemoryError oom) {
            throw new NO_MEMORY(describeNoMemory(MinorAllocationFailure), MinorAllocationFailure, COMPLETED_MAYBE);
        }
    }

    private static byte[] newBytes(int len) {
        try {
            // allocate only multiples of 16 so we can pad without checking
            return new byte[(len + 0xFF) & ~0xFF];
        } catch (OutOfMemoryError oom) {
            throw new NO_MEMORY(describeNoMemory(MinorAllocationFailure), MinorAllocationFailure, COMPLETED_MAYBE);
        }
    }

    @Override
    public String toString() {
        return reader.dumpAllDataWithPosition();
    }

    public boolean readFrom(InputStream in) throws IOException {
        try {
            int result = in.read(data_, pos_, available());
            if (result <= 0) return false;
            advance(result);
            return true;
        } catch (InterruptedIOException ex) {
            advance(ex.bytesTransferred);
            throw ex;
        }
    }

    public void writeTo(OutputStream out) throws IOException {
        try {
            out.write(data_, pos_, available());
            out.flush();
            pos_ = len_;
        } catch (InterruptedIOException ex) {
            advance(ex.bytesTransferred);
            throw ex;
        }
    }

    private enum Padding {;
        /**
         * The base 2 log of the width of our padding array.
         * The array size must be a power of 2, so bit
         * shifting and masking operations can replace
         * division and remainder operations.
         */
        private static final int PADDING_POWER = 5;
        private static final byte PAD_BYTE = (byte) 0xBD;
        private static final byte[] PADDING = arrayOf(1 << PADDING_POWER, PAD_BYTE);
        private static byte[] arrayOf(int n, byte b) {
            byte[] result = new byte[n];
            Arrays.fill(result, b);
            return result;
        }
    }

    final class Reader implements BufferReader {
        @Override
        public void align2() { align(TWO_BYTE_BOUNDARY); }
        @Override
        public void align4() { align(FOUR_BYTE_BOUNDARY); }
        @Override
        public void align8() { align(EIGHT_BYTE_BOUNDARY); }
        @Override
        public void align(int n) {
            switch (n) {
            case 2: align2(); break;
            case 4: align4(); break;
            case 8: align8(); break;
            case 0: break;
            }
        }

        @Override
        public void align(AlignmentBoundary boundary) {
            pos_ = boundary.newIndex(pos_);
        }

        @Override
        public void skipBytes(int n) {
            if (pos_ + n > len_) throw new IndexOutOfBoundsException();
            pos_ = pos_ + n;
        }

        @Override
        public void skipToEnd() {
            pos_ = len_;
        }

        @Override
        public void rewindToStart() {
            pos_ = 0;
        }

        @Override
        public byte peekByte() {
            return data_[pos_];
        }

        @Override
        public byte readByte() {
            return data_[pos_++];
        }

        @Override
        public char readByteAsChar() {
            return (char) data_[pos_++];
        }

        @Override
        public void readBytes(byte[] value, int offset, int length) {
            if (available() < length) throw new IndexOutOfBoundsException();

            System.arraycopy(data_, pos_, value, offset, length);
            pos_ += length;
        }

        @Override
        public char peekChar() {
            return (char)((data_[pos_] << 8) | (data_[pos_ + 1] & 0xff));
        }

        @Override
        public char readChar() {
            return (char) ((data_[pos_++] << 8) | (data_[pos_++] & 0xff));
        }

        @Override
        public char readChar_LE() {
            return (char) ((data_[pos_++] & 0xff) | (data_[pos_++] << 8));
        }

        /**
         * Return the cursor position in the buffer as a formatted string suitable for logging.
         */
        @Override
        public String dumpPosition() {
            return String.format("position=0x%x", pos_);
        }

        /**
         * Return the unread data in the buffer as a formatted string suitable for logging.
         */
        @Override
        public String dumpRemainingData() {
            StringBuilder dump = new StringBuilder();
            dump.append(String.format("Buffer pos=0x%x Buffer len=0x%x Remaining buffer data=%n%n", pos_, len_));

            IORUtil.dump_octets(data_, pos_, available(), dump);
            return dump.toString();
        }

        /**
         * Return all the data in the buffer as a formatted string suitable for logging.
         */
        @Override
        public String dumpAllData() {
            StringBuilder dump = new StringBuilder();
            dump.append(String.format("Buffer len=0x%x All buffer data=%n%n", len_));
            IORUtil.dump_octets(data_, 0, length(), dump);
            return dump.toString();
        }

        /**
         * Return all the data in the buffer, with the position marked, as a formatted string suitable for logging.
         */
        @Override
        public String dumpAllDataWithPosition() {
            StringBuilder sb = new StringBuilder();
            IORUtil.dump_octets(data_, 0, pos_, sb);
            sb.append(String.format("------------------ pos = 0x%08X -------------------%n", pos_));
            IORUtil.dump_octets(data_, pos_, len_ - pos_, sb);
            return sb.toString();
        }

        @Override
        public void appendAvailableDataTo(Writer writer) {
            writer.writeBytes(data_, pos_, available());
        }

        @Override
        public byte[] copyRemainingBytes() {
            return copyOf(data_, available());
        }

        @Override
        public String remainingBytesToAscii() {
            return HexConverter.octetsToAscii(data_, available());
        }
    }

    final class Writer implements BufferWriter {
        @Override
        public void trim() {
            len_ = pos_;
        }

        @Override
        public void padAlign(AlignmentBoundary boundary) {
            padGap(boundary.gap(pos_));
        }

        private void padGap(int gap) {
            switch (gap) {
            case 7: writeByte(Padding.PAD_BYTE);
            case 6: writeByte(Padding.PAD_BYTE);
            case 5: writeByte(Padding.PAD_BYTE);
            case 4: writeByte(Padding.PAD_BYTE);
            case 3: writeByte(Padding.PAD_BYTE);
            case 2: writeByte(Padding.PAD_BYTE);
            case 1: writeByte(Padding.PAD_BYTE);
            case 0: break;
            default: pad(gap);
            }
        }

        @Override
        public void pad(int n) {
            // write as many full copies of PADDING as required
            for (int i = n >> Padding.PADDING_POWER; i > 0; i--) writeBytes(Padding.PADDING);
            // write any remaining bytes of PADDING
            writeBytes(Padding.PADDING, 0, n & ((1 << Padding.PADDING_POWER) - 1));
        }

        @Override
        public boolean ensureAvailable(int size, AlignmentBoundary boundary) {
            int gap = boundary.gap(pos_);
            boolean bufferReplaced = ensureAvailable(gap + size);
            padGap(gap);
            return bufferReplaced;
        }

        @Override
        public boolean ensureAvailable(int size) {
            final int shortfall = size - available();
            return shortfall > 0 && addLength(shortfall);
        }

        @Override
        public void writeBytes(byte[] bytes) {
            writeBytes(bytes, 0, bytes.length);
        }

        @Override
        public void writeBytes(byte[] bytes, int offset, int length) {
            System.arraycopy(bytes, 0, data_, pos_, length);
            pos_ += length;
        }

        @Override
        public void readFrom(org.omg.CORBA.portable.InputStream source) {
            final int length = available();
            source.read_octet_array(data_, pos_, length);
            pos_ += length;
        }

        @Override
        public void writeByte(int i) {
            data_[pos_++] = (byte)i;
        }

        @Override
        public void writeByte(byte b) {
            data_[pos_++] = b;
        }

        @Override
        public void writeChar(char value) {
            data_[pos_++] = (byte) (value >> 010);
            data_[pos_++] = (byte) (value >> 000);
        }

        @Override
        public void writeShort(short value) {
            data_[pos_++] = (byte) (value >> 010);
            data_[pos_++] = (byte) (value >> 000);
        }

        @Override
        public void writeInt(int value) {
            data_[pos_++] = (byte) (value >> 030);
            data_[pos_++] = (byte) (value >> 020);
            data_[pos_++] = (byte) (value >> 010);
            data_[pos_++] = (byte) (value >> 000);
        }

        @Override
        public void writeLong(long value) {
            data_[pos_++] = (byte) (value >> 070);
            data_[pos_++] = (byte) (value >> 060);
            data_[pos_++] = (byte) (value >> 050);
            data_[pos_++] = (byte) (value >> 040);
            data_[pos_++] = (byte) (value >> 030);
            data_[pos_++] = (byte) (value >> 020);
            data_[pos_++] = (byte) (value >> 010);
            data_[pos_++] = (byte) (value >> 000);
        }

        @Override
        public void writeBytes(BufferReader reader) {
            reader.appendAvailableDataTo(this);
        }

        @Override
        public SimplyCloseable recordLength(Logger logger) {
            return new LengthWriter(logger);
        }
    }

    private final class LengthWriter implements SimplyCloseable {
        final Logger logger;
        final int index;

        private LengthWriter(Logger logger) {
            this.logger = logger;
            this.index = pos_;
            this.logger.finest("Writing a gap value for a length at offset " + index);
            writer.pad(4);
        }

        public void close() {
            writeLength();
        }

        private void writeLength() {
            final int length = pos_ - (index + 4);
            data_[index +0] = (byte) (length >>> 030);
            data_[index +1] = (byte) (length >>> 020);
            data_[index +2] = (byte) (length >>> 010);
            data_[index +3] = (byte) (length >>> 000);
            logger.finest("Wrote a length value of " + length + " at offset " + index);
        }
    }
}
