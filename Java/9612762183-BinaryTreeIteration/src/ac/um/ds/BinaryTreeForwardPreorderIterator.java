package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeForwardPreorderIterator<T, IBTN extends InternalBinaryTreeNode<T> > implements Iterator<T> {
	private IBTN mCurrentNode;
	private Stack<IBTN> mParentsStack;
	private BinaryTree<T, IBTN> mBinaryTree;

	//constructors:
	public BinaryTreeForwardPreorderIterator()
	{
	}

	// The binaryTree parameter is used to initialize the mCurrentNode to
	// the appropriate dummy root. In addition, it is used to initialize mParentsStack
	public BinaryTreeForwardPreorderIterator(BinaryTree<T, IBTN> binaryTree)
	{
		mBinaryTree = binaryTree;
		mParentsStack = new Stack<>();
		mCurrentNode = mBinaryTree.mDummyRoot3;
		mParentsStack.push(mCurrentNode);
	}
	
	//overloading operators:
	@Override
	public T next() {
		//if current node has left child : next node is his left child
        if(mCurrentNode.mLeftChild != null){
            mCurrentNode = (IBTN)mCurrentNode.mLeftChild;
            mParentsStack.push(mCurrentNode);
        }

        //if current node has right child : next node is his right child
        else if(mCurrentNode.mRightChild != null){
            mCurrentNode = (IBTN) mCurrentNode.mRightChild;
            mParentsStack.push(mCurrentNode);
        }
        //current node has not neither right child nor left child : go to parent till current node appear in left subtree
        else{
            IBTN childNode = mParentsStack.peek();
            mParentsStack.pop();
            IBTN parentNode = mParentsStack.peek();
            while(((childNode == parentNode.mLeftChild && parentNode.mRightChild == null) ||
                    (childNode == parentNode.mRightChild))
                    && childNode != mBinaryTree.mDummyRoot4){
                childNode = mParentsStack.peek();
                mParentsStack.pop();
                parentNode = mParentsStack.peek();
            }
            mCurrentNode = (IBTN)mParentsStack.peek().mRightChild;
            mParentsStack.push(mCurrentNode);
        }
        return mCurrentNode.mData;
	}

	@Override
	public boolean hasNext() {

		//last visited node in preorder traversal is most right child in with highest height
        IBTN lastNode = mBinaryTree.mDummyRoot3;
        lastNode = (IBTN)lastNode.mLeftChild;
        while(lastNode.mRightChild != null || lastNode.mLeftChild != null){
            if(lastNode.mRightChild != null)
                lastNode = (IBTN)lastNode.mRightChild;
            else
                lastNode = (IBTN)lastNode.mLeftChild;
        }
        if(mCurrentNode == lastNode) return false;
        return true;
	}
}
