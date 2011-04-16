package inter;
import symbols.*;

public class Return extends Stmt {
	public Expr expr;
	int lable;
	
	public Return(Expr e) {
		expr = e;
	}
	
	public void gen(int b, int a) {
		Temp t = new Temp(Type.Int);
		emit( "pop "  + t );
		emit( "push " + expr );
		emit( "goto " + t );
	}
}

