class CircularQueue {
private:
	int * data;
	int size;
	int front, back;
public:
	CircularQueue(int n) {             //#/ construct
		data = new int[n];
		size = n;
		front = 0;
		back = 0;
	}
	~CircularQueue() {
		delete[] data;
	}
public:
	void push(int x) {                 //#/ push
		if (is_full()) {
			return;
		}
		data[back] = x;                //#/ push_main_begin
		back = (back + 1) % size;
	}
	void pop() {                       //#/ pop
		if (is_empty()) {
			return;
		}
		front = (front + 1) % size;    //#/ pop_main_begin
	}
	int front() {                      //#/ front
		if (is_empty()) {
			return 0;
		}
		return data[front];            //#/ front_main_begin
	}
	bool is_empty() {                  //#/ isEmpty
		return (front == back);
	}
	bool is_full() {                   //#/ isFull
		int nback = (back + 1) % size;
		return (nback == front);
	}
};
