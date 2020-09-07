#pragma once
#include <ostream>



template <class T>
class LinkedLinearList{
private:
		class Node{
		public:
			T	mData;
			Node*	mPrev;
			Node*	mNext;
		};

public:
	class Iterator{
	public:
		//constructors:
		Iterator ()
			:mCurNode (NULL){}

		Iterator (const Iterator &other)
			:mCurNode (other.mCurNode){}
		
		Iterator (Node* node)
			:mCurNode (node){}
		
		//overloading operators:
		virtual Iterator& operator++(){ // preincrement
			mCurNode = mCurNode->mNext;
			return *this;

		}

		virtual Iterator operator++(int dummy){ // postincrement
			Iterator it = *this;
			mCurNode = mCurNode->mNext;
			return it;

		}

		virtual Iterator& operator--(){ // predecrement
			mCurNode = mCurNode->mPrev;
			return *this;
		}

		virtual Iterator operator--(int dummy){ // postdecrement
			Iterator it = *this;
			mCurNode = mCurNode->mPrev;
			return it;
		}

		bool operator!=(const Iterator& right)const {
			return mCurNode != right.mCurNode;
		}

		bool operator==(const Iterator& right)const {
			return mCurNode == right.mCurNode;
		}

		T* operator->() const {
			return &(mCurNode->mData);
		}

		T& operator*() const {
			return mCurNode->mData;
		}

		friend class LinkedLinearList;
	private:
		//const LinkedLinearList<T> *mLinkedList;
		Node *mCurNode;
	};

	LinkedLinearList(void){
		mSize = 0;
		mHeaderNode = new Node();
		mHeaderNode->mNext = mHeaderNode;
		mHeaderNode->mPrev = mHeaderNode;
	}

	virtual ~LinkedLinearList(void) {
		Node *n, *nn; 
		for (n = mHeaderNode->mNext; n != mHeaderNode;){
			nn = n->mNext;
			delete n;
			n = nn;
		}
		delete mHeaderNode;
	}

public:
	virtual Iterator begin() const {
		Iterator beginNode;
		beginNode.mCurNode = mHeaderNode->mNext;
		return beginNode;
	}

	virtual Iterator end() const {
		Iterator endNode;
		endNode.mCurNode = mHeaderNode;
		return endNode;
	}

	virtual Iterator rbegin() const {
		Iterator rbeginNode;
		rbeginNode.mCurNode = mHeaderNode->mPrev;
		return rbeginNode;
	}

	virtual Iterator rend() const {
		Iterator rendNode;
		rendNode.mCurNode = mHeaderNode;
		return rendNode;
	}

	// The returned iterator would point to the inserted element.
	virtual Iterator insert (Iterator it, const T& data) {
		mSize++;
		Iterator iit;
		Node* newNode = new Node();
		newNode->mData = data;
		newNode->mNext = it.mCurNode->mNext;
		newNode->mPrev = it.mCurNode;

		it.mCurNode->mNext = newNode;
		it.mCurNode->mNext->mNext->mPrev = newNode;


		iit.mCurNode = newNode;
		return iit;

	}

	// The returned iterator would point to the element after the removed one.
	virtual Iterator remove (Iterator it){
		Node* toDelete = it.mCurNode;
		mSize--;
		Iterator iit;

		it.mCurNode->mPrev->mNext = it.mCurNode->mNext;
		it.mCurNode->mNext->mPrev = it.mCurNode->mPrev;

		iit.mCurNode = it.mCurNode->mNext;
		delete toDelete;
		return iit;

	}

	virtual int size() const {
		return mSize;
	};

private:
	Node* mHeaderNode;
	int mSize;
};

template <class T>
std::ostream& operator<<(std::ostream& os, const LinkedLinearList<T>& ll){
	LinkedLinearList<T>::Iterator itr;

	for (itr = ll.begin(); itr != ll.end(); itr++)
		os << *itr << "\t";
	os << "\n";
	return os;
}
