class BinarySearchTree {
private:
	class Node {
	public:
        int value;
        Node * left;
        Node * right;
        Node(int value) {
            this->value = value;
            this->left = nullptr;
            this->right = nullptr;
        }
    };
private:
	Node * root;
public:
	BinarySearchTree() {                       //#/ construct
		root = nullptr;
	}
	~BinarySearchTree() {
		clear();
	}
public:
	Node * find(int value) {                   //#/ find
		return find_node(root, value);
	}
	void insert(int value) {                   //#/ insert
		insert_node(root, value);
	}
private:
	Node * find_Node(Node * & p, int value) {  //#/ findNode
		if (p == nullptr) {                    //#/ findNode_ifFail
			return nullptr;
		}
		if (value == p->value) {               //#/ findNode_ifSucc
			return p;
		}
		if (value < p->value) {                //#/ findNode_ifLR
			return find_Node(p->left, value);  //#/ findNode_recL
		} else {
			return find_Node(p->right, value); //#/ findNode_recR
		}
	}
	void insert_node(Node * & p, int value) {  //#/ insertNode
		if (p == nullptr) {                    //#/ insertNode_ifIns
			p = new Node(value);
			return;
		}
		if (value == p->value) {               //#/ insertNode_ifEql
			return;
		}
		if (value < p->value) {                //#/ insertNode_ifLR
			insert_node(p->left, value);       //#/ insertNode_recL
		} else {
			insert_node(p->right, value);      //#/ insertNode_recR
		}
	}
	void erase(int value) {             //#/ erase
		;
	}
	void clear() {                      //#/ clear
		;
	}
};
