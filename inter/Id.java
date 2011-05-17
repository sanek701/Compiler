package inter;
import lexer.*; import symbols.*;

public class Id extends Expr {
    
    public int offset;     // relative address
    public boolean constant = false;
    public Object value;
    public Id(Word id, Type p, int b, boolean c) {
		super(id, p);
		offset = b;
		constant = c;
		if(constant) calculatable = true;
	}
	
	public void setValue(Expr e) {
		//if(type==Type.Int) value = e.gen();
		//if(type==Type.Float) value = new Float(e.gen().calculateFloat());
		System.err.println(">>"+e.gen());
	}
	
	public int calculateInt() { return (Integer)value; }
	public float calculateFloat() { return (Float)value; }
    
//	public String toString() {return "" + op.toString() + offset;}
}
