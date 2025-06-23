package com.interview.langzhi;

/**
 * 应用场景：游戏物品栏管理
 * 游戏场景，玩家在背包中按栈方式存放物品（后放入的优先使用），但某些任务需要按队列顺序取出物品（先进先出）。
 * @author yangkuo
 * @date 2025/6/20
 * @description
 */
public class GameInventory <T>{
    private NewStackPipe<T> inventory = new NewStackPipe<>();

    public void addItem(T item) {
        inventory.push(item);
    }

    public T useItem() {
        // 栈式使用（后放先用）
        if (inventory.isEmpty()) {
            return null;
        }
        return inventory.pop();
    }

    public T useItemInQueue() {
        // 队列式使用（先放先用）
        if (inventory.isEmpty()) {
            return null;
        }
        return inventory.pop2();
    }

}
