package inter;
import symbols.*;
import java.util.*;

public class Call extends Stmt {
	public Function f;
  private Expr args[];
  private Iterator i;
  private Temp tmp;
  private int j, count;
	
	public Call(Function fun, Id t) {
		f = fun;
    tmp = (Temp)t;
    count = f.args.size();
    args = new Expr[count];
    Iterator i = f.args.entrySet().iterator();
    j = 0;
	}
  
  public boolean addArg(Expr e) {
    if(e.type != i.next()) return false;
    if(j>=count) return false;
    args[j] = e;
    j += 1;
    return true;
  }
	
	public void gen(int b, int a) { }
}

