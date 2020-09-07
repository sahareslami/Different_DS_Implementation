package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeForwardPostorderIterator<T, IBTN extends InternalBinaryTreeNode<T> > implements Iterator<T> {
	private IBTN mCurrentNode;
	private Stack<IBTN> mParentsStack;
	private BinaryTree<T, IBTN> mBinaryTree;

	//constructors:
	public BinaryTreeForwardPostorderIterator()
	{
	}

	// The binaryTree parameter is used to initialize the mCurrentNode to
	// the appropriate dummy root. In addition, it is used to initialize mParentsStack	
	public BinaryTreeForwardPostorderIterator(BinaryTree<T, IBTN> binaryTree)
	{
	    mBinaryTree = binaryTree;
	    mParentsStack = new Stack<>();
	    mCurrentNode = mBinaryTree.mDummyRoot2;
	    mParentsStack.push(mBinaryTree.mDummyRoot1);
	}

	//overloading operators:
	@Override
	public T next() {
	    IBTN parentNode = mParentsStack.peek();
		// if current node is left child of his father and has sibiling : the left most child with highest height in right subtree of current node parents is next node
        if(parentNode.mLeftChild == mCurrentNode && parentNode.mRightChild != null){
            IBTN node = (IBTN) parentNode.mRightChild;
            while(node.mLeftChild != null || node.mRightChild != null){
                mParentsStack.push(node);
                if(node.mLeftChild != null)
                    node = (IBTN)node.mLeftChild;
                else
                    node = (IBTN)node.mRightChild;

            }
            mCurrentNode = node;
        }
        //if current node left child of his father and has not sibilig : next node is his father
        else if(parentNode.mLeftChild == mCurrentNode && parentNode.mRightChild ==null && parentNode.mRightChild != mBinaryTree.mDummyRoot4){
            mCurrentNode = parentNode;
            mParentsStack.pop();
        }
        //if current node is right child of his father : next node is parent node
        else if(parentNode.mRightChild == mCurrentNode){
            mCurrentNode = parentNode;
            mParentsStack.pop();
        }
        return mCurrentNode.mData;
	}

	@Override
	public boolean hasNext() {
	    //last node is root
        IBTN lastNode = (IBTN)mBinaryTree.mDummyRoot3.mLeftChild;
        if(mCurrentNode == lastNode) return false;
        return true;
	}
}
