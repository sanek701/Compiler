package inter;
import lexer.*; import symbols.*;

public class Case extends Stmt {
   int lable;
   Token tok;

   public Case(Token c) {
     lable = newlabel();
     tok = c;
   }

   public Constant value() {
      switch(tok.tag) {
         case Tag.NUM:   return new Constant(tok, Type.Int);
         case Tag.REAL:  return new Constant(tok, Type.Float);
         case Tag.TRUE:  return Constant.True;
         case Tag.FALSE: return Constant.False;
         default: return null;
      }
   }

   public void gen(int b, int a) {
      emitlabel(lable);
   }
}
