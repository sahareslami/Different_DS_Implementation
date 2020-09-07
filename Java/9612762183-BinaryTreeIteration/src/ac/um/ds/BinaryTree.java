package ac.um.ds;

import com.sun.corba.se.spi.ior.IORTemplate;

import java.util.Iterator;


public class BinaryTree<T, IBTN extends InternalBinaryTreeNode<T> >
{
    private int mSize;
    protected IBTN mDummyRoot1;
    protected IBTN mDummyRoot2;
    protected IBTN mDummyRoot3;
    protected IBTN mDummyRoot4;
	
	
    public BinaryTree () {
		mSize = 0;
		mDummyRoot1 = createInternalBinaryTreeNodeInstance();
		mDummyRoot2 = createInternalBinaryTreeNodeInstance();
		mDummyRoot3 = createInternalBinaryTreeNodeInstance();
		mDummyRoot4 = createInternalBinaryTreeNodeInstance();

		mDummyRoot1.mLeftChild = mDummyRoot2;
		mDummyRoot1.mRightChild = mDummyRoot3;

		mDummyRoot2.mLeftChild = null;
		mDummyRoot2.mRightChild = null;

        mDummyRoot3.mLeftChild = null;
		mDummyRoot3.mRightChild = mDummyRoot4;

		mDummyRoot4.mLeftChild = null;
		mDummyRoot4.mRightChild = null;
    }

    protected IBTN createInternalBinaryTreeNodeInstance()
    {
        return (IBTN) new InternalBinaryTreeNode<T>();
    }
    protected int size() { return mSize; }

    boolean	isEmpty()
    {
        return mSize == 0;
        //return mInorderEnd.mLeftChild == null;
    }

    public void insertRootNode(T data) {
		IBTN root;
		if(mDummyRoot3.mLeftChild != null)
		    System.out.println("ERROR : root already exists");
		root = createInternalBinaryTreeNodeInstance();
		root.mLeftChild = null;
		root.mRightChild = null;
		root.mData = data;
		mDummyRoot3.mLeftChild = root;
		mSize = 1;
    }

    public BinaryTreeNode<T, IBTN> getRootNode()
    {
        return new BinaryTreeNode<T , IBTN>((IBTN) mDummyRoot3.mLeftChild);
    }

    public void insertLeftChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        IBTN left;
        if(parentNode.hasLeftChild())
            System.out.println("ERROR : left child already exists");
        left = createInternalBinaryTreeNodeInstance();
        left.mData = data;
        left.mLeftChild = null;
        left.mRightChild = null;
        parentNode.mActualNode.mLeftChild = left;

        mSize++;
    }

    public void insertRightChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        IBTN right;
        if(parentNode.hasRightChild())
            System.out.println("ERROR : right child already exist");
        right = createInternalBinaryTreeNodeInstance();
        right.mData = data;
        right.mLeftChild = null;
        right.mRightChild = null;
        parentNode.mActualNode.mRightChild = right;

        mSize++;
    }

    public void deleteLeftChild(BinaryTreeNode<T, IBTN> parent)  // Only leaf nodes and nodes with degree 1 can be deleted. If a degree 1 node is deleted, it is replaced by its subtree.
    {
        IBTN parentNode = parent.mActualNode;
        IBTN theNode = (IBTN) parent.getLeftChild().mActualNode;
        deleteNode(parentNode, theNode);
    }

    public void deleteRightChild(BinaryTreeNode<T, IBTN> parent)  // Only leaf nodes and nodes with degree 1 can be deleted. If a degree 1 node is deleted, it is replaced by its subtree.
    {
        IBTN parentNode = parent.mActualNode;
        IBTN theNode = (IBTN) parent.getRightChild().mActualNode;
        deleteNode(parentNode, theNode);
    }

    protected void deleteNode(IBTN parentNode, IBTN theNode)  // Only leaf nodes and nodes with degree 1 can be deleted. If a degree 1 node is deleted, it is replaced by its subtree.
    {
        //if deleted node ia leaf
        if(theNode.mRightChild == null && theNode.mLeftChild == null){
            //if deleted node is left child of his father
            if(parentNode.mLeftChild == theNode)
                parentNode.mLeftChild = null;
            if(parentNode.mRightChild == theNode)
                parentNode.mRightChild = null;
            theNode = null;
        }
        //if deleted node has left child
        else if(theNode.mLeftChild != null && theNode.mRightChild == null){
            //if deleted node is left child of his father
            if(parentNode.mLeftChild == theNode)
                parentNode.mLeftChild = theNode.mLeftChild;
            //if deleted node is right chuld of his father
            if(parentNode.mRightChild == theNode)
                parentNode.mRightChild = theNode.mLeftChild;
            theNode = null;
        }
        //if deleted node has right child
        else if(theNode.mRightChild != null && theNode.mLeftChild == null){
            //if deleted node is left child of his father
            if(parentNode.mLeftChild == theNode)
                parentNode.mLeftChild = theNode.mRightChild;
            //if deleted node is right child of his father
            if(parentNode.mRightChild == theNode)
                parentNode.mRightChild = theNode.mRightChild;
        }
        else
            System.out.println("ERROR : deleted node more than two child");

    }

    public Iterator<T> forwardInorderIterator() {
        Iterator FInI = new BinaryTreeForwardInorderIterator(this);
        return FInI;

    }

    public Iterator<T> backwardInorderIterator() {
		Iterator BInI = new BinaryTreeBackwardInorderIterator(this);
		return BInI;
	}

    public Iterator<T> forwardPreorderIterator() {
        Iterator FPrI = new BinaryTreeForwardPreorderIterator(this);
        return FPrI;
    }

    public Iterator<T> backwardPreorderIterator() {
		Iterator BPrI = new BinaryTreeBackwardPreorderIterator(this);
		return BPrI;
	}

    public Iterator<T> forwardPostorderIterator() {
        Iterator FpoI = new BinaryTreeForwardPostorderIterator(this);
        return FpoI;
    }

    public Iterator<T> backwardPostorderIterator() {
        Iterator BPoI = new BinaryTreeBackwardPostorderIterator(this);
        return BPoI;
	}
	
    protected IBTN getActualNode(BinaryTreeNode<T, IBTN> node) 
	{
		return node.mActualNode;
	}

    public void draw()
    {
        int maxDepth = 11;
        int depth = depthCalc(getRootNode().mActualNode, 1);
        depth = depth * 2 - 1;

        if (depth > maxDepth){
            System.out.println("Can't draw, the height of the tree is greater than " + (maxDepth + 1) / 2);
            return;
        }

        String [][] map = new String[depth][];
        for (int i = 0; i < depth; i++){
            map[i] = new String[160];
            for (int j = 0; j < 160; j++)
                map[i][j] = " ";
        }

        recursiveDraw(getRootNode().mActualNode, map, 80, 0);

        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < 160; j++) {
                System.out.print(map[i][j]);

            }
            System.out.println();
        }


    }

    public int depthCalc(IBTN root, int depth)
    {
        int res = depth;
        if (root.getRightChild() != null){
            int rightDepth = depthCalc((IBTN)root.getRightChild(), depth + 1);
            res = (res > rightDepth) ? res : rightDepth;
        }
        if(root.getLeftChild() != null){
            int leftDepth = depthCalc((IBTN)root.getLeftChild(), depth + 1);
            res = (res > leftDepth) ? res : leftDepth;
        }
        return res;
    }

    public void recursiveDraw(IBTN root, String lines[][], int x, int y)
    {
        int des = 1;
        for (int i = 0; i < y / 2 + 2; i++)
            des *= 2;
        des = 160 / des;
        //root:

        lines[y][x] =  root.getData().toString();
        //left child:
        if (root.getLeftChild() != null){
            for (int i = 0; i < des; i++)
                lines[y + 1][x - i] = "-";
            lines[y + 1][x] = "|";
            recursiveDraw((IBTN)root.getLeftChild(), lines, x - des, y + 2);
        }
        //right child:
        if (root.getRightChild() != null){
            for (int i = 0; i < des; i++)
                lines[y + 1][x + i] = "-";
            lines[y + 1][x] = "|";
            recursiveDraw((IBTN)root.getRightChild(), lines, x + des, y + 2);
        }

    }

}

