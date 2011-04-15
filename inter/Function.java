package inter;
import lexer.*; import symbols.*; import java.util.*;

public class Function extends Expr {

	private Stmt stmt;
	public Hashtable args;

	public Function(Word id, Type p) { super(id, p); }
	public void addArgument(Word id, Type p) { args.put(id, p); }

//	public String toString() {return "" + op.toString() + offset;}
}
