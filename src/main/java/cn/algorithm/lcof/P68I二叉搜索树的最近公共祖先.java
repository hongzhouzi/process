package cn.algorithm.lcof;

/**
 * @author hongzhou.wei
 * @date 2020/7/26
 */
public class P68I二叉搜索树的最近公共祖先 {
    /**
     * 【迭代】
     * 若目标值均大于当前根节点，则在根节点左边找；
     * 若目标值均小于当前根节点，则在根节点右边找；
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (root != null) {
            // 两值均小于当前根节点，则在左侧找
            if (p.val < root.val && q.val < root.val) {
                root = root.left;
            }
            // 两值均大于当前根节点，则在右侧找
            else if (p.val > root.val && q.val > root.val) {
                root = root.right;
            } else {
                break;
            }
        }
        return root;
    }

    /**
     * 【迭代优化判断条件】
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        // 保证p小于q，优化后续判断条件
        if (p.val > q.val) {
            TreeNode t = p;
            p = q;
            q = t;
        }
        while (root != null) {
            // 两值均小于当前根节点，则在左侧找
            if (q.val < root.val) {
                root = root.left;
            }
            // 两值均大于当前根节点，则在右侧找
            else if (p.val > root.val) {
                root = root.right;
            } else {
                break;
            }
        }
        return root;
    }

    /**
     * 【递归】
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        // 两值均大于当前根节点，则在右侧找
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        // 两值均小于当前根节点，则在左侧找
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        return root;
    }
}
