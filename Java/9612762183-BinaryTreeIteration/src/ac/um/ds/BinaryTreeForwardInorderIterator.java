package ac.um.ds;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTreeForwardInorderIterator<T, IBTN extends InternalBinaryTreeNode<T> > implements Iterator<T> {
	private IBTN mCurrentNode;
	private Stack<IBTN> mParentsStack;
	private BinaryTree<T, IBTN> mBinaryTree;

	//constructors:
	public BinaryTreeForwardInorderIterator()
	{
	}

	// The binaryTree parameter is used to initialize the mCurrentNode to
	// the appropriate dummy root. In addition, it is used to initialize mParentsStack	
	public BinaryTreeForwardInorderIterator(BinaryTree<T, IBTN> binaryTree)
	{
		mBinaryTree = binaryTree;
		mCurrentNode = mBinaryTree.mDummyRoot1;
		mParentsStack = new Stack<>();
		mParentsStack.push(mCurrentNode);
	}
	
	//overloading operators:
	@Override
	public T next(){
		//if current node has right child : left most node in right subtree is next node
		if(mCurrentNode.mRightChild != null){ ;
			IBTN node = (IBTN)mCurrentNode.mRightChild;
			while(node.mLeftChild != null){
				mParentsStack.push(node);
				node = (IBTN)node.mLeftChild;
			}
			mCurrentNode = node;
			mParentsStack.push(mCurrentNode);


		}
		//if current node has not right child : go to parent till the current node appear in left subtree
		else{
			IBTN childNode = mParentsStack.peek();
			mParentsStack.pop();
			while(mParentsStack.peek().mLeftChild != childNode && childNode != mBinaryTree.mDummyRoot3){
				childNode = mParentsStack.peek();
				mParentsStack.pop();
			}
			mCurrentNode = mParentsStack.peek();
		}
		return mCurrentNode.mData;

	}

	@Override
	public boolean hasNext(){
		IBTN rightMost = mBinaryTree.mDummyRoot3;
		rightMost = (IBTN)rightMost.mLeftChild;
		while(rightMost.mRightChild != null)
			rightMost = (IBTN)rightMost.mRightChild;


		if(mCurrentNode	== rightMost) return false;
		return true;
	}
}
