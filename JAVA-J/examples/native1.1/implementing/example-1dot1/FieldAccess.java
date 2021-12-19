class FieldAccess {
  static int si;
  String s;

  private native void accessFields();
  public static void main(String args[]) {
    FieldAccess c = new FieldAccess();
    FieldAccess.si = 100;
    c.s = "abc";
    c.accessFields();
    System.out.println("In Java:");
    System.out.println("  FieldAccess.si = " + FieldAccess.si);
    System.out.println("  c.s = \"" + c.s + "\"");
  }
  static {
    System.loadLibrary("MyImpOfFieldAccess");
  }
}

