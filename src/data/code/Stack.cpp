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
		if (is_full()) {
			return;
		}
		data[top] = x;          //#/ push_main_begin
		top ++;
	}                           //#/ push_end
	void pop() {                //#/ pop
		if (is_empty()) {
			return;
		}
		top --;                 //#/ pop_main_begin
	}                           //#/ pop_end
	int top() {                 //#/ top
		if (is_empty()) {
			return 0;
		}
		return data[top - 1];   //#/ top_main_begin
	}                           //#/ top_end
	bool is_empty() {           //#/ isEmpty
		return (top == 0);
	}
	bool is_full() {            //#/ isFull
		return (top == length);
	}
};
