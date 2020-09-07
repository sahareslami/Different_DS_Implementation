#include "../src/BinaryTree.h"
#include <iostream>
#include <string>

using namespace std;
typedef BinaryTree<string, InternalBinaryTreeNode<string>>  StringBinaryTree;

string inOrder[] = { "g" , "d" , "k" , "h" , "b" , "a" , "e" , "i" , "c" , "j" , "f" };
string preOrder[] = { "a" , "b" , "d" , "g" , "h" , "k" , "c" , "e" , "i" , "f" , "j" };
string postOrder[] = { "g" , "k" , "h" , "d" , "b" , "i" , "e" , "j" , "f" , "c" , "a" };
int arrSize  = 11;

void LoadBinaryTree(StringBinaryTree	*bt){
	//                                                     a
	//												/		      \
	//											b		            c
	//										/		         	/   	\
	//								  	   d		           e          f
	//							         g    h                 i     j
	//									 	 k

	bt->insertRootNode("a");

	StringBinaryTree::BinaryTreeNode		&btna = bt->getRootNode();
	bt->insertLeftChild(btna, "b");
	bt->insertRightChild(btna, "c");

	StringBinaryTree::BinaryTreeNode		&btnb = btna.getLeftChild();
	StringBinaryTree::BinaryTreeNode		&btnc = btna.getRightChild();
	bt->insertLeftChild(btnb, "d");
	bt->insertLeftChild(btnc, "e");
	bt->insertRightChild(btnc, "f");

	StringBinaryTree::BinaryTreeNode		&btnd = btnb.getLeftChild();
	StringBinaryTree::BinaryTreeNode		&btne = btnc.getLeftChild();
	StringBinaryTree::BinaryTreeNode		&btnf = btnc.getRightChild();
	bt->insertLeftChild(btnd, "g");
	bt->insertRightChild(btnd, "h");
	bt->insertRightChild(btne, "i");
	bt->insertLeftChild(btnf, "j");

	StringBinaryTree::BinaryTreeNode		&btnh = btnd.getRightChild();
	bt->insertLeftChild(btnh, "k");
}

bool inOrderCheck(StringBinaryTree *bt){
	int i;
	cout << "\n**Inorder Iteration:\nYours\tCorrect" << endl;
	bool result = true;

	StringBinaryTree::InOrderIterator itr = bt->inOrderBegin();
	for (i = 0; itr != bt->inOrderEnd(); ++itr, ++i){
		cout << *itr << "\t" << inOrder[i] << endl;
		if (*itr != inOrder[i])
			result = false;
	}

	if (i != arrSize)
		cout << "Iteration terminated immaturely.\n";

	return result;
}

bool inOrderReverseCheck(StringBinaryTree *bt){
	int i;
	cout << "\n**Inorder Reverse Iteration:\nYours\tCorrect" << endl; bool result = true;

	StringBinaryTree::InOrderIterator itr = bt->inOrderReverseBegin();
	for (i = arrSize - 1; itr != bt->inOrderReverseEnd(); --itr, --i){
		cout << *itr << "\t" << inOrder[i] << endl;
		if (*itr != inOrder[i])
			result = false;
	}

	if (i != -1)
		cout << "Iteration terminated immaturely.\n";

	return result;
}

bool preOrderCheck(StringBinaryTree *bt){
	int i;
	cout << "\n**PreOrder Iteration:\nYours\tCorrect" << endl;
	bool result = true;

	StringBinaryTree::PreOrderIterator itr = bt->preOrderBegin();
	for (i = 0; itr != bt->preOrderEnd(); ++itr, ++i){
		cout << *itr << "\t" << preOrder[i] << endl;
		if (*itr != preOrder[i])
			result = false;
	}
	if (i != arrSize)
		cout << "Iteration terminated immaturely.\n";

	return result;
}

bool preOrderReverseCheck(StringBinaryTree *bt){
	int i;
	cout << "\n**PreOrder Reverse Iteration:\nYours\tCorrect" << endl;
	bool result = true;

	StringBinaryTree::PreOrderIterator itr = bt->preOrderReverseBegin();
	for (i = arrSize - 1; itr != bt->preOrderReverseEnd(); --itr, --i){
		cout << *itr << "\t" << preOrder[i] << endl;
		if (*itr != preOrder[i])
			result = false;
	}

	if (i != -1)
		cout << "Iteration terminated immaturely.\n";

	return result;
}

bool postOrderCheck(StringBinaryTree *bt){
	int i;
	cout << "\n**PostOrder Iteration:\nYours\tCorrect" << endl;
	bool result = true;

	StringBinaryTree::PostOrderIterator itr = bt->postOrderBegin();
	for (i = 0; itr != bt->postOrderEnd(); ++itr, ++i){
		cout << *itr << "\t" << postOrder[i] << endl;
		if (*itr != postOrder[i])
			result = false;
	}

	if (i != arrSize)
		cout << "Iteration terminated immaturely.\n";

	return result;
}

bool postOrderReverseCheck(StringBinaryTree *bt){
	int i;
	cout << "\n**PostOrder Reverse Iteration:\nYours\tCorrect" << endl;
	bool result = true;

	StringBinaryTree::PostOrderIterator itr = bt->postOrderReverseBegin();
	for (i = arrSize - 1; itr != bt->postOrderReverseEnd(); --itr, --i){
		cout << *itr << "\t" << postOrder[i] << endl;
		if (*itr != postOrder[i])
			result = false;
	}
	if (i != -1)
		cout << "Iteration terminated immaturely.\n";


	return result;
}

int main(){
	int input;
	bool result = false;
	StringBinaryTree*	bt = new StringBinaryTree;

	LoadBinaryTree(bt);
	bt->setNodeDisplayWidth(1);
	bt->draw(cout);

	cout << "\nChoose an Iteration:" << endl;
	cout << "1: inOrder Iteration" << endl;
	cout << "2: inOrder Reverse Iteration" << endl;
	cout << "3: preOrder Iteration" << endl;
	cout << "4: preOrder Reverse Iteration" << endl;
	cout << "5: postOrder Iteration" << endl;
	cout << "6: postOrder Reverse Iteration" << endl;
	cout << "7: exit" << endl;

	for (input =1; input < 7; input++)
	{
		switch (input) {
		case 1:
			result = inOrderCheck(bt);
			break;
		case 2:
			result = inOrderReverseCheck(bt);
			break;
		case 3:
			result = preOrderCheck(bt);
			break;
		case 4:
			result = preOrderReverseCheck(bt);
			break;
		case 5:
			result = postOrderCheck(bt);
			break;
		case 6:
			result = postOrderReverseCheck(bt);
			break;
		}

		if (result)
			cout << "** That was correct!" << endl;
		else
		{
			cout << "** Doesn't match." << endl;
			cout << "Your code did not pass the tests." << endl;
			int dummy;
			cin >> dummy;
			return 0;
		}
	}

	cout << "Your code passed all the tests." << endl;
	int dummy;
	cin >> dummy;

	delete bt;
	return 0;
}
