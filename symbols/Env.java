package symbols;
import java.util.*; import lexer.*; import inter.*;

public class Env {

	private Hashtable table;
	protected Env prev;

	public Env(Env n) { table = new Hashtable(); prev = n; }

	public void put(Token w, Id i) { table.put(w, i); }
	public void put(Token w, Function i) { table.put(w, i); }

	public java.util.Set<Token> localVars() {
		return table.keySet();
	}

	public Id get(Token w) {
		for( Env e = this; e != null; e = e.prev ) {
			Id found = (Id)(e.table.get(w));
			if( found != null ) return found;
		}
		return null;
	}
	
	public Function getFunc(Token w) {
		for( Env e = this; e != null; e = e.prev ) {
			Function found = (Function)(e.table.get(w));
			if( found != null ) return found;
		}
		return null;
	}
}
