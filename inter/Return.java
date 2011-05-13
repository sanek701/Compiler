package inter;
import symbols.*;

public class Return extends Stmt {
	public Expr expr;
	public Function f;
	int lable;
	
	public Return(Expr e, Function func) {
		expr = e;
		f = func;
		if(expr.type != f.type)
			throw new Error("Returning "+e.type+" insted "+f.type);
		f.addReturn();
	}
	
	public void gen(int b, int a) {
		Temp t = new Temp(Type.Int);
		int label = newlabel();
		expr.gen(label, a); // if there were calls in return stmt
		emit( "push " + expr ); // push the result
		emit( "ret " + f.ret );
	}
}

