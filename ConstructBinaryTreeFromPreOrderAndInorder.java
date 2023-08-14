import com.sun.source.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

class BinaryNode{
    int data;
    BinaryNode left;
    BinaryNode right;
    BinaryNode(int data){
        this.data=data;
    }
}
class SerializeAndDeserialize{
    BinaryNode root;
    SerializeAndDeserialize(BinaryNode root){
        this.root =root;
    }
    void serialize2(BinaryNode root,StringBuilder sb){
        if(root==null){
            sb.append("null,");
            return ;
        }
        sb.append(root.data+",");
        serialize2(root.left,sb);
        serialize2(root.right,sb);
    }
    String serialize(BinaryNode root){
        StringBuilder sb=new StringBuilder();
        serialize2(root,sb);
        sb.deleteCharAt(sb.length()-1);
        System.out.println("Serialized String:- "+sb);
        return sb.toString();
    }
    static int itr;
    BinaryNode deserialize(String[] preOrder){
        if(itr==preOrder.length){
            return null;
        }
        if(preOrder[itr].equalsIgnoreCase("null")){
            itr++;
            return null;
        }
        BinaryNode root=new BinaryNode(Integer.parseInt(preOrder[itr]));
        itr++;

        root.left=deserialize(preOrder);
        root.right=deserialize(preOrder);

        return root;
    }
    void Head(){
        String ans = serialize(root);
        itr=0;
        String[] preOrder = ans.split(",");
        BinaryNode root=deserialize(preOrder);
        ConstructBinaryTree cbt = new ConstructBinaryTree();
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Tree Representation after Serializing and Deserializing");
        cbt.display(root);
        System.out.println("-------------------------------------------------------------------");
    }
}
class ConstructBinaryTree{
    public BinaryNode constructBinaryTreeFromPreorderAndInorder2(int[] pre,int psi,int pei,int[] in,int isi,int iei,HashMap<Integer,Integer> map){
        if(isi>iei || psi>pei){
            return null;
        }
        BinaryNode root = new BinaryNode(pre[psi]);

        int i=map.get(root.data);
        int countLST=i-isi;

        root.left = constructBinaryTreeFromPreorderAndInorder2(pre,psi+1,psi+countLST,in,isi,i-1,map);
        root.right = constructBinaryTreeFromPreorderAndInorder2(pre,psi+countLST+1,pei,in,i+1,iei,map);

        return root;
    }
    BinaryNode constructBinaryTreeFromPreorderAndInorder(int[] preOrder,int[] inOrder){
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<inOrder.length;i++){
            map.put(inOrder[i],i);
        }
        return (constructBinaryTreeFromPreorderAndInorder2(preOrder,0, preOrder.length-1,inOrder,0, inOrder.length-1,map));
    }
    void display(BinaryNode root){
        
        if(root==null){
            return ;
        }

        StringBuilder sb=new StringBuilder();
        sb.append((root.left!=null?root.left.data:"."));
        sb.append("->"+root.data+"<-");
        sb.append((root.right!=null?root.right.data:"."));

        System.out.println(sb);

        display(root.left);
        display(root.right);
    }
}
class DFSAndBFSOnBinaryTree{
    class Pair{
        BinaryNode node;
        int state;
        Pair(BinaryNode node,int state){
            this.node=node;
            this.state=state;
        }
    }
    BinaryNode root;
    DFSAndBFSOnBinaryTree(BinaryNode root){
        this.root=root;
    }
    BinaryNode nextOrder(Stack<Pair> st,int currState){
        while(st.size()>0){
            Pair rpair=st.peek();
            if(rpair.state==1){
                rpair.state++;
                //For Preorder Traversal
                if(rpair.node.left!=null){
                    st.push(new Pair(rpair.node.left,1));
                }
                if(currState==1){
                    return rpair.node;
                }
            }
            else if(rpair.state==2){
                rpair.state++;
                //For Inorder Traversal
                if(rpair.node.right!=null){
                    st.push(new Pair(rpair.node.right,1));
                }
                if(currState==2){
                    return rpair.node;
                }
            }
            else if(rpair.state==3){
                st.pop();
                if(currState==3){
                    return rpair.node;
                }
            }
        }
        return null;
    }
    void forAllOrders(int requiredstate){
        String orderDefined = "";
        if(requiredstate==1)
            orderDefined="PreOrder";
        else if(requiredstate==2)
            orderDefined="InOrder";
        else if(requiredstate==3)
            orderDefined="PostOrder";

        System.out.print(orderDefined+" is:- ");
        Stack<Pair> st = new Stack<>();
        st.push(new Pair(root,1));
        BinaryNode curr=nextOrder(st,requiredstate);
        System.out.print(curr.data);
        while(curr!=null){
            curr=nextOrder(st,requiredstate);
            if(curr!=null)
                System.out.print(", "+curr.data);
        }
        System.out.println();
    }
    void mainTriggerForDFS(){
        forAllOrders(1);
        forAllOrders(2);
        forAllOrders(3);
    }
    void forBFS(){
        Queue<BinaryNode> que=new ArrayDeque<>();
        que.add(root);
        int level=1;
        while(que.size()>0){
            ArrayList<Integer> ansList=new ArrayList<>();
            int size= que.size();
            while(size-->0){
                BinaryNode node=que.remove();
                if(node.left!=null){
                    que.add(node.left);
                }
                if(node.right!=null){
                    que.add(node.right);
                }
                ansList.add(node.data);
            }
            String arrayListAsString = ansList.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

//            System.out.println(arrayListAsString);
            System.out.println("Level "+level+" is:- "+arrayListAsString);
            level++;
        }
    }
}
class VerticalOrderTraversal{
    class Pair{
        BinaryNode node;
        int vLevel;
        Pair(BinaryNode node,int vLevel){
            this.node=node;
            this.vLevel=vLevel;
        }
    }
    BinaryNode root;
    VerticalOrderTraversal(BinaryNode root){
        this.root=root;
    }
    static int minVLevel=0,maxVLevel=0;
    void traversal(Pair rpair){
        if(rpair.node==null){
            return ;
        }
        minVLevel=Math.min(minVLevel,rpair.vLevel);
        maxVLevel=Math.max(maxVLevel, rpair.vLevel);
        traversal(new Pair(rpair.node.left,rpair.vLevel-1));
        traversal(new Pair(rpair.node.right,rpair.vLevel+1));
    }
    void verticalOrder(){
        traversal(new Pair(root,0));
        int currVLevel=-minVLevel;
        int rangeVLevel=maxVLevel-minVLevel+1;
        List<List<Integer>> vot = new ArrayList<>();
        for(int i=0;i<rangeVLevel;i++){
            vot.add(new ArrayList<>());
        }
//        System.out.println("MinVLevel:- "+minVLevel);
//        System.out.println("MaxVLevel:- "+maxVLevel);
//        System.out.println("Initial vot:- "+vot);
        Queue<Pair> que = new ArrayDeque<>();
        que.add(new Pair(root,currVLevel));
        while(que.size()>0){
            int size=que.size();
            while(size-->0){
                Pair rpair=que.remove();
                vot.get(rpair.vLevel).add(rpair.node.data);
                if(rpair.node.left!=null){
                    que.add(new Pair(rpair.node.left, rpair.vLevel-1));
                }
                if(rpair.node.right!=null){
                    que.add(new Pair(rpair.node.right,rpair.vLevel+1));
                }
            }
        }
        System.out.println("Final vot:- "+vot);
        for(List<Integer> itr:vot){
            String arrayListAsString = itr.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            System.out.println(arrayListAsString);
        }
    }
}
public class ConstructBinaryTreeFromPreOrderAndInorder {
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the size of the array:- ");
        int n=sc.nextInt();
        System.out.println("Enter the preorder following by inorder");
        int[] preOrder=new int[n];
        int[] inOrder=new int[n];
        for(int i=0;i<n;i++){
            preOrder[i]=sc.nextInt();
        }
        for(int i=0;i<n;i++){
            inOrder[i]=sc.nextInt();
        }
        ConstructBinaryTree cbt = new ConstructBinaryTree();
        BinaryNode root = cbt.constructBinaryTreeFromPreorderAndInorder(preOrder,inOrder);
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Tree After Creation Representation");
        cbt.display(root);
        System.out.println("-------------------------------------------------------------------");
        SerializeAndDeserialize sb = new SerializeAndDeserialize(root);
        sb.Head();
        DFSAndBFSOnBinaryTree dfsAndBFSOnBinaryTree = new DFSAndBFSOnBinaryTree(root);
        System.out.println("DFS of Binary Tree");
        dfsAndBFSOnBinaryTree.mainTriggerForDFS();
        System.out.println("-------------------------------------------------------------------");
        System.out.println("BFS of Binary Tree");
        dfsAndBFSOnBinaryTree.forBFS();
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Vertical Order Traversal of Binary Tree");
        VerticalOrderTraversal verticalOrderTraversal = new VerticalOrderTraversal(root);
        verticalOrderTraversal.verticalOrder();
        System.out.println("-------------------------------------------------------------------");
    }
}
