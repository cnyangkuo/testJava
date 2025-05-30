package com.practice;

import java.util.*;

/**
 * @author yangkuo
 * @date 2023/9/5
 * @description
 */
public class TaskScheduler {
    private List<Task> tasks;

    public TaskScheduler() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public void scheduleTasks() {
        // 构建任务图
        Map<Task, List<Task>> taskGraphMap = new HashMap<>();
        for (Task task: tasks) {
            taskGraphMap.put(task, new ArrayList<>());
            for (Task dependency : task.dependencies) {
                taskGraphMap.get(task).add(dependency);
            }
        }

        // 任务排序
        List<Task> sortedTasks = sortTask(taskGraphMap);

        // 任务执行
        for (Task task : sortedTasks) {
            executeTask(task);
        }
    }

    private void executeTask(Task task) {
        // 模拟任务执行
        System.out.println("execute task " + task.name);
    }

    /**
     * 对task进行排序
     * @param taskGraphMap
     * @return
     */
    private List<Task> sortTask(Map<Task, List<Task>> taskGraphMap) {
        List<Task> result = new ArrayList<>();
        // 已经访问过的task
        Set<Task> visitedTaskSet = new HashSet<>();
        // 已经没有依赖的task
        Set<Task> readyTaskSet = new HashSet<>();
        while (!taskGraphMap.isEmpty()) {
            Iterator iterator = taskGraphMap.keySet().iterator();
            while(iterator.hasNext()) {
                Task task = (Task) iterator.next();
                List<Task> dependencies = taskGraphMap.get(task);

                if (dependencies.isEmpty()|| dependencies.stream().allMatch(readyTaskSet::contains)) {
                    readyTaskSet.add(task);
                    iterator.remove();
                    if(!visitedTaskSet.contains(task)){
                        result.add(task);
                    }
                }
            }
        }
        return result;
    }

    private void resetTask() {
        this.tasks = new ArrayList<>();
    }

    public static void main(String[] args) {
        TaskScheduler taskScheduler = new TaskScheduler();
//        taskScheduler.addTask(new Task("A1"));
//        taskScheduler.addTask(new Task("A2"));
//        taskScheduler.addTask(new Task("A3"));
//        taskScheduler.scheduleTasks();
//        taskScheduler.resetTask();

        Task taskB1 = new Task("B1");
        Task taskB2 = new Task("B2");
        Task taskB3 = new Task("B3");
        taskB1.addDependencies(taskB3);
        taskB3.addDependencies(taskB2);

        taskScheduler.addTask(taskB1);
        taskScheduler.addTask(taskB2);
        taskScheduler.addTask(taskB3);
        taskScheduler.scheduleTasks();
        taskScheduler.resetTask();
    }



}
