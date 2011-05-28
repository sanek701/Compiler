package inter;
import lexer.*; import symbols.*;

public class Constant extends Expr {

   public Constant(Token tok, Type p) { super(tok, p); calculatable = true; }
   public Constant(int i) { super(new Num(i), Type.Int); calculatable = true; }
   public Constant(float i) { super(new Real(i), Type.Float); calculatable = true; }
   
   public Constant calculate() { return this; }

   public static final Constant
       True  = new Constant(Word.True,  Type.Bool),
       False = new Constant(Word.False, Type.Bool);
    
   public void jumping(int t, int f) {
      if ( this == True && t != 0 ) emit("goto L" + t);
      else if ( this == False && f != 0) emit("goto L" + f);
   }

   public int toInt() { return (type==Type.Int) ? ((Num)op).value : (int)((Real)op).value; }
   public float toFloat() { return (type==Type.Int) ? (float)((Num)op).value : ((Real)op).value; }
}
