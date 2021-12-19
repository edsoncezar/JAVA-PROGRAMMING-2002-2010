import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;

public class CheckedInputStream extends FilterInputStream {
    private Checksum cksum;

    public CheckedInputStream(InputStream in, Checksum cksum) {
        super(in);
        this.cksum = cksum;
    }

    public int read() throws IOException {
        int b = in.read();
        if (b != -1) {
            cksum.update(b);
        }
        return b;
    }

    public int read(byte[] b) throws IOException {
        int len;
        len = in.read(b, 0, b.length);
        if (len != -1) {
            cksum.update(b, 0, len);
        }
        return len;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        len = in.read(b, off, len);
        if (len != -1) {
            cksum.update(b, off, len);
        }
        return len;
    }

    public Checksum getChecksum() {
        return cksum;
    }
}

