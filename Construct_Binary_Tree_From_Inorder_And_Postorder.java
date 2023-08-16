import java.util.*;
class BinaryTreeNode{
    int data;
    BinaryTreeNode left;
    BinaryTreeNode right;
    BinaryTreeNode(int data){
        this.data=data;
    }
}
class Validate_Binary_Search_Tree{
    class Pair{
        BinaryTreeNode root;
        boolean isBST;
        int maxi,mini;
        Pair(BinaryTreeNode root,int maxi,int mini){
            this.root=root;
            this.maxi=maxi;
            this.mini=mini;
        }
        Pair(boolean isBST,int mini,int maxi){
            this.isBST=isBST;
            this.mini=mini;
            this.maxi=maxi;
        }
    }
    Pair validationForBinarySearchTree2(BinaryTreeNode root){
        if(root==null){
            return new Pair(true,Integer.MAX_VALUE,Integer.MIN_VALUE);
        }
        Pair LST=validationForBinarySearchTree2(root.left);
        Pair RST=validationForBinarySearchTree2(root.right);
        boolean isCurrRoot=LST.isBST && RST.isBST && (root.data>LST.maxi) && (root.data<RST.mini);
        if(!isCurrRoot){
            return new Pair(false,0,0);
        }
        int currMax=Math.max(RST.maxi,root.data);
        int currMin=Math.min(LST.mini,root.data);
        return new Pair(true,currMin,currMax);
    }
    boolean validationForBinarySearchTree(BinaryTreeNode root){
        return validationForBinarySearchTree2(root).isBST;
    }
}
class Views_Of_Binary_Tree{
    BinaryTreeNode root;
    Views_Of_Binary_Tree(BinaryTreeNode root){
        this.root=root;
    }
    void leftViewAndRightViewOfBinaryTree(){
        List<Integer> leftView = new ArrayList<>();
        List<Integer> rightView = new ArrayList<>();
        Queue<BinaryTreeNode> que = new ArrayDeque<>();
        que.add(root);
        while(que.size()>0){
            int currSize=que.size();
            int size=que.size();
            while(size-->0){
                BinaryTreeNode curr=que.remove();
                if(curr.left!=null){
                    que.add(curr.left);
                }
                if(curr.right!=null){
                    que.add(curr.right);
                }
                if(size==currSize-1){
                    leftView.add(curr.data);
                }
                if(size==0){
                    rightView.add(curr.data);
                }
            }
        }
        System.out.println("Left View of Binary Tree:- "+leftView);
        System.out.println("Right View of Binary Tree:- "+rightView);
    }
    static int minVLevel=0;
    static int maxVLevel=0;
    void traversal(BinaryTreeNode root,int currVLevel){
        if(root==null)
            return ;
        minVLevel=Math.min(minVLevel,currVLevel);
        maxVLevel=Math.max(maxVLevel,currVLevel);
        traversal(root.left,currVLevel-1);
        traversal(root.right,currVLevel+1);
    }
    class Pair{
        BinaryTreeNode root;
        int vLevel;
        Pair(BinaryTreeNode root,int vLevel){
            this.root=root;
            this.vLevel=vLevel;
        }
    }
    void topViewAndBottomViewOfBinaryTree(){
        traversal(root,0);
        int vLevel=-minVLevel,rangeOfVLevel=maxVLevel-minVLevel+1;
        List<List<Integer>> vot = new ArrayList<>();
        for(int i=0;i<rangeOfVLevel;i++){
            vot.add(new ArrayList<>());
        }
        Queue<Pair> que=new ArrayDeque<>();
        que.add(new Pair(root,vLevel));
        while(que.size()>0){
            int size=que.size();
            while(size-->0){
                Pair rpair=que.remove();
                vot.get(rpair.vLevel).add(rpair.root.data);
                if(rpair.root.left!=null){
                    que.add(new Pair(rpair.root.left,rpair.vLevel-1));
                }
                if(rpair.root.right!=null){
                    que.add(new Pair(rpair.root.right,rpair.vLevel+1));
                }
            }
        }
        List<Integer> topView = new ArrayList<>();
        List<Integer> bottomView = new ArrayList<>();
        for(List<Integer> itr:vot){
            topView.add(itr.get(0));
            bottomView.add(itr.get(itr.size()-1));
        }
        System.out.println("Top View:- "+topView);
        System.out.println("Bottom View:- "+bottomView);
    }
    void leftSide(BinaryTreeNode root,List<Integer> boundaryElements){
        if(root==null){
            return ;
        }
        if(root.left!=null || root.right!=null){
            boundaryElements.add(root.data);
        }
        if(root.left==null && root.right!=null){
            leftSide(root.right,boundaryElements);
        }
        else{
            leftSide(root.left,boundaryElements);
        }
    }
    void leafNodeTraversal(BinaryTreeNode root,List<Integer> boundaryElements){
        if(root==null){
            return ;
        }
        if(root.left==null && root.right==null){
            boundaryElements.add(root.data);
        }
        leafNodeTraversal(root.left,boundaryElements);
        leafNodeTraversal(root.right,boundaryElements);
    }
    void rightSide(BinaryTreeNode root,List<Integer> boundaryElements){
        if(root==null){
            return ;
        }
        if(root.right==null && root.left!=null){
            rightSide(root.left,boundaryElements);
        }
        else{
            rightSide(root.right,boundaryElements);
        }
        if(root.left!=null || root.right!=null){
            boundaryElements.add(root.data);
        }
    }
    void boundaryTraversal(){
        List<Integer> boundaryElements=new ArrayList<>();
        boundaryElements.add(root.data);
        leftSide(root.left,boundaryElements);
        leafNodeTraversal(root,boundaryElements);
        rightSide(root.right,boundaryElements);
        System.out.println("Boundary Elements:- "+boundaryElements);
    }
}
public class Construct_Binary_Tree_From_Inorder_And_Postorder {
    public static BinaryTreeNode constructTree2(int[] inorder,int isi,int iei,int[] postOrder,int psi,int pei,HashMap<Integer,Integer> map){
        if(isi>iei || psi>pei) return null;

        BinaryTreeNode root=new BinaryTreeNode(postOrder[pei]);

        int i=map.get(root.data);
        int countLST=i-isi;
        root.left=constructTree2(inorder,isi,i-1,postOrder,psi,psi+countLST-1,map);
        root.right=constructTree2(inorder,i+1,iei,postOrder,psi+countLST,pei-1,map);
        return root;
    }
    public static BinaryTreeNode constructTree(int[] inOrder,int[] postOrder){
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<inOrder.length;i++){
            map.put(inOrder[i],i);
        }
        return constructTree2(inOrder,0, inOrder.length-1, postOrder,0, postOrder.length-1, map);
//        return null;
    }
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the number of node of binary tree:- ");
        int n=sc.nextInt();
        int[] inOrder=new int[n];
        int[] postOrder=new int[n];
        System.out.println("Enter the InOrder and PostOrder");
        for(int i=0;i<n;i++){
            inOrder[i]=sc.nextInt();
        }
        for(int i=0;i<n;i++){
            postOrder[i]=sc.nextInt();
        }
        BinaryTreeNode root=constructTree(inOrder,postOrder);
        Views_Of_Binary_Tree views=new Views_Of_Binary_Tree(root);
        views.leftViewAndRightViewOfBinaryTree();
        views.topViewAndBottomViewOfBinaryTree();
        views.boundaryTraversal();
        Validate_Binary_Search_Tree validating=new Validate_Binary_Search_Tree();
        boolean isBST = validating.validationForBinarySearchTree(root);
        System.out.println((isBST?"It is Binary Search Tree":"It is not Binary Search Tree"));
        display(root);
    }
    public static void display(BinaryTreeNode root){
        if(root==null)
            return ;
        StringBuilder sb = new StringBuilder();
        sb.append((root.left!=null?root.left.data:"."));
        sb.append("->"+root.data+"<-");
        sb.append((root.right!=null?root.right.data:"."));
        System.out.println(sb);
        display(root.left);
        display(root.right);
    }
}
