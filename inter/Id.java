package inter;
import lexer.*; import symbols.*;

public class Id extends Expr {
    
    public int offset;     // relative address
    public boolean constant = false;
    public Constant value;
    
    public Id(Word id, Type p, int b, boolean c) {
		super(id, p);
		offset = b;
		constant = c;
		if(constant) calculatable = true;
	}
	
	public void setValue(Expr e) {
		Expr c = e.gen();
		System.err.println(">>"+e.gen());
	}
    
//	public String toString() {return "" + op.toString() + offset;}
}
