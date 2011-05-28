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
		  Constant c1 = expr1.gen().calculate();
		  Constant c2 = expr2.gen().calculate();
		  switch(op.tag) {
			case '+':
				return add(c1, c2);
			case '-':
				return sub(c1, c2);
			case '*':
				return mul(c1, c2);
			case '/':
				return div(c1, c2);
		  }
		}
      return new Arith(op, expr1.reduce(), expr2.reduce());
   }

   public Constant add(Constant c1, Constant c2) {
	   Type type = Type.max(c1.type, c2.type);
	   if(type==Type.Float)
			return new Constant( c1.toFloat()+c2.toFloat() );
	   else
			return new Constant( c1.toInt()+c2.toInt() );
   }
	 
	public Constant sub(Constant c1, Constant c2) {
	   Type type = Type.max(c1.type, c2.type);
	   if(type==Type.Float)
			return new Constant( c1.toFloat()-c2.toFloat() );
	   else
			return new Constant( c1.toInt()-c2.toInt() );
	}

   public Constant mul(Constant c1, Constant c2) {
	   Type type = Type.max(c1.type, c2.type);
	   if(type == Type.Float)
			return new Constant( c1.toFloat()*c2.toFloat() );
           else
			return new Constant( c1.toInt()*c2.toInt() );   
   }
	 
	public Constant div(Constant c1, Constant c2) {
	   Type type = Type.max(c1.type, c2.type);
	   if(type == Type.Float)
			return new Constant( c1.toFloat()/c2.toFloat() );
           else
			return new Constant( c1.toInt()/c2.toInt() );   
	}
	
   public String toString() {
	  if(calculatable)
		return expr1.toString()+" ^"+op.toString()+"^ "+expr2.toString();
      return expr1.toString()+" "+op.toString()+" "+expr2.toString();
   }
}
