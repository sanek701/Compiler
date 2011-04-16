package inter;
import lexer.*; import symbols.*;

public class Id extends Expr {

	public int offset;     // relative address
	public Temp fid=null;

	public Id(Word id, Type p, int b) { super(id, p); offset = b; }
	public Id(Word id, Type p, int b, Temp id2) { super(id, p); offset = b; fid = id2; }

	public String toString() {
		if (fid==null)
			 return op.toString();
		else return fid.toString();
	}
}
