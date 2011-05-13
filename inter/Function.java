package inter;
import lexer.*; import symbols.*; import java.util.*;

public class Function extends Stmt {

	private Stmt body;
	private boolean hasReturn = false;
	public Hashtable args;
	public int label;
	public String name;
	public Type type;
	public Temp ret; // Stores the return point
	public Env env;

	public Function(Word id, Type p) {
		name = id.toString();
		args = new Hashtable();
		body = Stmt.Null;
		label = newlabel();
		type = p;
		ret = new Temp(Type.Int);
	}
	
	public void addArgument(Word id, Type p) { args.put(id, p); }
	public void addReturn() { hasReturn = true; }
	public void setBody(Stmt s, Env e) {
		body = s;
		env = e;
		e.put(new Word(ret.toString(), Tag.TEMP), ret); // adding ret to save it before calls
		if(!hasReturn)
			 throw new Error("Function "+name+" should return value");
	}
	
	public void gen(int b, int a) {
		emitlabel(label);
		emit( "pop "  + ret ); // Get the return point
		
		java.util.Set<Word> vars = args.keySet();
		for(Iterator<Word> i=vars.iterator(); i.hasNext();) // Get arguments from Stack
			emit("pop "+i.next());
		
		int bodyLable = newlabel();
		body.gen(bodyLable, a);
	}
}
