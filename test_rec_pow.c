int pow(int n, int p) {
	if(n==0)	return p-1;
	return pow(n/2, p+1);
}

int main() {
	puts pow(256, 0);
	return 0;
}
