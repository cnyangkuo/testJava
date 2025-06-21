// 添加LT145二叉树的后序遍历问题解决方案
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// 定义二叉树节点类
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    
    TreeNode(int x) {
        val = x;
    }
}

public class LT145BinaryTreePostorderTraversal {
    // 题目：二叉树的后序遍历（Binary Tree Postorder Traversal）
    // 难度：中等
    // 描述：给定一个二叉树，返回其后序遍历结果。
    // 解法：迭代法，使用两个栈模拟递归过程
    
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        
        Stack<TreeNode> stack1 = new Stack<>();
        Stack<TreeNode> stack2 = new Stack<>();
        
        stack1.push(root);
        
        while (!stack1.isEmpty()) {
            TreeNode node = stack1.pop();
            stack2.push(node);
            
            if (node.left != null) {
                stack1.push(node.left);
            }
            if (node.right != null) {
                stack1.push(node.right);
            }
        }
        
        while (!stack2.isEmpty()) {
            result.add(stack2.pop().val);
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        LT145BinaryTreePostorderTraversal solution = new LT145BinaryTreePostorderTraversal();
        
        // 创建测试用的二叉树
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        
        List<Integer> result = solution.postorderTraversal(root);
        System.out.println("Test case 1: " + result); // 应该输出[4, 5, 2, 3, 1]
        
        // 创建空二叉树
        TreeNode nullRoot = null;
        List<Integer> nullResult = solution.postorderTraversal(nullRoot);
        System.out.println("Test case 2: " + nullResult); // 应该输出[]
    }
}