package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeBackwardPreorderIterator<T, IBTN extends InternalBinaryTreeNode<T> > implements Iterator<T> {
	private IBTN mCurrentNode;
	private Stack<IBTN> mParentsStack;
	private BinaryTree<T, IBTN> mBinaryTree;

	//constructors:
	public BinaryTreeBackwardPreorderIterator()
	{
	}
	// The binaryTree parameter is used to initialize the mCurrentNode to
	// the appropriate dummy root. In addition, it is used to initialize mParentsStack	
	public BinaryTreeBackwardPreorderIterator(BinaryTree<T, IBTN> binaryTree)
	{
	    mBinaryTree = binaryTree;
	    mParentsStack = new Stack<>();
	    mCurrentNode = mBinaryTree.mDummyRoot4;
	    mParentsStack.push(mBinaryTree.mDummyRoot3);

	}
	
	//overloading operators:
	@Override
	public T next(){
	    IBTN parentNode = mParentsStack.peek();
	    //if current node is right child of his father and have sibiling: go to left child of his father and go to right most node with highest height
        if(parentNode.mRightChild == mCurrentNode && parentNode.mLeftChild != null){
            IBTN node = (IBTN)parentNode.mLeftChild;
            while(node.mRightChild != null || node.mLeftChild != null){
                mParentsStack.push(node);
                if(node.mRightChild != null)
                    node = (IBTN)node.mRightChild;
                else
                    node = (IBTN) node.mLeftChild;
            }
            mCurrentNode = node;
        }
        //if current node is right child of his father and doesnot have sibiling : next node is father node
        else if(parentNode.mRightChild == mCurrentNode && parentNode.mLeftChild == null){
            mParentsStack.pop();
            mCurrentNode = parentNode;
        }
        //if current node is left child of his father : next node is father node
        else if(parentNode.mLeftChild == mCurrentNode && parentNode != mBinaryTree.mDummyRoot3){
            mCurrentNode = parentNode;
            mParentsStack.pop();
        }
        return mCurrentNode.mData;
	}

	@Override
	public boolean hasNext() {
		//last element is root
        IBTN lastNode = (IBTN)mBinaryTree.mDummyRoot3.mLeftChild;
        if(lastNode == mCurrentNode) return false;
        return true;

	}


}
