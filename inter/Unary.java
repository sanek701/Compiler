package inter;
import lexer.*; import symbols.*;

public class Unary extends Op {

   public Expr expr;

   public Unary(Token tok, Expr x) {    // handles minus, for ! see Not
      super(tok, null);  expr = x;
      if (x.calculatable)  calculatable = true;
      type = Type.max(Type.Int, expr.type);
      if (type == null ) error("type error");
   }

   public Expr gen() { 
	System.err.println("Un: "+expr+" "+calculatable);
	 if(calculatable) {
		Constant c1 = expr.gen().calculate();
		if(type == Type.Float) return new Constant( -c1.toFloat() );
		else return new Constant( -c1.toInt() );
	}
	return new Unary(op, expr.reduce());
   }

   public String toString() { return op.toString()+" "+expr.toString(); }
}
