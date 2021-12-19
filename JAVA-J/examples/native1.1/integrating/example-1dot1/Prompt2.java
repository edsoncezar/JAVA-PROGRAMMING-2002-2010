class Prompt2 {
  private native String getLine(String prompt);
  private native String getLine(String prompt, int length);
  public static void main(String args[]) {
    Prompt2 p = new Prompt2();
    String input = p.getLine("Type a line: ");
    System.out.println("User typed: " + input);
  }
  static {
    System.loadLibrary("MyImpOfPrompt2");
  }
}

