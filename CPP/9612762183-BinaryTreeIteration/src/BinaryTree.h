// Copyright (C) Kamaledin Ghiasi-Shirazi, Ferdowsi Univerity of Mashhad, 2018 (1397 Hijri Shamsi)
//
// Author: Kamaledin Ghiasi-Shirazi

#pragma once

#include <stack>
#include "InternalBinaryTreeNode.h"

using namespace std;

//template <class T, class IBTN = InternalBinaryTreeNode<T>>
template <class T, class IBTN>
class BinaryTree{
public:

	class BinaryTreeNode{
	public:
		virtual ~BinaryTreeNode(void){}
		virtual T&	getData(){return mActualNode->getData();}
		virtual void	setData(const T& data){ return mActualNode->setData(data); }
		virtual bool	hasLeftChild() const{ return mActualNode->getLeftChild() != 0; }
		virtual BinaryTreeNode	getLeftChild()const { return BinaryTreeNode(mActualNode->getLeftChild()); }
		virtual bool	hasRightChild() const { return mActualNode->getRightChild() != 0; }
		virtual BinaryTreeNode	getRightChild() const { return BinaryTreeNode(mActualNode->getRightChild()); }

	private:
		template <class T, class R>
		friend class BinaryTree;
		BinaryTreeNode(IBTN* node){mActualNode = node;}

		IBTN	*mActualNode;
	};


public:
	class Iterator{
	public:
		Iterator (IBTN* currentNode): mCurrentNode(currentNode){}
		virtual T& operator*(){return mCurrentNode->mData;}
		virtual T* operator->(){return &mCurrentNode->mData;}
		virtual bool operator==(Iterator& itr){return mCurrentNode == itr.mCurrentNode;}
		virtual bool operator!=(Iterator& itr){return mCurrentNode != itr.mCurrentNode;}
		virtual void operator++() = 0;
		virtual void operator--() = 0;
	protected:
		IBTN*			mCurrentNode;
		stack<IBTN*>	mParents;

		friend class BinaryTree<T, InternalBinaryTreeNode<T>>;
	};

	class InOrderIterator: public Iterator{
	public:
		InOrderIterator(IBTN* currentNode) : Iterator(currentNode) {}
		virtual void operator++(){
			//if current node has right child : left most node in right subtree of current node is next node
			 if(mCurrentNode->mRightChild){
				 IBTN* node = mCurrentNode->mRightChild;
				 while(node->mLeftChild){
					 mParents.push(node);
					 node = node->mLeftChild;
				 } 
				 mCurrentNode = node;
				 mParents.push(mCurrentNode);
			 }
			 //if current node has not left child : go  up till mcurrent node appear in left subtree
			 else{
				 IBTN* childNode = mParents.top();
				 mParents.pop();
				 while(mParents.top()->mLeftChild != childNode){
					 childNode = mParents.top();
					 mParents.pop();
				 }
				 mCurrentNode = mParents.top();
			 }
		}

		virtual void operator--(){
			//if current node has left child : right most child in left subtree
			if(mCurrentNode->mLeftChild){
				IBTN* node = mCurrentNode->mLeftChild;
				while(node->mRightChild){
					mParents.push(node);
					node = node->mRightChild;
				}
				mCurrentNode = node;
				mParents.push(mCurrentNode);
			}
			//if current node has not left child : go up till current node appear in right sutree
			else{
				IBTN* node = mParents.top();
				mParents.pop();
				while(mParents.top()->mRightChild != node){
					node = mParents.top();
					mParents.pop();
				}
				mCurrentNode = mParents.top();
			}
		}
	};

	class PreOrderIterator : public Iterator{
	public:
		PreOrderIterator(IBTN* currentNode) : Iterator(currentNode){}
		virtual void operator++(){
			//if current node has left child : next node is left child
			if(mCurrentNode->mLeftChild){
				mCurrentNode = mCurrentNode->mLeftChild;
				mParents.push(mCurrentNode);
			}
			//if current node has not left child but has right child : next node is right child
			else if(mCurrentNode->mRightChild){
				mCurrentNode = mCurrentNode->mRightChild;
				mParents.push(mCurrentNode);
			}
			//current node is leaf : go up till current node appear in left subtree
			else{
				IBTN* childNode = mParents.top();
				mParents.pop();
				IBTN* parentNode = mParents.top();
				while((childNode == parentNode->mLeftChild && !parentNode->mRightChild) ||
					childNode == parentNode->mRightChild){
					childNode = mParents.top();
					mParents.pop();
					parentNode = mParents.top();
				}
				mCurrentNode = mParents.top()->mRightChild;
				mParents.push(mCurrentNode);
			}
		}


		virtual void operator--(){
			IBTN* parentNode = mParents.top();
			//if current node is right child of his father and have sibiling : go to left child of his father and go to left most child with highest height
			if(parentNode->mRightChild == mCurrentNode && parentNode->mLeftChild){
				IBTN* node = parentNode->mLeftChild;
				while(node->mRightChild || node->mLeftChild){
					mParents.push(node);
					if(node->mRightChild)
						node = node->mRightChild;
					else
						node = node->mLeftChild;
				}
				mCurrentNode = node;
			}
			//if current node is right child of his father and has not sibiling  : next node is father node
			else if(parentNode->mRightChild == mCurrentNode && !parentNode->mLeftChild){
				mParents.pop();
				mCurrentNode = parentNode;
			}
			//if current node is left child of his father : next node is father node
			else if(parentNode->mLeftChild == mCurrentNode){
				mCurrentNode = parentNode;
				mParents.pop();
			}
		}
	};

	class PostOrderIterator: public Iterator{
	public:
		PostOrderIterator (IBTN* currentNode): Iterator (currentNode){}
		virtual void operator++(){
			IBTN* parentNode = mParents.top();
			//if current node is left child of his father and has sibiling : next node is left most child with hightest height
			if(parentNode->mLeftChild == mCurrentNode && parentNode->mRightChild){
				IBTN* node = parentNode->mRightChild;
				while(node->mLeftChild || node->mRightChild){
					mParents.push(node);
					if(node->mLeftChild)
						node = node->mLeftChild;
					else
						node = node->mRightChild;
				}
				mCurrentNode = node;
			}
			//if current node is left child of his father and has no sibiling : next node is his father
			else if(parentNode->mLeftChild == mCurrentNode && !parentNode->mRightChild){
				mCurrentNode = parentNode;
				mParents.pop();
			}
			//if current node is right child of his father : next node is parent node
			else if(parentNode->mRightChild == mCurrentNode){
				mCurrentNode = parentNode;
				mParents.pop();
			}

		}

		virtual void operator--(){
			//if current node has right child : next node is his right child
			if(mCurrentNode->mRightChild){
				mCurrentNode = mCurrentNode->mRightChild;
				mParents.push(mCurrentNode);
			}
			//if current node has left child : next node is his left child
			else if(mCurrentNode->mLeftChild){
				mCurrentNode = mCurrentNode->mLeftChild;
				mParents.push(mCurrentNode);
			}
			//if current node has not any child: go to parents till current node appear in right subtree
			else{
				IBTN* childNode = mParents.top();
				mParents.pop();
				IBTN* parentNode = mParents.top();
				while((parentNode->mRightChild == childNode && !parentNode->mLeftChild) ||
					parentNode->mLeftChild == childNode){
						childNode = parentNode;
						mParents.pop();
						parentNode = mParents.top();
				}
				mCurrentNode = mParents.top()->mLeftChild;
				mParents.push(mCurrentNode);
			}
		}
	};

protected:

	IBTN* mDummyRoot1;
	IBTN* mDummyRoot2;
	IBTN* mDummyRoot3;
	IBTN* mDummyRoot4;

	/* 
						       1
						--------------
						2              3
							    -------------
								root         4
	*/
	
	int		mNodeDisplayWidth;
	int		mSize;

public:
	BinaryTree(void){
		mNodeDisplayWidth = 2;
		mSize = 0;

		mDummyRoot1 = new IBTN();
		mDummyRoot2 = new IBTN();
		mDummyRoot3 = new IBTN();
		mDummyRoot4 = new IBTN();

		mDummyRoot1->mLeftChild = mDummyRoot2;
		mDummyRoot1->mRightChild = mDummyRoot3;

		mDummyRoot2->mLeftChild = 0;
		mDummyRoot2->mRightChild = 0;

		mDummyRoot3->mRightChild = mDummyRoot4;
		mDummyRoot3->mLeftChild = 0;

		mDummyRoot4->mLeftChild = 0;
		mDummyRoot4->mRightChild = 0;

	}

	// caution: don't interpret virtual destrucor as an ordinary virtual function!
	virtual ~BinaryTree(void){
		
		DeleteSubtree(mDummyRoot1);
	}

	int	size(){ return mSize; }
	void DeleteSubtree (IBTN* node){
		if (node->mLeftChild)
			DeleteSubtree (node->mLeftChild);
		if (node->mRightChild)
			DeleteSubtree (node->mRightChild);
		delete node;
	}

	bool	isEmpty(){
		return mDummyRoot3->mLeftChild == 0;
	}

	virtual void	insertRootNode(T data){
		IBTN* root;
		if(mDummyRoot3->mLeftChild)
			throw("ERROR : Root already exists");
		root = new IBTN();
		root->mData = data;
		mDummyRoot3->mLeftChild = root;
		mSize = 1;
	
	}

	virtual BinaryTreeNode	getRootNode(){
		return BinaryTreeNode(mDummyRoot3->mLeftChild);
		
	}

	// error if a left child already exists.
	virtual void 	insertLeftChild(const BinaryTreeNode& parentNode, T data){
		IBTN* left;
		IBTN* parent = parentNode.mActualNode;
		if(parent->mLeftChild)
			throw("ERROR : left child already exists.");
		left = new IBTN();
		left->mData = data;
		parent->mLeftChild = left;

		mSize++;
	}


	// error if a right child already exists.
	virtual void insertRightChild(const BinaryTreeNode& parentNode, T data){
		IBTN* right;
		IBTN* parent = parentNode.mActualNode;
		if(parent->mRightChild)
			throw("ERROR : right child already exists.");
		right = new IBTN();
		right->mData = data;
		parent->mRightChild = right;

		mSize++;
	}

	virtual void 	deleteLeftChild(const BinaryTreeNode& node) {
		IBTN *theParent = node.mActualNode;
		IBTN *theNode = theParent->getLeftChild();		
		deleteNode(theNode, theParent);
	}

	virtual void 	deleteRightChild(const BinaryTreeNode& node) {
		IBTN *theParent = node.mActualNode;
		IBTN *theNode = theParent->getRightChild();
		deleteNode(theNode, theParent);
	}
	
	virtual InOrderIterator inOrderBegin(){
		BinaryTree<T,IBTN>::InOrderIterator itr = this->inOrderReverseEnd();
		++itr;
		return itr;
	}

	virtual InOrderIterator inOrderEnd(){
		//inorder end is mDummyRoot3
		return InOrderIterator(mDummyRoot3);
	}

	virtual InOrderIterator inOrderReverseBegin(){
		BinaryTree<T,IBTN>::InOrderIterator itr = this->inOrderEnd();
		(itr.mParents).push(mDummyRoot1);
		(itr.mParents).push(mDummyRoot3);
		--itr;
		return itr;
	}

	virtual InOrderIterator inOrderReverseEnd(){
		// in order Reverse end is mDummyRoot1
		return InOrderIterator(mDummyRoot1);
	}

	virtual PreOrderIterator preOrderBegin(){
		BinaryTree<T,IBTN>::PreOrderIterator itr = this->preOrderReverseEnd();
		(itr.mParents).push(mDummyRoot3);
		++itr;
		return itr;
		
	}

	virtual PreOrderIterator preOrderEnd(){
		//pre order end is  mDummyRoot4
		return PreOrderIterator(mDummyRoot4);
	}

	virtual PreOrderIterator preOrderReverseBegin(){
		BinaryTree<T,IBTN>::PreOrderIterator itr = this->preOrderEnd();
		(itr.mParents).push(mDummyRoot3);
		--itr;
		return itr;
		
	}

	virtual PreOrderIterator preOrderReverseEnd(){
		//Reverse PreOrder End is mDummyroot3
		return PreOrderIterator(mDummyRoot3);
	}

	virtual PostOrderIterator postOrderBegin(){
		BinaryTree<T,IBTN>::PostOrderIterator itr = this->postOrderReverseEnd();
		(itr.mParents).push(mDummyRoot1);
		++itr;
		return itr;
	}

	virtual PostOrderIterator postOrderEnd(){
		//post order reverse end is mDummyRoot4
		return PostOrderIterator(mDummyRoot4);
		
	}

	virtual PostOrderIterator postOrderReverseBegin(){
		BinaryTree<T,IBTN>::PostOrderIterator itr = this->postOrderEnd();
		(itr.mParents).push(mDummyRoot1);
		(itr.mParents).push(mDummyRoot3);
		(itr.mParents).push(mDummyRoot4);
		--itr;
		return itr;
	}
	
	virtual PostOrderIterator postOrderReverseEnd(){
		//Post order reverse end is mDummyroot2
		return PostOrderIterator(mDummyRoot2);
	}
	
	void setNodeDisplayWidth(int width){
		mNodeDisplayWidth = width;
	}

	// This function is solely written to work on small binary trees.
	// The code has many other known limitations.
	void draw(std::ostream& out){
		if (mSize == 0)
			return;

		int maxDepth = 9;
		int depth = depthCalc(getRootNode().mActualNode, 1);
		depth = depth * 2 - 1;


		if (depth > maxDepth){
			out << "Can't draw, the height of the tree is greater than " << (maxDepth + 1) / 2 << "\n";
			return;
		}

		char **map = new char *[depth];
		for (int i = 0; i < depth; i++){
			map[i] = new char[80];
			for (int j = 0; j < 80; j++)
				map[i][j] = ' ';
		}

		recursiveDraw(getRootNode().mActualNode, map, 40, 0);

		for (int i = 0; i < depth; i++)
		for (int j = 0; j < 80; j++)
			out << map[i][j];

		for (int i = 0; i < depth; i++)
			delete[] map[i];
		delete[] map;
	}

protected:
	// Only leaf nodes and nodes with degree 1 can be deleted. 
	// If a degree-1 node is deleted, it is replaced by its subtree.
	virtual void 	deleteNode(IBTN* theNode, IBTN* theParent) {
		//if deleted node is leaf
		if(!theNode->mLeftChild && !theNode->mRightChild){
			//if deleted node is left child of his father
			if(theParent->mLeftChild == theNode)
				theParent->mLeftChild = 0;
			if(theParent->mRightChild == theNode)
				theParent->mRightChild = 0;
			delete theNode;
		}
		//if a degree-1 node deleted and have left child
		else if(theNode->mLeftChild != 0 && theNode->mRightChild == 0){
			//if deleted node is left child of his father
			if(theParent->mLeftChild == theNode)
				theParent->mLeftChild = theNode->mLeftChild;
			//if deleted node is right child of his father
			if(theParent->mRightChild == theNode)
				theParent->mRightChild = theNode->mLeftChild;
			delete theNode;
		}
		//if a degree-1 node deleted and have right child
		else if(theNode->mRightChild != 0 && theNode->mLeftChild == 0){
			//if deleted node is left child of his father
			if(theParent->mLeftChild == theNode)
				theParent->mLeftChild = theNode->mRightChild;
			//if deleted node is right child of his father
			if(theParent->mRightChild == theNode)
				theParent->mRightChild = theNode->mRightChild;
			delete theNode;
		}
		mSize--;
	}	
	int depthCalc(IBTN* root, int depth){
		int res = depth;
		if (root->mRightChild){
			int rightDepth = depthCalc(root->mRightChild, depth + 1);
			res = (res > rightDepth) ? res : rightDepth;
		}
		if (root->mLeftChild){
			int leftDepth = depthCalc(root->mLeftChild, depth + 1);
			res = (res > leftDepth) ? res : leftDepth;
		}
		return res;
	}

	void recursiveDraw(IBTN* root, char** lines, int x, int y){
		int des = 1;
		for (int i = 0; i < y / 2 + 2; i++)
			des *= 2;
		des = 80 / des;
		//root:
		for (int i = 0; i < mNodeDisplayWidth; i++)
			lines[y][x + i - mNodeDisplayWidth / 2] = (root->mData)[i];
		//left child:
		if (root->mLeftChild){
			for (int i = 0; i < des; i++)
				lines[y + 1][x - i] = '-';
			lines[y + 1][x] = '|';
			recursiveDraw(root->mLeftChild, lines, x - des, y + 2);
		}
		//right child:
		if (root->mRightChild){
			for (int i = 0; i < des; i++)
				lines[y + 1][x + i] = '-';
			lines[y + 1][x] = '|';
			recursiveDraw(root->mRightChild, lines, x + des, y + 2);
		}
	}

protected:
	virtual IBTN*	getActualNode(const BinaryTreeNode& node){ return node.mActualNode; }
};

