import java.util.List;

public class ClassDelim {
  public static void main(String[] args) {

    String s = "  sdv            sfsfd         sfg dsfd edsffds    dsf  sgfs ";
    String[] f = s.trim().split("\\s+");
    System.out.println(f[0]);
  }
}
