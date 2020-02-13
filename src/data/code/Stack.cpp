class Stack {
private:
	int * data;
	int length;
	int top;
public:
	Stack(int n) {              //#/ construct
		data = new int[n];
		length = n;
		top = 0;
	}
	~Stack() {
		delete[] data;
	}
public:
	void push(int x) {          //#/ push
		data[top] = x;
		top ++;
	}
	void pop() {                //#/ pop
		top --;
	}
	int top() {                 //#/ top
		if (is_empty()) {
			return 0;
		}
		return data[top - 1];
	}
	bool is_empty() {           //#/ is_empty
		return (top == 0);
	}
	bool is_full() {            //#/ is_full
		return (top == length);
	}
};
