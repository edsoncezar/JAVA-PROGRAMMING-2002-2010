/*
 * This class is an example only. A "final" version of
 * this class should implement the DataOutput interface
 * and provide implementations for the methods declared in
 * DataOutput.
 */

import java.io.*;

public class CheckedDataOutput {
    private Checksum cksum;
    private DataOutput out;

    public CheckedDataOutput(DataOutput out, Checksum cksum) {
        this.cksum = cksum;
        this.out = out;
    }

    public void write(int b) throws IOException {
        out.write(b);
        cksum.update(b);
    }

    public void write(byte[] b) throws IOException {
        out.write(b, 0, b.length);
        cksum.update(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
        cksum.update(b, off, len);
    }

    public Checksum getChecksum() {
        return cksum;
    }
}

