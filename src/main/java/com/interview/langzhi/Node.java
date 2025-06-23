package com.interview.langzhi;

/**
 * 双向链表节点类
 * @author yangkuo
 * @date 2025/6/19
 * @description
 */
public class Node<T> {
    // 节点数据
    private T data;
    // 指向上方节点
    private Node<T> up;
    // 指向下方节点
    private Node<T> down;

    public Node(T data) {
        this.data = data;
        this.up = null;
        this.down = null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getUp() {
        return up;
    }

    public void setUp(Node<T> up) {
        this.up = up;
    }

    public Node<T> getDown() {
        return down;
    }

    public void setDown(Node<T> down) {
        this.down = down;
    }

}