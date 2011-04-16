package inter;
import lexer.*; import symbols.*;

public class Temp extends Id {

   static int count = 0;
   int number = 0;

   public Temp(Type p) { super(Word.temp, p, 0); number = ++count; }

   public String toString() { return "t" + number; }
}
