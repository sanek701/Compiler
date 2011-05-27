package inter;
import lexer.*; import symbols.*;

public class Arith extends Op {

   public Expr expr1, expr2;

   public Arith(Token tok, Expr x1, Expr x2)  {
      super(tok, null); expr1 = x1; expr2 = x2;
      type = Type.max(expr1.type, expr2.type);
      if (type == null ) error("type error");
      if (x1.calculatable && x2.calculatable)
		calculatable = true;
   }

   public Expr gen() {
	  if(calculatable) {
		  Constant c1 = expr1.calculate();
		  Constant c2 = expr2.calculate();
		  switch(op.toString().charAt(0)) {
			case '+':
				return Constant.sum(c1, c2);
			case '*':
				//return Constant.mul(c1, c2);
		  }
		}
      return new Arith(op, expr1.reduce(), expr2.reduce());
   }
	
   public String toString() {
	  if(calculatable)
		return expr1.toString()+" ^"+op.toString()+"^ "+expr2.toString();
      return expr1.toString()+" "+op.toString()+" "+expr2.toString();
   }
}
