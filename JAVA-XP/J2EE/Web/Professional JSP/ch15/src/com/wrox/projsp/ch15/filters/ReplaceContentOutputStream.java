package com.wrox.projsp.ch15.filters;

import java.io.*;
import javax.servlet.*;

public abstract class ReplaceContentOutputStream extends ServletOutputStream {

  private OutputStream intStream;
  private ByteArrayOutputStream baStream;
  private boolean closed = false;
  private boolean transformOnCloseOnly = false;
   
  public ReplaceContentOutputStream(OutputStream outStream) {
    intStream = outStream;
    baStream = new ByteArrayOutputStream();
  }

  public void write(int i) throws java.io.IOException {
    baStream.write(i);
  }

  public void close() throws java.io.IOException {
    if (!closed) {
      processStream();
      intStream.close();
      closed = true;
    }
  }
    
  public void flush() throws java.io.IOException {
    if (baStream.size() != 0) {
      if (!transformOnCloseOnly) {
        processStream();
        baStream = new ByteArrayOutputStream();
      }
    }
  }

  public abstract byte[] replaceContent(byte[] inBytes)
            throws java.io.IOException;

  public void processStream() throws java.io.IOException {
    intStream.write(replaceContent(baStream.toByteArray()));
    intStream.flush();
  }

  public void setTransformOnCloseOnly() {
    transformOnCloseOnly = true;
  }
}
