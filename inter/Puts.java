package inter;
import symbols.*;

public class Puts extends Stmt {

   Expr expr;

   public Puts(Expr x) {
      expr = x;
   }
   
   public void gen(int b, int a) {
     expr.gen(b, a);
     emit( "print " + expr );
   }
}
