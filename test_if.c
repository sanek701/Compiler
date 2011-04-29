int sum(int a, int b) {
	return a+b;
}

int main() {
	int a; int b;
	a = 2; b = 3;
	
	if(sum(a,b)==sum(b,a)) {
		puts 1;
	}
}

