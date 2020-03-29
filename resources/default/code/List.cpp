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
	List() {                                   //#/ construct
		head = nullptr;
	}
	~List() {
		clear();
	}
private:
	Node get_node(int index) {                 //#/ getNode
		Node * p = head;
		for (int i = 0; i < index; i ++) {     //#/ getNode_loop_begin
			p = p->next;
			if (p == nullptr) {
				return nullptr;
			}
		}
		return p;                              //#/ getNode_loop_end
	}
public:
	int get(int index) {                       //#/ get
		return get_node(index).value;
	}
	void push_front(int value) {               //#/ pushFront
		Node * p = new Node(value);
		p->next = head;
		head = p;
	}                                          //#/ pushFront_end
	void insert(int index, int value) {        //#/ insert
		if (index <= 0) {
			push_front(value);
			return;
		}
		Node * prv = get_node(index - 1);      //#/ insert_getNode
		Node * p = new Node(value);            //#/ insert_main_begin
		p->next = prv->next;
		prv->next = p;
	}
	void pop_front() {                         //#/ popFront
		if (head == nullptr) {
			return;
		}
		Node * p = head;                       //#/ popFront_main_begin
		head = head->next;
		delete p;
	}
	void erase(int index) {                    //#/ erase
		if (index <= 0) {
			pop_front();
			return;
		}
		Node * prv = get_node(index - 1);      //#/ erase_getNode
		if (prv->next == nullptr) {
			return;
		}
		Node * p = prv->next;                  //#/ erase_main_begin
		prv->next = prv->next->next;
		delete p;
	}
	void clear() {                      //#/ clear
		;
	}
};
