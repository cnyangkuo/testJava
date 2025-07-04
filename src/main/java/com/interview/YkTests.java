package com.interview;

import com.interview.sc.DataStructureExplanation;
import com.interview.sc.ForkJoinBatchProcessor;
import com.interview.sc.StreamProcessingDemo;

import java.util.List;

/**
 * YkApplicationTests
 * 包含对新增类的单元测试
 */
public class YkTests {

    public static void main(String[] args) {
        YkTests tests = new YkTests();
        tests.testStreamProcessing();
        tests.testForkJoinProcessing();
        tests.testDataStructureExplanation();
    }

    public void testStreamProcessing() {
        StreamProcessingDemo demo = new StreamProcessingDemo();
        demo.initData();
        
        List<Integer> result = demo.parallelProcess();
        
        notNull(result, "结果列表不应为空");
        isTrue(result.size() == 5, "偶数数量应为5个");
        isTrue(result.get(0) == 20, "最大值应为20");
        
        System.out.println("Stream处理结果: " + result);
    }

    public void testForkJoinProcessing() {
        int[] dataArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int result = ForkJoinBatchProcessor.batchProcess(dataArray);
        
        isTrue(result == 120, "总和应为55");
        System.out.println("ForkJoin处理结果: " + result);
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
    }

    public void testDataStructureExplanation() {
        DataStructureExplanation explanation = new DataStructureExplanation();
        
        // 测试ArrayList扩容
//        explanation.arrayListGrowth(10);
        
        // 测试LinkedList节点管理
        explanation.linkedListNode("TEST");
        
        // 测试HashMap冲突处理
        explanation.hashMapCollisionHandling(100, "VALUE1");
        explanation.hashMapCollisionHandling(132, "VALUE2"); // 制造哈希冲突
    }
}
