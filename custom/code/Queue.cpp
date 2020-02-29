class Queue {
private:
	int * data;
	int length;
	int front, back;
public:
	Queue(int n) {              //#/ construct
		data = new int[n];
		length = n;
		front = 0;
		back = 0;
	}
	~Queue() {
		delete[] data;
	}
public:
	void push(int x) {          //#/ push
		data[top] = x;          //#/ push_main_begin
		top ++;
	}
};
