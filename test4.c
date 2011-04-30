int plus10(int a) {
	return a+10;
}

int main() {
	puts plus10(plus10(0));
	return 0;
}