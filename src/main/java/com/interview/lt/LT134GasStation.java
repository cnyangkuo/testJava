package com.interview.lt;

/**
 * 134. 加油站
 * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
 * 你有一辆油箱容量无限的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。
 * 你可以选择任意一个加油站作为起点，但返回绕环路行驶一周的起始加油站索引。
 * 难度: 中等
 * 标签: 贪心算法
 */
public class LT134GasStation {
    // 使用贪心算法解决加油站问题
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length == 0 || cost.length == 0) {
            return -1;
        }
        
        int totalTank = 0; // 总油量变化
        int currentTank = 0; // 当前油量
        int startingIndex = 0; // 起始加油站索引
        
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            totalTank += diff;
            currentTank += diff;
            
            // 如果当前油量小于0，说明从该加油站之前的任何位置作为起点都会失败
            // 因此将起始位置设置为下一个加油站
            if (currentTank < 0) {
                currentTank = 0;
                startingIndex = i + 1;
            }
        }
        
        // 如果总油量大于等于0，说明可以完成环路行驶
        return totalTank >= 0 ? startingIndex : -1;
    }

    // 测试用例
    public static void main(String[] args) {
        LT134GasStation solution = new LT134GasStation();
        
        // 测试案例1
        int[] gas1 = {1, 2, 3, 4, 5};
        int[] cost1 = {4, 5, 1, 5, 1};
        System.out.println("测试案例1结果: " + solution.canCompleteCircuit(gas1, cost1)); // 应该输出4
        
        // 测试案例2
        int[] gas2 = {2, 3, 4};
        int[] cost2 = {3, 4, 3};
        System.out.println("测试案例2结果: " + solution.canCompleteCircuit(gas2, cost2)); // 应该输出-1
        
        // 测试案例3
        int[] gas3 = {5, 1, 2, 3, 4};
        int[] cost3 = {4, 4, 1, 5, 1};
        System.out.println("测试案例3结果: " + solution.canCompleteCircuit(gas3, cost3)); // 应该输出0
    }
}