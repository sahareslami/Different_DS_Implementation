package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeBackwardPostorderIterator<T, IBTN extends InternalBinaryTreeNode<T> > implements Iterator<T> {
	private IBTN mCurrentNode;
	private Stack<IBTN> mParentsStack;
	private BinaryTree<T, IBTN> mBinaryTree;

	//constructors:
	public BinaryTreeBackwardPostorderIterator()
	{
	}

	// The binaryTree parameter is used to initialize the mCurrentNode to
	// the appropriate dummy root. In addition, it is used to initialize mParentsStack	
	public BinaryTreeBackwardPostorderIterator(BinaryTree<T, IBTN> binaryTree){
	    mBinaryTree = binaryTree;
	    mParentsStack = new Stack<>();
	    mCurrentNode = mBinaryTree.mDummyRoot4;
	    mParentsStack.push(mBinaryTree.mDummyRoot3);
	    mParentsStack.push(mBinaryTree.mDummyRoot4);
    }
	//overloading operators
	@Override
	public T next() {
        //if current node has right child : next node is his right child
        if(mCurrentNode.mRightChild != null){
            mCurrentNode = (IBTN)mCurrentNode.mRightChild;
            mParentsStack.push(mCurrentNode);
        }

        //if current node has left child : next node is his left child
        else if(mCurrentNode.mLeftChild != null){
            mCurrentNode = (IBTN) mCurrentNode.mLeftChild;
            mParentsStack.push(mCurrentNode);
        }
        //current node has not neither right child nor left child : go to parent till current node appear in right subtree
        else{
            IBTN childNode = mParentsStack.peek();
            mParentsStack.pop();
            IBTN parentNode = mParentsStack.peek();
            while((parentNode.mRightChild == childNode && parentNode.mLeftChild == null) ||
                    parentNode.mLeftChild == childNode){
                childNode = parentNode;
                mParentsStack.pop();
                parentNode = mParentsStack.peek();

            }
            mCurrentNode = (IBTN)mParentsStack.peek().mLeftChild;
            mParentsStack.push(mCurrentNode);
        }
        return mCurrentNode.mData;
	}

	@Override
	public boolean hasNext(){
	    IBTN lastNode = (IBTN)mBinaryTree.mDummyRoot3.mLeftChild;
        while(lastNode.mRightChild != null || lastNode.mLeftChild != null){
            if(lastNode.mLeftChild != null)
                lastNode = (IBTN)lastNode.mLeftChild;
            else
                lastNode = (IBTN)lastNode.mRightChild;
        }
        if(mCurrentNode == lastNode) return false;
        return true;


	}
}
