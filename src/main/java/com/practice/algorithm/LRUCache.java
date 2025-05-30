package com.practice.algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yangkuo
 * @date 2025/5/26
 * @description
 */
public class LRUCache<K, V> {
    // 缓存容量
    private final int capacity;
    // 哈希表，存储键到节点的映射
    private final Map<K, Node<K, V>> map;
    // 双向链表的虚拟头尾节点
    private final Node<K, V> head, tail;
    // 当前缓存大小
    private int size;

    // 节点类
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public String toString() {
            return  key + ": " + value;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node<>(null, null);
        this.tail = new Node<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    // 获取元素
    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) {return null;}
        moveToHead(node); // 移动到头部
        return node.value;
    }

    // 插入元素
    public void put(K key, V value) {
        Node<K, V> node = map.get(key);
        if (node != null) {
            // 如果已存在，更新值并移动到头部
            node.value = value;
            moveToHead(node);
        } else {
            // 否则创建新节点
            Node<K, V> newNode = new Node<>(key, value);
            map.put(key, newNode);
            addNode(newNode);
            if (size > capacity) {
                // 缓存满时删除尾部节点
                Node<K, V> removed = removeTail();
                map.remove(removed.key);
            }
        }
    }

    // 将节点移动到头部
    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addNode(node);
    }

    // 添加节点到头部
    private void addNode(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        size++;
    }

    // 删除节点
    private void removeNode(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }

    // 删除尾部节点
    private Node<K, V> removeTail() {
        Node<K, V> res = tail.prev;
        removeNode(res);
        return res;
    }

    // 打印链表中的元素，从头到尾
    public String printListFromHead() {
        StringBuilder sb = new StringBuilder();
        Node<K, V> current = head.next;
        while (current != tail) {
            sb.append(current.key).append(": ").append(current.value).append(", ");
            current = current.next;
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // 去掉最后的逗号和空格
        }
        return "[" + sb.toString() + "]";
    }

    // 使用 LinkedHashMap 实现的 LRU 缓存
    private static class LinkedHashMapLRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public LinkedHashMapLRUCache(int capacity) {
            super(capacity, 0.75f, true); // accessOrder = true 表示按访问顺序排序
            this.capacity = capacity;
        }

        // 控制缓存大小，超过容量时移除最久未使用的条目
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }

        /**
         * 遍历打印输出K、V，只输出一行
         */
        public void printKVs(){
            System.out.print("[");
            Iterator<Map.Entry<K, V>> iterator = entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, V> entry = iterator.next();
                System.out.print(entry.getKey() + ":" + entry.getValue());
                if (iterator.hasNext()) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }


    // 示例测试方法
    public static void linkedHashMapBasedTest() {
        LinkedHashMapLRUCache cache = new LinkedHashMapLRUCache<>(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.printKVs();

        cache.get(2);
        cache.printKVs();

        cache.put(4, "D");
        cache.printKVs();
    }

    // 示例测试
    public static void main(String[] args) {
        System.out.println("---solution1----");
        linkedHashMapBasedTest();
        System.out.println("---solution2----");
        LRUCache<Integer, String> cache = new LRUCache<>(3);

        // 插入元素
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println("Initial cache: " + cache.printListFromHead()); // [A, B, C]

        // 访问元素 2
        System.out.println("Get 2: " + cache.get(2)); // B
        System.out.println("After accessing 2: " + cache.printListFromHead()); // [A, C, B]

        // 插入新元素 4
        cache.put(4, "D");
        System.out.println("After inserting 4: " + cache.printListFromHead()); // [C, B, D]
    }
}