package com.interview.lt;

// 添加LT138复制带随机指针的链表问题解决方案
import java.util.HashMap;
import java.util.Map;

// 定义带随机指针的链表节点类
class Node {
    int val;
    Node next;
    Node random;
    
    Node(int x) {
        val = x;
        next = null;
        random = null;
    }
}

public class LT138CopyListWithRandomPointer {
    // 题目：复制带随机指针的链表（Copy List with Random Pointer）
    // 难度：中等
    // 描述：给定一个带随机指针的链表，返回它的深拷贝。
    // 解法：哈希表存储原节点到新节点的映射
    
    public Node copyRandomList(Node head) {
        if (head == null) return null;
        
        Map<Node, Node> map = new HashMap<>();
        Node current = head;
        
        // 第一次遍历：创建新节点并建立映射关系
        while (current != null) {
            map.put(current, new Node(current.val));
            current = current.next;
        }
        
        // 第二次遍历：设置新节点的next和random指针
        current = head;
        while (current != null) {
            Node newNode = map.get(current);
            newNode.next = map.get(current.next);
            newNode.random = map.get(current.random);
            current = current.next;
        }
        
        return map.get(head);
    }
    
    public static void main(String[] args) {
        LT138CopyListWithRandomPointer solution = new LT138CopyListWithRandomPointer();
        
        // 创建测试用的链表
        // 1 -> 2 -> 3 -> 4
        // random指针: 1->3, 2->4, 3->1, 4->2
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n1.random = n3;
        n2.random = n4;
        n3.random = n1;
        n4.random = n2;
        
        Node copied = solution.copyRandomList(n1);
        
        // 打印复制后的链表
        Node p = copied;
        while (p != null) {
            System.out.print("[" + p.val);
            if (p.random != null) {
                System.out.print("->" + p.random.val);
            }
            System.out.print("]");
            if (p.next != null) {
                System.out.print(" -> ");
            }
            p = p.next;
        }
    }
}