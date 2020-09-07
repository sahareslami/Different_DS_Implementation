import ac.um.ds.BinaryTree;
import ac.um.ds.BinaryTreeNode;
import ac.um.ds.InternalBinaryTreeNode;

import java.lang.String;

public class TestBinaryTree {

    public static void main(String[] args)
    {

        String ans = "                                           1\n" +
                "                        -------------------|-------------------\n" +
                "                       2                                       3\n" +
                "              ---------|                              ---------|\n" +
                "             4                                       9                  \n" +
                "         ----|----\n" +
                "        7         0";

        System.out.println("Right answer : \n" + ans);

        BinaryTree<Integer, InternalBinaryTreeNode<Integer>>	bt = new BinaryTree<>();
        bt.insertRootNode(1);

        BinaryTreeNode btna = bt.getRootNode();
        bt.insertLeftChild(btna, 2);
        bt.insertRightChild(btna, 3);

        BinaryTreeNode	btnb = btna.getLeftChild();
        BinaryTreeNode	 btnc = btna.getRightChild();
        bt.insertLeftChild(btnb, 4);
        bt.insertLeftChild(btnc, 5);
        bt.insertRightChild(btnc, 6);

        BinaryTreeNode	btnd = btnb.getLeftChild();
        BinaryTreeNode	btne = btnc.getLeftChild();
        BinaryTreeNode	 btnf = btnc.getRightChild();
        bt.insertLeftChild(btnd, 7);
        bt.insertRightChild(btnd, 8);
        bt.insertRightChild(btne, 9);

        BinaryTreeNode	btnh = btnd.getRightChild();
        BinaryTreeNode	btni = btne.getRightChild();
        bt.insertLeftChild(btnh, 0);

        BinaryTreeNode	btnk = btnh.getLeftChild();

        bt.deleteNode(btne);
        bt.deleteNode(btnh);
        bt.deleteNode(btnf);
       


        bt.setNodeDisplayWidth(1);
        System.out.println("Your answer:");
        bt.draw();

    }
}
