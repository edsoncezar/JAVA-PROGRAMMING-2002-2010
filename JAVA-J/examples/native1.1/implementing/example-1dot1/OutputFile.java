public class OutputFile extends File {

    static {
        try {
            System.loadLibrary("file");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("can't find your library");
            System.exit(-1);
        }

    }

    protected int fd;

    public OutputFile(String path) {
        super(path);
    }

    public native boolean open();
    public native void close();
    public native int write(byte[] b, int len);
}
