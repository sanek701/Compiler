package inter;
import lexer.*; import symbols.*; import java.util.*;

public class Expr extends Stmt {

   public Token op;
   public Type type;
   private LinkedList<Call> beforeStmts = null;

   Expr(Token tok, Type p) { op = tok; type = p; }

   public Expr reduce() { return this; }
   public Expr gen() { return this; }

	public void addBeforeStmts(LinkedList<Call> v) {
		beforeStmts = v;
	}

	public void gen(int b, int a) { // if there is a function call in the expression
		if(beforeStmts==null) return;
		for(Iterator<Call> i = beforeStmts.iterator(); i.hasNext();) {
			i.next().gen(b, a);
		}
	}

   public void jumping(int t, int f) { emitjumps(toString(), t, f); }

   public void emitjumps(String test, int t, int f) {
	  gen(0,0);
      if( t != 0 && f != 0 ) {
         emit("if " + test + " goto " + t);
         emit("goto " + f);
      }
      else if( t != 0 ) emit("if " + test + " goto " + t);
      else if( f != 0 ) emit("iffalse " + test + " goto " + f);
      else ; // nothing since both t and f fall through
   }
   public String toString() { return op.toString(); }
}
