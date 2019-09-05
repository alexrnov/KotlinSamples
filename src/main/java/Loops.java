public class Loops {
  public static void main(String[] args) {
    f();
  }
  private static void f() {
    long intervals = 10;

    while (true) {
      long pastTime = System.currentTimeMillis();
      long spentTime = 0L;

      do {
        spentTime = System.currentTimeMillis() - pastTime;
        System.out.println(spentTime + " + spentTime");

      } while (spentTime < 100);
      System.out.println("-----------------------");
      System.out.println("spotPosition");
    }
  }
}
