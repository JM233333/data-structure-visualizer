class List {
private:
	class Node {
	public:
        int value;
        Node * next;
        Node(int value) {
            this->value = value;
            this->next = nullptr;
        }
    };
private:
	Node * head;
public:
	List() {                     //#/ construct
		head = nullptr;
	}
	~List() {
		clear();
	}
public:
	void push_front(int value) { //#/ pushFront
		Node * p = new Node(value);
		if (head == nullptr) {
			head = p;
		} else {                 //#/ pushFront_if/else
			p->next = head->next;
			head->next = p;
		}
	}                            //#/ pushFront_end
	void clear() {
		;
	}
};
