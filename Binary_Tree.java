import java.util.*;
class TreeNode{
    int data;
    TreeNode right;
    TreeNode left;
    TreeNode(int data){
        this.data=data;
    }
}
public class Binary_Tree{
    static int itr;
    public static TreeNode deserialize2(String[] preOrder){
        if(itr==preOrder.length){
            return null;
        }
        if(preOrder[itr].equalsIgnoreCase("null")==true){
            itr++;
            return null;
        }
        TreeNode root=new TreeNode(Integer.parseInt(preOrder[itr]));
        itr++;
        System.out.println("itr:-"+itr);
        root.left=deserialize2(preOrder);
        root.right=deserialize2(preOrder);
        return root;
    }
    public static TreeNode deserialize(String serialize){
        String[] preOrder = serialize.split(" ");
        itr=0;
        return deserialize2(preOrder);
    }
    public static void display(TreeNode node){
        if(node==null)
           return ;
        StringBuilder sb=new StringBuilder();
        sb.append((node.left!=null?node.left.data:"."));
        sb.append("->"+node.data+"<-");
        sb.append((node.right!=null?node.right.data:"."));
        System.out.println(sb);
        display(node.left);
        display(node.right);
    }
    public static int maximumOfBinaryTree(TreeNode node){
        if(node==null)
            return Integer.MIN_VALUE;
        int LST = maximumOfBinaryTree(node.left);
        int RST = maximumOfBinaryTree(node.right);
        return Math.max(node.data,Math.max(LST,RST));
    }
    public static int minimumOfBinaryTree(TreeNode node){
        if(node==null)
            return Integer.MAX_VALUE;
        int LST = minimumOfBinaryTree(node.left);
        int RST = minimumOfBinaryTree(node.right);
        return Math.min(node.data,Math.min(LST,RST));
    }
    public static int sizeOfBinaryTree(TreeNode node){
        if(node==null)
            return 0;
        int LST=sizeOfBinaryTree(node.left);
        int RST=sizeOfBinaryTree(node.right);
        return LST+RST+1;
    }
    static class Pair{
        int height;
        int diameter;
        Pair(int height,int diameter){
            this.height=height;
            this.diameter=diameter;
        }
    }
//    public static int diameter(TreeNode root){
//        //Faith of function to return diameter and height
//        if(root==null)
//            return 0;
//
//        int diameterOfLST=diameter(root.left);
//        int diameterOfRST=diameter(root.right);
//
//        int heightOfLST=height(root.left);
//        int heightOfRST=height(root.right);
//
//        int diameterThroughRoot=heightOfLST+heightOfRST+1;
//        return Math.max(diameterThroughRoot,Math.max(diameterOfLST,diameterOfRST));
//    }
    public static Pair diameterAndHeightOfTree(TreeNode root){
        if(root==null)
            return new Pair(0,0);
        //Faith of function to return diameter and height
        Pair LST=diameterAndHeightOfTree(root.left);
        Pair RST=diameterAndHeightOfTree(root.right);
        int diameterThroughRoot=LST.height+RST.height+1;
        int maxDiameter=Math.max(diameterThroughRoot,Math.max(LST.diameter, RST.diameter));
        int heightThroughRoot=Math.max(LST.height, RST.height)+1;
        return new Pair(heightThroughRoot,maxDiameter);
    }
    public static int diameter(TreeNode root){
        return diameterAndHeightOfTree(root).diameter;
    }
    public static int height(TreeNode root){
        if(root==null)
            return 0;
        int heightOfLST=height(root.left);
        int heightOfRST=height(root.right);
        return Math.max(heightOfLST,heightOfRST)+1;
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the serialize string:- ");
        String serialize=sc.nextLine();
        TreeNode root = deserialize(serialize);
        display(root);
        int max=maximumOfBinaryTree(root);
        int min=minimumOfBinaryTree(root);
        int size=sizeOfBinaryTree(root);
        int heightOfBinary=height(root);
        int diameterOfBinaryTree=diameter(root);
        System.out.println("Height of Tree:- "+heightOfBinary);
        System.out.println("Maximum of Tree:- "+max);
        System.out.println("Minimum of Tree:- "+min);
        System.out.println("Size of Tree:- "+size);
        System.out.println("Diameter of Tree:- "+diameterOfBinaryTree);
    }
}