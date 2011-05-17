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
		  if(type==Type.Int) return new Constant(calculateInt());
		  if(type==Type.Float) return new Constant(calculateFloat());
		}
      return new Arith(op, expr1.reduce(), expr2.reduce());
   }
   
   public int calculateInt() {
		int e1 = expr1.calculateInt();
		int e2 = expr2.calculateInt();
		switch(op.toString().charAt(0)) {
			case '+':
				return e1+e2;
			case '*':
				return e1*e2;
		}
		return 0;
	}
	
	public float calculateFloat() {
		float e1 = expr1.calculateFloat();
		float e2 = expr2.calculateFloat();
		switch(op.toString().charAt(0)) {
			case '+':
				return e1+e2;
			case '*':
				return e1*e2;
		}
		return 0;
	}

   public String toString() {
	  if(calculatable)
		return expr1.toString()+" ^"+op.toString()+"^ "+expr2.toString();
      return expr1.toString()+" "+op.toString()+" "+expr2.toString();
   }
}
