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
	void traverse_pre_order() {                //#/ traversePreOrder
		dfs_pre_order(root);
	}
	void traverse_in_order() {                 //#/ traverseInOrder
		dfs_in_order(root);
	}
	void traverse_post_order() {               //#/ traversePostOrder
		dfs_post_order(root);
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
	}                                          //#/ insertNode_end
	void erase_node(Node * & p, int value) {   //#/ eraseNode
		if (p == nullptr) {
			return;
		}
		if (value == p->value) {               //#/ eraseNode_ifEql
			if (p->left != nullptr
				 && p->right != nullptr) {
				Node * np = find_max(p->left);
				int t = np->value;
				erase_node(p->left, t);
				p->value = t;
			} else {
				Node * q = p;                  //#/ eraseNode_preDel
				if (p->left != nullptr) {
					p = p->left;               //#/ eraseNode_linkL
				} else {
					p = p->right;              //#/ eraseNode_linkR
				}
				delete q;                      //#/ eraseNode_delete
			}
			return;                            //#/ eraseNode_return
		}
		if (value < p->value) {                //#/ eraseNode_ifLR
			erase_node(p->left, value);        //#/ eraseNode_recL
		} else {
			erase_node(p->right, value);       //#/ eraseNode_recR
		}
	}                                          //#/ eraseNode_end
	Node * find_max_of(Node * p) {             //#/ findMaxOf
		if (p == nullptr) {
			return nullptr;
		}
		while (p->right != nullptr) {          //#/ findMaxOf_loop
			p = p->right;
		}
		return p;                              //#/ findMaxOf_return
	}
	void dfs_pre_order(Node * p) {             //#/ dfsPreOrder
		if (p == nullptr) {
			return;
		}
		visit(p);                              //#/ dfsPreOrder_main_begin
		dfs_pre_order(p->left);
		dfs_pre_order(p->right);
	}
	void dfs_in_order(Node * p) {              //#/ dfsInOrder
		if (p == nullptr) {
			return;
		}
		dfs_in_order(p->left);                 //#/ dfsInOrder_main_begin
		visit(p);
		dfs_in_order(p->right);
	}
	void dfs_post_order(Node * p) {            //#/ dfsPostOrder
		if (p == nullptr) {
			return;
		}
		dfs_post_order(p->left);               //#/ dfsPostOrder_main_begin
		dfs_post_order(p->right);
		visit(p);
	}
	void visit(Node * p) {
		printf("Node(%d)\n", p->value);
	}
	void clear() {                      //#/ clear
		;
	}
};
/*
insert 50
insert 45
insert 47
insert 48
insert 46
insert 40
erase 45
erase 40
erase 50
*/
