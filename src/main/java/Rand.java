import java.util.Random;

public class Rand {
  public static void main(String[] args) {
    String s = "edsfsd" + "fdf"
                    + "dsffsd";

    Random r = new Random();
    int i = 0;
    for (int k = 0; k < 20; k++) {
      i = -15 + r.nextInt(31);
      System.out.print(i + " ");
    }
    System.out.println("---------------");
    f();
    System.out.println("---------------");
    f2();
    System.out.println("---------------");
    f3(2 | 100 | 12);
    float[] a = new float[10];
  }

  private static void f() {
    Random r = new Random();
    int i;
    for (int k = 0; k < 20; k++) {
      i = (r.nextInt(40 - 30) + 30) * (r.nextBoolean() ? -1 : 1);
      System.out.print(i + ", ");
    }
  }

  private static void f2() {
    double d2 = Math.pow(4.0, 3.0);
    System.out.println("d2 = " + d2);
  }

  private static void f3(int i) {
    System.out.println("classesAndObjects.f3() = " + i);
  }

  private float[] array() {
    return new float[] {1, 2};
  }
}
