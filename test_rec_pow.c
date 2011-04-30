int pow(int n, int p) {
	if(n==0)	return p;
	return pow(n/2, p+1);
}

int main() {
	puts pow(128, 0);
	return 0;
}
