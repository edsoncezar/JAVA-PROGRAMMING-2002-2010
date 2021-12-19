public class File {

    protected String path;

    public static final char separatorChar = ':';

    public File(String path) {
        if (path == null) {
            throw new NullPointerException();
        }
        this.path = path;
    }        

    public String getFileName() {
        int index = path.lastIndexOf(separatorChar);
        return (index < 0) ? path : path.substring(index + 1);
    }

    public String getPath() {
        return path;
    }
}
