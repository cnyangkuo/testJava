package com.practice.lz;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU缓存实现
 * 考察点：双向链表、哈希表、设计模式
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class LRUCache<K, V> {
    // 双向链表节点定义
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;
        
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    private final int capacity;
    private final Map<K, Node<K, V>> cacheMap;
    // 伪头部和尾部节点，始终有这2个node
    private final Node<K, V> head = new Node<>(null, null);
    private final Node<K, V> tail = new Node<>(null, null);
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>(capacity);
        // 初始化双向链表
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * 题目：实现LRU缓存机制
     * 核心思想：双向链表+哈希表
     * 操作：get/put的时间复杂度均为O(1)
     * @param key 键
     * @return 缓存值
     */
    public V get(K key) {
        Node<K, V> node = cacheMap.get(key);
        if (node == null) return null;
        // 将访问的节点移到头部
        remove(node);
        addToHead(node);
        return node.value;
    }
    
    /**
     * 添加或更新缓存
     * @param key 键
     * @param value 值
     */
    public void put(K key, V value) {
        Node<K, V> node = cacheMap.get(key);
        if (node != null) {
            // 更新值并移到头部
            node.value = value;
            remove(node);
            addToHead(node);
        } else {
            // 淘汰超过容量的节点
            if (cacheMap.size() >= capacity) {
                // 移除尾部节点
                Node<K, V> lru = tail.prev;
                remove(lru);
                cacheMap.remove(lru.key);
            }
            // 添加新节点
            Node<K, V> newNode = new Node<>(key, value);
            cacheMap.put(key, newNode);
            addToHead(newNode);
        }
    }
    
    /**
     * 辅助方法：将节点添加到头部
     * @param node 待添加节点
     */
    private void addToHead(Node<K, V> node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * 辅助方法：移除节点
     * @param node 待移除节点
     */
    private void remove(Node<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    /**
     * 扩展功能：获取当前缓存大小
     * @return 缓存项数
     */
    public int getCurrentSize() {
        return cacheMap.size();
    }
    
    /**
     * 扩展功能：打印缓存内容（按使用顺序）
     */
    public void printCache() {
        Node<K, V> current = head.next;
        while (current != tail) {
            System.out.print("(" + current.key + ", " + current.value + ") -> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
    public static void main(String[] args) {
        // 测试用例1：基本操作
        LRUCache<Integer, String> cache1 = new LRUCache<>(2);
        System.out.println("测试LRU缓存容量=2");
        
        cache1.put(1, "one");
        cache1.put(2, "two");
        System.out.println("插入(1,one),(2,two)后:");
        cache1.printCache();
        
        System.out.println("获取1: " + cache1.get(1));
        System.out.println("访问后顺序:");
        cache1.printCache();
        
        cache1.put(3, "three");
        System.out.println("插入(3,three)后（应淘汰2）:");
        cache1.printCache();
        
        // 测试用例2：泛型支持
        LRUCache<String, Integer> cache2 = new LRUCache<>(3);
        System.out.println("\n测试泛型支持，String键Integer值");
        
        cache2.put("a", 1);
        cache2.put("b", 2);
        cache2.put("c", 3);
        System.out.println("插入(a,1),(b,2),(c,3)后:");
        cache2.printCache();
        
        cache2.get("a");
        System.out.println("访问a后:");
        cache2.printCache();
        
        cache2.put("d", 4);
        System.out.println("插入(d,4)后（应淘汰b）:");
        cache2.printCache();
    }
}