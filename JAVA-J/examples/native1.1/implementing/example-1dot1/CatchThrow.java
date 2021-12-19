class CatchThrow {
  private native void catchThrow() throws IllegalArgumentException;
  private void callback() throws NullPointerException {
    throw new NullPointerException("thrown in CatchThrow.callback");
  }
  public static void main(String args[]) {
    CatchThrow c = new CatchThrow();
    try {
      c.catchThrow();
    } catch (Exception e) {
      System.out.println("In Java:\n  " + e);
    }
  }
  static {
    System.loadLibrary("MyImpOfCatchThrow");
  }
}

