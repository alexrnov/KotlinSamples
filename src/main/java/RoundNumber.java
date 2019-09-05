import java.text.DecimalFormat;

public class RoundNumber {
  public static void main(String... args) {
    System.out.println("main method");
    double v;
    //v = 89.23456;
    //v = 0.0000001;
    v = 0.35999998;
    double v1 = Math.round(v * 10000.0) / 10000.0;
    double v2 = Math.round(v * 100000.0) / 100000.0;
    double v3 = Math.round(v * 1000000.0) / 1000000.0;
    System.out.println("v1 = " + v1 + " v2 = " + v2 + " v3 = " + v3);

    v = 0.5895;
    double v4 = Math.round(v * 1000.0) / 1000.0;
    System.out.println("v4 = " + v4);
  }
}
