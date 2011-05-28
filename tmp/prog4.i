L1:	readch = 55
L3:	aa = 3.4
L4:L5:	if peek == BLANK goto L9
	iffalse peek == TAB goto L8
L9:L7:	goto L6
L8:	iffalse peek == NEWLINE goto L11
L10:	line = line + 1
	goto L6
L11:	goto L2
L6:	peek = readch
	goto L4
L2:
