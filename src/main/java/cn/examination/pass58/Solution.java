package cn.examination.pass58;

import org.omg.PortableInterceptor.INACTIVE;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 输出树中平均值最大的层
 6 3 9 4 5 1 6 ===> 2
 */
public class Solution {
    class Node{
        int val;
        Node left;
        Node right;
        public Node(int val){
            this.val = val;
        }
    }

    private Node createBinaryTreeByArray(Integer[] array,int index)
    {
        Node tn = null;
        if (index<array.length) {
            Integer value = array[index];
            if (value == null) {
                return null;
            }
            tn = new Node(value);
            tn.left = createBinaryTreeByArray(array, 2*index+1);
            tn.right = createBinaryTreeByArray(array, 2*index+2);
            return tn;
        }
        return tn;
    }

    public int levelOrder(Node root){
        Queue<Node> queue1 = new LinkedList<>() ;
        Queue<Node> queue2 = new LinkedList<>() ;
        Node node = root;
        Map<Integer,Integer> val = new HashMap<>();
        queue1.add(root);
        int i=1;
        while (node!=null){
            if(i%2==1){
                while (!queue1.isEmpty()){
                    Node p = queue1.poll();
                    val.put(i, (val.get(i).intValue()) + p.val);
                    //下一层
                    if(p.left != null){
                        queue2.add(p.left);
                    }
                    if(p.right != null){
                        queue2.add(p.right);
                    }
                }
            }else {
                while (!queue2.isEmpty()){
                    Node q = queue2.poll();
                    val.put(i, (val.get(i).intValue()) + q.val);
                    //下一层
                    if(q.left != null){
                        queue1.add(q.left);
                    }
                    if(q.right != null){
                        queue1.add(q.right);
                    }
                }
            }

        }
        //请您完成此方法
        return 2;
    }


    public  Node createTree(int[] array){
        List<Node> nodeList=new LinkedList<Node>();

        for(int nodeIndex=0;nodeIndex<array.length;nodeIndex++){
            nodeList.add(new Node(array[nodeIndex]));
        }
        for(int parentIndex=0;parentIndex<=array.length/2-1;parentIndex++){
            nodeList.get(parentIndex).left =nodeList.get(parentIndex*2+1);
            //防止是非完全二叉树
            if((parentIndex*2+2)<array.length) {
                nodeList.get(parentIndex).right = nodeList.get(parentIndex * 2 + 2);
            }
        }
        return nodeList.get(0);
    }


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        //int [] array = new int [n];
        Integer [] array = new Integer[n];
        for(int i = 0;i < n;i++){
            String value =scanner.next();
            if(value.equals( "null" )){
                array[i]=null;
            }else{
                array[i] = Integer.valueOf( value );
            }

        }
        Solution solution = new Solution ();
        Node root =solution.createBinaryTreeByArray(array,0);
        int level = solution.levelOrder( root );
        System.out.println(level);

    }
}
