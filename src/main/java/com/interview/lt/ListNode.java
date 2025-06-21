package com.interview.lt;


/**
 * @author yangkuo
 * @date 2025/6/21
 * @description
 */
public class ListNode<T> {
    T val;
    ListNode next;
    ListNode() {}


    ListNode(T x) {
        val = x;
        next = null;
    }

    ListNode(T x, ListNode nextNode) {
        val = x;
        this.next = nextNode;
    }
        
}
