int plus10(int a) {
	return a+10;
}

int main() {
	int a; int b; int c;
	b = 0;
	a = b+10;
	c = plus10(a)+plus10(a)+1;

	puts c;
	
	return 0;
}
