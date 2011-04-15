package inter;
import symbols.*;

public class Return extends Stmt {
	public Expr expr;
	public Return(Expr e) {
		expr = e;
   	}
}

