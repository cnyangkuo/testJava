package com.interview.sc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.lang.reflect.Field;

/**
 * 数据结构底层实现解析
 * 1. ArrayList动态扩容机制
 * 2. LinkedList双向链表实现
 * 3. HashMap哈希冲突解决
 */
public class DataStructureExplanation {
    // 使用原生类示例
    private final ArrayList<Integer> arrayList;
    private final LinkedList<String> linkedList;
    private final HashMap<Integer, String> hashMap;

    public DataStructureExplanation() {
        this.arrayList = new ArrayList<>();
        this.linkedList = new LinkedList<>();
        this.hashMap = new HashMap<>();
    }

    /**
     * 演示ArrayList扩容机制
     * @param size 初始容量
     * 
     * 底层实现：
     * 1. 使用elementData数组存储元素
     * 2. 扩容时使用Arrays.copyOf（基于System.arraycopy）
     * 3. 默认负载因子为1，每次扩容1.5倍
     */
    public void arrayListGrowth(int size) {
        for (int i = 0; i < size; i++) {
            arrayList.add(i);
            if (i % 3 == 2) {
                // 输出容量变化
                System.out.println("ArrayList capacity after add " + (i+1) + " elements: " + getArrayListCapacity());
            }
        }
    }

    /**
     * 获取ArrayList当前容量
     * 使用反射访问内部数组
     * @return 容量大小
     */
    private int getArrayListCapacity() {
        try {
            Field field = ArrayList.class.getDeclaredField("elementData");
            field.setAccessible(true);
            return ((Object[]) field.get(arrayList)).length;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get array list capacity", e);
        }
    }

    /**
     * 演示LinkedList节点管理
     * @param element 元素值
     * 
     * 底层实现：
     * 1. Node类包含prev/next指针
     * 2. 使用CAS操作保证线程安全
     * 3. 双向链表结构特性
     */
    public void linkedListNode(String element) {
        linkedList.add(element);
        linkedList.addFirst("HEAD");
        linkedList.addLast("TAIL");
    }

    /**
     * 演示HashMap哈希冲突处理
     * @param key 键
     * @param value 值
     * 
     * 实现细节：
     * 1. 使用位运算计算桶索引
     * 2. 链表转红黑树阈值为8
     * 3. 扩容阈值(loadFactor * capacity)
     */
    public void hashMapCollisionHandling(int key, String value) {
        hashMap.put(key, value);
        System.out.println("Hash for key " + key + ": " + (key ^ (key >>> 16)));
    }
}