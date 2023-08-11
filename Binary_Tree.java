import java.util.*;
class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode left1;
    TreeNode(int val){
        this.val=val;
        this.left=null;
        this.right=null;
    }
}
public class Binary_Tree {
    /*This class is to create a binary tree from serialize string*/
    static int itr=0;
    public static TreeNode deserialize(String[] preOrder){
        /*This function is used to create a Binary Tree from preOrder Traversal*/
        if(itr>=preOrder.length){
            return null;
        }
        if(preOrder[itr].equals("-1")==true){
            itr++;
            return null;
        }
        int data=Integer.parseInt(preOrder[itr]);
        TreeNode root = new TreeNode(data);

        itr++;
        root.left=deserialize(preOrder);
        root.right=deserialize(preOrder);

        return root;
    }
    public static void preOrder(TreeNode root){
        if(root == null)
            return ;
        System.out.print(root.val+" ");
        preOrder(root.left);
        preOrder(root.right);
    }
    public static void inOrder(TreeNode root){
        if(root==null){
            return ;
        }
        inOrder(root.left);
        System.out.print(root.val+" ");
        inOrder(root.right);
    }
    public static void postOrder(TreeNode root){
        if(root==null){
            return ;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val+" ");
    }
    static int height(TreeNode root){
        //faith return the maximum height of binary tree
        if(root==null)
            return 0;
        int LST=height(root.left);
        int RST=height(root.right);
        return Math.max(LST,RST)+1;
    }
//    static int diameter2(TreeNode root){
//        int diameterOfLST = dia
//    }
    static class PairOfDiameterAndHeight{
        int diameter;
        int height;
        PairOfDiameterAndHeight(int diameter, int height){
            this.diameter=diameter;
            this.height=height;
        }
    }
    static PairOfDiameterAndHeight diameterAndHeight(TreeNode root){
        //faith of function is to return the diameter and height of tree
        if(root==null){
            return new PairOfDiameterAndHeight(0,0);
        }
        PairOfDiameterAndHeight LST = diameterAndHeight(root.left);
        PairOfDiameterAndHeight RST = diameterAndHeight(root.right);

        int diameterThroughRoot = LST.height+RST.height+1;
        int maxDiameter = Math.max(diameterThroughRoot,Math.max(LST.diameter,RST.diameter));

        int heightOfTree = Math.max(LST.height, RST.height)+1;

        return new PairOfDiameterAndHeight(maxDiameter,heightOfTree);
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the preorder of tree with null as -1 separated by space:- ");
        String str = sc.nextLine();
        String[] preOrder = str.split(" ");
        TreeNode root = deserialize(preOrder);
        preOrder(root);
        System.out.println();
        inOrder(root);
        System.out.println();
        postOrder(root);
        System.out.println();
        int heightOfTree=height(root);
        System.out.println("Height of Tree:- "+heightOfTree);
        PairOfDiameterAndHeight maxDiameter = diameterAndHeight(root);
        System.out.println("Diameter of Tree:- "+maxDiameter.diameter);
    }
}
