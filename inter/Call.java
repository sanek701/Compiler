package inter;
import lexer.*;
import symbols.*;
import java.util.*;

public class Call extends Stmt {
	public Function f;
	public Function parent;
	private Expr args[];
	private Object[] argsTypes;
	private Temp tmp; // Stores the return value
	private int j, count;
	
	public Call(Function func, Id t, Function p) {
		f = func;
		parent = p;
    	tmp = (Temp)t; // returning value
		count = f.args.size(); //  expected args count
		args = new Expr[count];
		argsTypes = f.args.values().toArray();
		j = 0; // args counter
	}
  
	public boolean addArg(Expr e) {
		if(j>=count) return false;
		if(e.type != (Type)argsTypes[j]) return false;
		args[j] = e;
		j += 1;
    	return true;
  	}

	public boolean checkArg() {
		return (j==count);
	}
	
	public void gen(int b, int a) {
		java.util.Set<Token> vars = parent.env.localVars();
		LinkedList<Token> reversedVars = new LinkedList();		// Save auto vars
		for(Iterator<Token> i=vars.iterator(); i.hasNext();) {
			Token varId = i.next();
			emit("push "+varId);
			reversedVars.add(varId);
		}
		
		for(int i=0; i<count; i++) // Push arguments
			emit( "push " + args[i] );

		int label = newlabel(); // Push the returning point
		emit("push " + label ); 
		
		emit( "call " + f.label);
		emitlabel(label);
		emit( "pop " + tmp ); // Get the result

		for(Iterator<Token> i=reversedVars.descendingIterator(); i.hasNext();) { // Restore auto vars
			Token tok = i.next();
			if(tmp.toString().equals(tok.toString())) {
				emit("pop");
				continue;
			}
			emit("pop "+tok);
		}
	}
}

