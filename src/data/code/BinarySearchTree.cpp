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
	BinarySearchTree() {                //#/ construct
		root = nullptr;
	}
	~BinarySearchTree() {
		clear();
	}
public:
	void find(int value) {              //#/ find
		;
	}
private:
	void find(Node * p, int value) {
		;
	}
public:
	void insert(int value) {            //#/ insert
		if (root == nullptr) {
			root = new Node(value);
			return;
		}
		insert(root, value);            //#/ insert_jump
	}
private:
	void insert(Node * p, int value) {  //#/ insert#
		;
	}
	void erase(int value) {             //#/ erase
		;
	}
	void clear() {                      //#/ clear
		;
	}
};
