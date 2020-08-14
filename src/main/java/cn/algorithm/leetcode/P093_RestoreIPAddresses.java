package cn.algorithm.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 93. 复原IP地址
 * 给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。
 * 有效的 IP 地址正好由四个整数（每个整数位于 0 到 255 之间组成），整数之间用 '.' 分隔。
 * 示例:
 * 输入: "25525511135"
 * 输出: ["255.255.11.135", "255.255.111.35"]
 *
 * @author hongzhou.wei
 * @date 2020/8/10
 */
public class P093_RestoreIPAddresses {
    public static void main(String[] args) {

    }

    /**
     * 【深搜+剪枝】
     * 剪枝条件：
     * 1.字符串长度<4 或 >12 一定不能拼出合法IP；
     * 2.每个节点截取方式分3种，分别是截1 2 3位（三叉树）；
     * 3.每个IP都是4个段，（三叉树最多4层）（递归终止条件之一）；
     * 需要的状态变量：
     * 已经分割出的IP段
     * 截取IP段的起始位置
     * path：记录从根节点到叶子节点的一个路径（回溯算法的常规变量 一个栈）
     * res：记录结果集的变量
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        int len = s.length();
        List<String> res = new LinkedList<>();
        if (len < 4 || len > 12) {
            return res;
        }
        Deque<String> path = new ArrayDeque<>();
        dfs(s, len, 0, 4, path, res);
        return res;
    }

    /**
     * 深搜
     *
     * @param s       字符串
     * @param len     字符串长度
     * @param begin   开始截取长度
     * @param residue 剩余没有被分割的段
     * @param path    从根节点到叶子节点的一个路径
     * @param res     结果集变量
     */
    void dfs(String s, int len, int begin, int residue, Deque<String> path, List<String> res) {
        // 遍历结束
        if (begin == len) {
            if (residue == 0) {
                res.add(String.join(".", path));
            }
            return;
        }
        // 组合三叉树
        for (int i = begin; i < begin + 3 && i < len; i++) {
            if (residue * 3 < len - i) {
                continue;
            }
            if (judgeIPSegment(s, begin, i)) {
                String curIpSegment = s.substring(begin, i + 1);
                path.addLast(curIpSegment);
                dfs(s, len, i + 1, residue - 1, path, res);
                path.removeLast();
            }
        }
    }

    boolean judgeIPSegment(String s, int left, int right) {
        // 子串长度大于1时，前导不能为0
        if (right - left + 1 > 1 && s.charAt(left) == '0') {
            return false;
        }
        // 子串为大于等于
        int res = 0;
        while (left <= right) {
            res = res * 10 + s.charAt(left++) - '0';
        }
        return res >= 0 && res <= 255;
    }

}