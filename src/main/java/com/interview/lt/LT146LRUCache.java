// 添加LT146LRU缓存机制问题解决方案
import java.util.HashMap;
import java.util.Map;

// 定义双向链表节点类
class DLinkedNode {
    int key;
    int value;
    DLinkedNode prev;
    DLinkedNode next;
    
    public DLinkedNode() {}
    
    public DLinkedNode(int _key, int _value) {
        key = _key;
        value = _value;
    }
}

public class LT146LRUCache {
    // 题目：LRU缓存机制（LRU Cache）
    // 难度：中等
    // 描述：设计和实现一个LRU（最近最少使用）缓存机制。
    // 解法：哈希表+双向链表，get和put操作的时间复杂度均为O(1)
    
    private Map<Integer, DLinkedNode> cache;
    private int size;
    private int capacity;
    private DLinkedNode head;
    private DLinkedNode tail;
    
    public LT146LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        
        // 使用伪头部和伪尾部简化操作
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
        
        cache = new HashMap<>();
    }
    
    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1; // 不存在该键
        }
        // 如果存在，则移到头部表示最近使用
        moveToHead(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 创建新节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加到哈希表
            cache.put(key, newNode);
            // 添加到双向链表头部
            addToHead(newNode);
            size++;
            
            // 如果超出容量，删除双向链表尾部节点
            if (size > capacity) {
                // 删除尾部节点
                DLinkedNode tailNode = removeTail();
                // 从哈希表中删除
                cache.remove(tailNode.key);
                size--;
            }
        } else {
            // 更新值，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }
    
    // 在头部添加节点
    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    
    // 移动节点到头部
    private void moveToHead(DLinkedNode node) {
        // 先删除节点
        removeNode(node);
        // 再添加到头部
        addToHead(node);
    }
    
    // 删除节点
    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    // 删除尾部节点
    private DLinkedNode removeTail() {
        DLinkedNode node = tail.prev;
        removeNode(node);
        return node;
    }
    
    public static void main(String[] args) {
        LT146LRUCache lruCache = new LT146LRUCache(2);
        
        System.out.println("Test case 1: " + lruCache.get(2)); // 应该输出-1
        
        lruCache.put(1, 1);
        lruCache.put(2, 2);
        System.out.println("Test case 2: " + lruCache.get(1)); // 应该输出1
        
        lruCache.put(3, 3); // 使2失效
        System.out.println("Test case 3: " + lruCache.get(2)); // 应该输出-1
        
        lruCache.put(4, 4);
        System.out.println("Test case 4: " + lruCache.get(1)); // 应该输出-1
        System.out.println("Test case 5: " + lruCache.get(3)); // 应该输出3
        System.out.println("Test case 6: " + lruCache.get(4)); // 应该输出4
    }
}