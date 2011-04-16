package inter;
import lexer.*; import symbols.*; import java.util.*;

public class Function extends Stmt {

	private Stmt body;
	public Hashtable args;
	public int label;
	public String name;
	public Type type;

	public Function(Word id, Type p) {
		//super(id, p); //expr
		name = id.toString();
		args = new Hashtable();
		body = Stmt.Null;
		label = newlabel();
		type = p;
	}
	
	public void addArgument(Word id, Type p) { args.put(id, p); }
	public void setBody(Stmt s) { body = s; }
	
	public void gen(int b, int a) {
		emitlabel(label);
		java.util.Set<Word> vars = args.keySet();
		for(Iterator<Word> i=vars.iterator(); i.hasNext();)
			emit("pop "+i.next());
		int bodyLable = newlabel();
		body.gen(bodyLable, a);
	}

//	public String toString() {return "" + op.toString() + offset;}
}
