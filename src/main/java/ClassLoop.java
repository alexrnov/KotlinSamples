public class ClassLoop {
  public static void main(String[] args) {
    int[] a = new int[5];
    for (int i = 0; i < a.length; i++) {
      a[i] = i;
    }
    for (int k: a) {
      System.out.println("k = " + k);
    }
  }
}
