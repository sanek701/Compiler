package inter;
import symbols.*;
import java.util.*;

public class Call extends Stmt {
	public Function f;
	private Expr args[];
	private Object[] argsTypes;
	private Temp tmp;
	private int j, count;
	
	public Call(Function func, Id t) {
		f = func;
    	tmp = (Temp)t;
		count = f.args.size();
		args = new Expr[count];
		argsTypes = f.args.values().toArray();
		j = 0;
	}
  
	public boolean addArg(Expr e) {
		if(j>=count) return false;
		if(e.type != (Type)argsTypes[j]) return false;
		args[j] = e;
		j += 1;
    	return true;
  	}
	
	public void gen(int b, int a) {
		int label = newlabel();
		emit("push " + label );
		
		for(int i=0; i<count; i++)
			emit( "push " + args[i] );
		
		emit( "goto " + f.label);
		emitlabel(label);
		emit( "pop " + tmp );
	}
}

