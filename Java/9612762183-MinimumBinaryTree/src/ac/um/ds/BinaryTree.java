package ac.um.ds;

public class BinaryTree<T, IBTN extends InternalBinaryTreeNode<T> >
{
    private IBTN mDummyRoot;
    private int	mNodeDisplayWidth;
    private int mSize;

    public BinaryTree () {
        mNodeDisplayWidth = 2;
        mSize = 0;
        mDummyRoot = createInternalBinaryTreeNodeInstance();
    }

    protected IBTN createInternalBinaryTreeNodeInstance()
    {
        return (IBTN) new InternalBinaryTreeNode<T>();
    }
    protected int size() { return mSize; }

    boolean	isEmpty()
    {
        return mDummyRoot.mLeftChild == null;
    }

    public void insertRootNode(T data) {
        IBTN	root;
        if (mDummyRoot.getLeftChild() != null)
            System.out.print("Error: Root already exists.");
        root = createInternalBinaryTreeNodeInstance();
        root.mParent = mDummyRoot;
        root.mData = data;
        root.mLeftChild = null;
        root.mRightChild = null;
        mDummyRoot.mLeftChild = root;
        mSize = 1;
    }

    public BinaryTreeNode<T, IBTN> getRootNode()
    {
        return new BinaryTreeNode<T, IBTN>((IBTN)mDummyRoot.mLeftChild);
    }

    public void insertLeftChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        if(parentNode.hasLeftChild())
            System.out.print("Error: left child alreay exists." + "   " + data + "\n");
        else {

            IBTN left = createInternalBinaryTreeNodeInstance();
            left.mData = data;
            left.mParent = parentNode.mActualNode;
            parentNode.mActualNode.mLeftChild = left;
            left.mRightChild = null;
            left.mLeftChild = null;
        }
    }

    public void insertRightChild(BinaryTreeNode<T, IBTN> parentNode, T data) {
        if(parentNode.hasRightChild())
            System.out.print("Error : right child already exist."+ "   " + data + "\n");
        else {
            IBTN right = createInternalBinaryTreeNodeInstance();
            right.mData = data;
            right.mParent = parentNode.mActualNode;
            parentNode.mActualNode.mRightChild = right;
            right.mRightChild = null;
            right.mLeftChild = null;
        }
    }


    public void deleteNode(BinaryTreeNode node)  // Only leaf nodes and nodes with degree 1 can be deleted. If a degree 1 node is deleted, it is replaced by its subtree.
    {
        //if deleted node is leaf
        if(!node.hasRightChild() && !node.hasLeftChild()){
            if(node.mActualNode.mParent.mRightChild == node.mActualNode) {

                node.mActualNode.mParent.mRightChild = null;
            }
            else if(node.mActualNode.mParent.mLeftChild == node.mActualNode) {

                node.mActualNode.mParent.mLeftChild = null;
            }
            //node.mActualNode = null;

        }
        //if deleted node has degree 1 and has only left child
        else if(node.hasLeftChild()  && !node.hasRightChild()){
            node.mActualNode.mLeftChild.mParent = node.mActualNode.mParent;
            //if deleted node is left child of his father
            if(node.mActualNode.mParent.mLeftChild == node.mActualNode)
                node.mActualNode.mParent.mLeftChild = node.mActualNode.mLeftChild;
                //if deleted node is right child of his father
            else {

                node.mActualNode.mParent.mRightChild = node.mActualNode.mLeftChild;
            }
        }
        // if deleted node has degree 1 and has only right child
        else if(node.hasRightChild() && !node.hasLeftChild()){
            node.mActualNode.mRightChild.mParent = node.mActualNode.mParent;

            //if node is left child of his father
            if(node.mActualNode.mParent.mLeftChild == node.mActualNode) {


                node.mActualNode.mParent.mLeftChild = node.mActualNode.mRightChild;

            }
            //if node is right child of his father
            else
                node.mActualNode.mParent.mRightChild = node.mActualNode.mRightChild;


        }
        else
            System.out.println("Error : node has two child");

    }

    protected IBTN getActualNode(BinaryTreeNode<T, IBTN> node) {return node.mActualNode;}

    public void setNodeDisplayWidth(int width)
    {
        mNodeDisplayWidth = width;
    }

    public void draw()
    {
        int maxDepth = 9;
        int depth = depthCalc(getRootNode().mActualNode, 1);
        depth = depth * 2 - 1;

        if (depth > maxDepth){
            System.out.println("Can't draw, the height of the tree is greater than " + (maxDepth + 1) / 2);
            return;
        }

        String [][] map = new String[depth][];
        for (int i = 0; i < depth; i++){
            map[i] = new String[80];
            for (int j = 0; j < 80; j++)
                map[i][j] = " ";
        }

        recursiveDraw(getRootNode().mActualNode, map, 40, 0);

        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < 80; j++) {
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
        des = 80 / des;
        //root:
        for (int i = 0; i < mNodeDisplayWidth; i++)
            lines[y][x + i - mNodeDisplayWidth / 2] = root.getData().toString() ;
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

