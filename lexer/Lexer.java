package lexer;
import java.io.*; import java.util.*; import symbols.*;
public class Lexer {
   public static int line = 1;
   char peek = ' ';
   Hashtable words = new Hashtable();
   void reserve(Word w) { words.put(w.lexeme, w); }
   final char EOF = (char)(-1);

   public Lexer() {

      reserve( new Word("if",    Tag.IF)    );
      reserve( new Word("else",  Tag.ELSE)  );
      reserve( new Word("while", Tag.WHILE) );
      reserve( new Word("do",    Tag.DO)    );
      reserve( new Word("break", Tag.BREAK) );
      reserve( new Word("return", Tag.RETURN) );

      reserve( Word.True );  reserve( Word.False );

      reserve( Type.Int  );  reserve( Type.Char  );
      reserve( Type.Bool );  reserve( Type.Float );
   }

   void readch() throws IOException { peek = (char)System.in.read(); }
   boolean readch(char c) throws IOException {
      readch();
      if( peek != c ) return false;
      peek = ' ';
      return true;
   }
   public Token scan() throws IOException {
      for( ; ; readch() ) {
         if( peek == ' ' || peek == '\t' ) continue;
         else if( peek == '\n' ) line = line + 1;
         else break;
      }
      switch( peek ) {
      case EOF:
         return new Token(Tag.EOF);
      case '&':
         if( readch('&') ) return Word.and;  else return new Token('&');
      case '|':
         if( readch('|') ) return Word.or;   else return new Token('|');
      case '=':
         if( readch('=') ) return Word.eq;   else return new Token('=');
      case '!':
         if( readch('=') ) return Word.ne;   else return new Token('!');
      case '<':
         if( readch('=') ) return Word.le;   else return new Token('<');
      case '>':
         if( readch('=') ) return Word.ge;   else return new Token('>');
      }
      if( Character.isDigit(peek) ) {
         int v = 0;
         do {
            v = 10*v + Character.digit(peek, 10); readch();
         } while( Character.isDigit(peek) );
         if( peek != '.' ) {
            //System.err.println("P NUM "+v);
            return new Num(v);
      }
         float x = v; float d = 10;
         for(;;) {
            readch();
            if( ! Character.isDigit(peek) ) break;
            x = x + Character.digit(peek, 10) / d; d = d*10;
         }
      //System.err.println("P REAL "+x);
         return new Real(x);
      }
      if( Character.isLetter(peek) ) {
         StringBuffer b = new StringBuffer();
         do {
            b.append(peek); readch();
         } while( Character.isLetterOrDigit(peek) );
         String s = b.toString();
         Word w = (Word)words.get(s);
         if( w != null ) {
            //System.err.println("P R_WORD "+s);
            return w;
      }
         w = new Word(s, Tag.ID);
         words.put(s, w);
      //System.err.println("P N_WORD "+s);
         return w;
      }
      //System.err.println("P TOK "+peek);
      Token tok = new Token(peek); peek = ' ';
      return tok;
   }
}
