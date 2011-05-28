package inter;
import symbols.*; import lexer.*; import java.util.*;

public class Switch extends Stmt {

   Id id; Access ac; Stmt stmt;
   Case defaultCase = null;
   Vector<Case> cases = new Vector();

   public Switch(Id i) { id = i; stmt = null; }
   public Switch(Access i) { ac = i; stmt = null; }

   public void init(Stmt s) { stmt = s; }
   public void addCase(Case c) { cases.add(c); }
   public void addDefault(Case c) { defaultCase = c; }

   public void gen(int b, int a) {
      after = a;                // save label a
      for(Iterator<Case> i = cases.iterator(); i.hasNext(); ) {
         Case c = i.next();
         Rel r = new Rel(Word.eq, id, c.value());
         r.jumping(c.lable, 0);
      }
      if(defaultCase!=null) emit("goto L" + defaultCase.lable);
      int label = newlabel();   // label for expr
      stmt.gen(b,label);
      emitlabel(label);
   }
}
