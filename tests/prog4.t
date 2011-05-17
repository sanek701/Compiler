{
	int BLANK; int TAB; int NEWLINE; int peek; int line; const int readch= 55; const float aa = 3.4;
	while( true ) {
		if( peek == BLANK || peek == TAB ) ;
		else if( peek == NEWLINE ) line = line + 1;
		else break;
		peek = readch;
	}
}
