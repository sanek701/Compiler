int zero(int n) {
	if(n==0) return 0;
	return zero(n-1);
}

int main() {
	puts zero(2);
	return 0;
}
