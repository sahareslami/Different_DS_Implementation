package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeBackwardInorderIterator<T, IBTN extends InternalBinaryTreeNode<T> > implements Iterator<T> {
	private IBTN mCurrentNode;
	private Stack<IBTN> mParentsStack;
	private BinaryTree<T, IBTN> mBinaryTree;

	//constructors:
	public BinaryTreeBackwardInorderIterator()
	{
	}

	// The binaryTree parameter is used to initialize the mCurrentNode to
	// the appropriate dummy root. In addition, it is used to initialize mParentsStack
	public BinaryTreeBackwardInorderIterator(BinaryTree<T, IBTN> binaryTree)
	{
	    mBinaryTree = binaryTree;
	    mParentsStack = new Stack<>();
	    mCurrentNode = mBinaryTree.mDummyRoot3;
	    mParentsStack.push(mCurrentNode);
	}

	//overloading operators
	@Override
	public T next() {
		//if current node has left subtree: right most node in left subtree is next node
        if(mCurrentNode.mLeftChild != null){
            IBTN node = (IBTN)mCurrentNode.mLeftChild;
            while(node.mRightChild != null){
                mParentsStack.push(node);
                node = (IBTN)node.mRightChild;
            }
            mCurrentNode = node;
            mParentsStack.push(mCurrentNode);
        }
        //if current node has not left child : go to parent till the current node appear in right subtree
        else{
            IBTN node = mParentsStack.peek();
            mParentsStack.pop();
            while(mParentsStack.peek().mRightChild != node && node != mBinaryTree.mDummyRoot1){
                node = mParentsStack.peek();
                mParentsStack.pop();
            }
            mCurrentNode = mParentsStack.peek();
        }
        return mCurrentNode.mData;
	}

	@Override
	public boolean hasNext() {
		IBTN leftMost = mBinaryTree.mDummyRoot3;
		//leftMost = leftMost.mLeftChild;
		while(leftMost.mLeftChild != null)
		    leftMost = (IBTN)leftMost.mLeftChild;
		if(mCurrentNode == leftMost) return false;
		return true;
	}
}
