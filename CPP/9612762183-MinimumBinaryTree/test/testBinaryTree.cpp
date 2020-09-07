#include "../src/BinaryTree.h"
#include <iostream>
#include <time.h>

using namespace std;

typedef BinaryTree<string, InternalBinaryTreeNode<string>>  IBT;

void LoadBinaryTree(IBT	*bt){
	/*
	                                                      a
													/		      \
												b		            c
											/		         	/   	\
									  	   d		           e          f
								         g    h                 i
										 	 k
	*/

	bt->insertRootNode("a");

	IBT::BinaryTreeNode		btna(bt->getRootNode());
//	IBT::BinaryTreeNode		&btna = bt->getRootNode();
	bt->insertLeftChild(btna, "b");
	bt->insertRightChild(btna, "c");

	IBT::BinaryTreeNode		&btnb = btna.getLeftChild();
	IBT::BinaryTreeNode		&btnc = btna.getRightChild();
	bt->insertLeftChild(btnb, "d");
	bt->insertLeftChild(btnc, "e");
	bt->insertRightChild(btnc, "f");

	IBT::BinaryTreeNode		&btnd = btnb.getLeftChild();
	IBT::BinaryTreeNode		&btne = btnc.getLeftChild();
	IBT::BinaryTreeNode		&btnf = btnc.getRightChild();
	bt->insertLeftChild(btnd, "g");
	bt->insertRightChild(btnd, "h");
	bt->insertRightChild(btne, "i");

	IBT::BinaryTreeNode		&btnh = btnd.getRightChild();
	
	bt->insertLeftChild(btnh, "k");

	
}

void testBinaryTree()
{
	IBT*	bt = new IBT;
	srand(time(NULL));

	LoadBinaryTree(bt);
	bt->setNodeDisplayWidth(1);
	bt->draw(cout);

	delete bt;
}

int main()
{
	testBinaryTree();

	int dummy;
	cin >> dummy;
}
