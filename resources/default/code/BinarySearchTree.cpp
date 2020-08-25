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
	void erase(int value) {                    //#/ erase
		erase_node(root, value);
	}
	Node * find_max() {                        //#/ findMax
		return find_max_of(root);
	}
private:
	Node * find_node(Node * & p, int value) {  //#/ findNode
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
	void erase_node(Node * & p, int value) {    //#/ eraseNode
		if (value == p->value) {
			if (p->left != nullptr
				 && p->right != nullptr) {
				Node * np = find_max(p->left);  //#/ eraseNode_findMax
				p->value = np->value;
				erase_node(p->left, np->value); //#/ eraseNode_rec
			} else {
				Node * q = p;
				if (p->left != nullptr) {       //#/ eraseNode_ifLR_1
					p = p->left;                //#/ eraseNode_linkL
				} else {
					p = p->right;               //#/ eraseNode_linkR
				}
				delete q;                       //#/ eraseNode_delete
			}
			return;                             //#/ eraseNode_return
		}
		if (value < p->value) {                 //#/ eraseNode_ifLR_2
			erase_node(p->left, value);         //#/ eraseNode_recL
		} else {
			erase_node(p->right, value);        //#/ eraseNode_recR
		}
	}
	Node * find_max_of(Node * p) {             //#/ findMaxOf
		if (p == nullptr) {
			return nullptr;
		}
		while (p->right != nullptr) {          //#/ findMaxOf_loop
			p = p->right;
		}
		return p;                              //#/ findMaxOf_return
	}
	void clear() {                      //#/ clear
		;
	}
};
