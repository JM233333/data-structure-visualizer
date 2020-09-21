class Queue {
private:
	int * data;
	int length;
	int front, back;
public:
	Queue(int n) {               //#/ construct
		data = new int[n];
		length = n;
		front = 0;
		back = 0;
	}
	~Queue() {
		delete[] data;
	}
public:
	void push(int x) {           //#/ push
		if (is_full()) {
			return;
		}
		data[back] = x;          //#/ push_main_begin
		back ++;
	}
	void pop() {                 //#/ pop
		if (is_empty()) {
			return;
		}
		front ++;                //#/ pop_main_begin
	}
	int front() {                //#/ front
		if (is_empty()) {
			return 0;
		}
		return data[front];      //#/ front_main_begin
	}
	bool is_empty() {            //#/ isEmpty
		return (front == back);
	}
	bool is_full() {             //#/ isFull
		return (back == length);
	}
};
