package cn.algorithm.base.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * @author weihongzhou
 * @date 2019/8/15
 */
public class BDfs {

    private static int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    private static List<TreeNode> nodeList = null;

    public static void main(String[] args) {
        createBinTree();
        TreeNode root = nodeList.get(0);
        dfsStack(root);
    }

    static void createBinTree() {
        nodeList = new LinkedList<TreeNode>();
        // 将一个数组的值依次转换为Node节点
        for (int nodeIndex = 0; nodeIndex < array.length; nodeIndex++) {
            nodeList.add(new TreeNode(array[nodeIndex]));
        }
        // 对前lastParentIndex-1个父节点按照父节点与孩子节点的数字关系建立二叉树
        for (int parentIndex = 0; parentIndex < array.length / 2 - 1; parentIndex++) {
            // 左孩子
            nodeList.get(parentIndex).leftChild = nodeList
                    .get(parentIndex * 2 + 1);
            // 右孩子
            nodeList.get(parentIndex).rightChild = nodeList
                    .get(parentIndex * 2 + 2);
        }
        // 最后一个父节点:因为最后一个父节点可能没有右孩子，所以单独拿出来处理
        int lastParentIndex = array.length / 2 - 1;
        // 左孩子
        nodeList.get(lastParentIndex).leftChild = nodeList
                .get(lastParentIndex * 2 + 1);
        // 右孩子,如果数组的长度为奇数才建立右孩子
        if (array.length % 2 == 1) {
            nodeList.get(lastParentIndex).rightChild = nodeList
                    .get(lastParentIndex * 2 + 2);
        }
    }

    /**
     * 深度优先遍历--->分为前中后序遍历
     * @param root
     */
    static void dfs(TreeNode root){
        if(root == null){
            return;
        }
        //前序
        System.out.println(root.data);
        dfs(root.leftChild);
        dfs(root.rightChild);

       /* //中序
        dfs(root.leftChild);
        System.out.println(root.data);
        dfs(root.rightChild);

        //后序
        dfs(root.leftChild);
        dfs(root.rightChild);
        System.out.println(root.data);*/
    }

    /**
     * 因为栈和递归都具有回溯性，所以用递归解的程序用栈也可以用同样的思路解决
     * @param root
     */
    static void dfsStack(TreeNode root){
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> output = new Stack<>();//后序遍历用
        TreeNode curNode = root;
        while (curNode != null || !stack.isEmpty()){
            /**
             * 先序
             */
            /*//迭代访问左孩子
            while (curNode != null){
                System.out.println(curNode.data);// 输出当前元素
                stack.push(curNode);    //将当前节点入栈，以便下次回溯
                curNode = curNode.leftChild;//访问下一个节点
            }
            //若左孩子访问完了，则开始弹栈访问右孩子
            if(!stack.isEmpty()){
                curNode = stack.pop();
                curNode = curNode.rightChild;
            }*/
            /**
             * 中序
             */
            /*//迭代访问左孩子
            while (curNode != null){
                stack.push(curNode);
                curNode = curNode.leftChild;
            }
            //左孩子访问完了，就开始输出左孩子中元素，并回溯
            if(!stack.isEmpty()){
                curNode = stack.pop();//弹栈，回到上个节点
                System.out.println(curNode.data);//输出当前元素
                curNode = curNode.rightChild;//访问右孩子
            }*/
            /**
             * 后序
             */
            if(curNode != null){
                stack.push(curNode);
                output.push(curNode);
                curNode = curNode.rightChild;
            }else {
                curNode = stack.pop();
                curNode = curNode.leftChild;
            }
        }
        //后序遍历需要
        while (output.size() > 0){
            System.out.println(output.pop().data);
        }
    }

    /**
     * 广度优先遍历
     * @param root
     */
    static void bfs(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        // 把根节点入队列
        queue.offer(root);
        while (!queue.isEmpty()){//不能用!=null来判断
            //1、把当前节点移除队列，输出内容
            TreeNode cur = queue.poll();
            System.out.println(cur.data);
            //2、若存在左孩子，就将其加入队列
            if(cur.leftChild != null){
                queue.offer(cur.leftChild);
            }
            //3、若存在右孩子，就将其加入队列
            if(cur.rightChild != null){
                queue.offer(cur.rightChild);
            }
        }
    }
}
class TreeNode{
    int data;
    TreeNode leftChild;
    TreeNode rightChild;

    TreeNode(int i) {
        leftChild = null;
        rightChild = null;
        data = i;
    }
}

