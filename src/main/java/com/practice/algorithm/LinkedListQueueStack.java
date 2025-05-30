package com.practice.algorithm;

/**
 * 常见面试算法结构实现（链表、FIFO 队列、LIFO 栈）
 */
public class LinkedListQueueStack {
    // 单向链表节点
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    // 链表操作工具类
    static class LinkedListUtil {
        // 头插法
        public static ListNode insertAtHead(ListNode head, int val) {
            ListNode newHead = new ListNode(val);
            newHead.next = head;
            return newHead;
        }

        // 尾插法
        public static ListNode insertAtTail(ListNode head, int val) {
            if (head == null) return new ListNode(val);
            ListNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = new ListNode(val);
            return head;
        }

        // 删除指定值的节点
        public static ListNode delete(ListNode head, int target) {
            if (head == null) return null;
            if (head.val == target) return head.next;

            ListNode prev = head;
            ListNode curr = head.next;
            while (curr != null && curr.val != target) {
                prev = curr;
                curr = curr.next;
            }

            if (curr != null) {
                prev.next = curr.next;
            }
            return head;
        }

        // 打印链表
        public static void printList(ListNode head) {
            ListNode curr = head;
            while (curr != null) {
                System.out.print(curr.val + " -> ");
                curr = curr.next;
            }
            System.out.println("null");
        }
    }

    // FIFO 队列实现
    static class Queue {
        private ListNode front, rear;

        public void enqueue(int val) {
            ListNode newNode = new ListNode(val);
            if (rear == null) {
                front = rear = newNode;
                return;
            }
            rear.next = newNode;
            rear = newNode;
        }

        public int dequeue() {
            if (front == null) throw new IllegalStateException("Queue is empty");
            int val = front.val;
            front = front.next;
            if (front == null) rear = null;
            return val;
        }

        public boolean isEmpty() {
            return front == null;
        }

        public void print() {
            ListNode curr = front;
            while (curr != null) {
                System.out.print(curr.val + " -> ");
                curr = curr.next;
            }
            System.out.println("null");
        }
    }

    // LIFO 栈实现
    static class Stack {
        private ListNode top;

        public void push(int val) {
            ListNode newNode = new ListNode(val);
            newNode.next = top;
            top = newNode;
        }

        public int pop() {
            if (top == null) throw new IllegalStateException("Stack is empty");
            int val = top.val;
            top = top.next;
            return val;
        }

        public int peek() {
            if (top == null) throw new IllegalStateException("Stack is empty");
            return top.val;
        }

        public boolean isEmpty() {
            return top == null;
        }

        public void print() {
            ListNode curr = top;
            while (curr != null) {
                System.out.print(curr.val + " -> ");
                curr = curr.next;
            }
            System.out.println("null");
        }
    }

    // 双向链表节点 - 泛型版本
    static class DoublyListNode<T> {
        T val;
        DoublyListNode<T> prev;
        DoublyListNode<T> next;

        DoublyListNode(T val) {
            this.val = val;
        }
    }

    // 双向链表操作工具类 - 泛型版本
    static class DoublyLinkedListUtil<T> {
        // 头插法
        public DoublyListNode<T> insertAtHead(DoublyListNode<T> head, T val) {
            DoublyListNode<T> newHead = new DoublyListNode<>(val);
            if (head != null) {
                newHead.next = head;
                head.prev = newHead;
            }
            return newHead;
        }

        // 尾插法
        public DoublyListNode<T> insertAtTail(DoublyListNode<T> head, T val) {
            if (head == null) return new DoublyListNode<>(val);

            DoublyListNode<T> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }

            DoublyListNode<T> newNode = new DoublyListNode<>(val);
            curr.next = newNode;
            newNode.prev = curr;
            return head;
        }

        // 删除指定值的节点
        public DoublyListNode<T> delete(DoublyListNode<T> head, T target) {
            if (head == null) return null;

            DoublyListNode<T> curr = head;
            while (curr != null && !curr.val.equals(target)) {
                curr = curr.next;
            }

            if (curr == null) return head; // 未找到目标

            if (curr.prev != null) {
                curr.prev.next = curr.next;
            } else {
                head = curr.next; // 删除头节点
            }

            if (curr.next != null) {
                curr.next.prev = curr.prev;
            }

            return head;
        }

        // 正向打印链表
        public void printForward(DoublyListNode<T> head) {
            DoublyListNode<T> curr = head;
            while (curr != null) {
                System.out.print(curr.val + " <-> ");
                curr = curr.next;
            }
            System.out.println("null");
        }

        // 反向打印链表（从尾到头）
        public void printBackward(DoublyListNode<T> head) {
            if (head == null) return;

            DoublyListNode<T> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }

            while (curr != null) {
                System.out.print(curr.val + " <-> ");
                curr = curr.prev;
            }
            System.out.println("null");
        }
    }

    // 测试 main 方法
    public static void main(String[] args) {
        System.out.println("=== 测试链表 ===");
        ListNode head = new ListNode(1);
        head = LinkedListUtil.insertAtTail(head, 2);
        head = LinkedListUtil.insertAtTail(head, 3);
        head = LinkedListUtil.insertAtHead(head, 0);
        LinkedListUtil.printList(head);
        head = LinkedListUtil.delete(head, 2);
        LinkedListUtil.printList(head);

        System.out.println("\n=== 测试队列（FIFO） ===");
        Queue queue = new Queue();
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.print();
        System.out.println("Dequeue: " + queue.dequeue());
        queue.print();

        System.out.println("\n=== 测试栈（LIFO） ===");
        Stack stack = new Stack();
        stack.push(100);
        stack.push(200);
        stack.push(300);
        stack.print();
        System.out.println("Pop: " + stack.pop());
        System.out.println("Peek: " + stack.peek());
        stack.print();

        // 测试双向链表
        System.out.println("\n=== 测试泛型双向链表 ===");
        DoublyLinkedListUtil<String> listUtil = new DoublyLinkedListUtil<>();
        DoublyListNode<String> dHead = new DoublyListNode("A1");
        dHead = listUtil.insertAtHead(dHead, "A0");
        dHead = listUtil.insertAtTail(dHead, "A2");
        dHead = listUtil.insertAtTail(dHead, "A3");
        listUtil.printForward(dHead); // A0 <-> A1 <-> A2 <-> A3 <-> null
        listUtil.printBackward(dHead); // A3 <-> A2 <-> A1 <-> A0 <-> null

        dHead = listUtil.delete(dHead, "A2");
        listUtil.printForward(dHead); // A0 <-> A1 <-> A3 <-> null
    }
}