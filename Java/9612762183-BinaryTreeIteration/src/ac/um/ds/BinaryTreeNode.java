package ac.um.ds;

public class BinaryTreeNode<T, IBTN extends InternalBinaryTreeNode<T> >
{
    protected IBTN mActualNode;

    protected BinaryTreeNode(IBTN node) { mActualNode = node; }

    public T getData() { return mActualNode.getData(); }

    public boolean hasLeftChild()
    {
        return mActualNode.getLeftChild() != null;
    }

    public BinaryTreeNode getLeftChild()
    {
        return new BinaryTreeNode(mActualNode.getLeftChild());
    }

    public boolean hasRightChild() { 
		return mActualNode.getRightChild() != null; 
	}

    public BinaryTreeNode getRightChild()
    {
        return new BinaryTreeNode(mActualNode.getRightChild());
    }
}
