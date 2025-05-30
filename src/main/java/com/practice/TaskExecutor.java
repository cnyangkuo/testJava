package com.practice;

/**
 * @author yangkuo
 * @date 2023/9/5
 * @description
 */
import java.util.*;
import java.util.concurrent.*;

public class TaskExecutor {
    private Map<String, NewTask> taskMap = new HashMap<>();
    private ExecutorService executorService;

    public TaskExecutor(int maxThreads) {
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    public void addTask(String taskId, Runnable runnable) {
        NewTask NewTask = new NewTask(taskId, runnable);
        taskMap.put(taskId, NewTask);
    }

    public void addDependency(String taskId, String dependencyId) {
        if (taskMap.containsKey(taskId) && taskMap.containsKey(dependencyId)) {
            NewTask newTask = taskMap.get(taskId);
            NewTask dependency = taskMap.get(dependencyId);
            newTask.addDependency(dependency);
        }
    }

    public void executeTasks() {
        List<Future<?>> futures = new ArrayList<>();

        // Find tasks without dependencies and submit them to the executor
        for (NewTask task : taskMap.values()) {
            if (task.getDependencies().isEmpty()) {
                Future<?> future = executorService.submit(task.getRunnable());
                futures.add(future);
            }
        }

        // Wait for all tasks to complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shutdown the executor service
        executorService.shutdown();
    }

    public static void main(String[] args) {
        TaskExecutor executor = new TaskExecutor(4);

        // Define tasks and dependencies
        executor.addTask("Task1", () -> System.out.println("NewTask 1 executed"));
        executor.addTask("Task2", () -> System.out.println("NewTask 2 executed"));
        executor.addTask("Task3", () -> System.out.println("NewTask 3 executed"));

        executor.addDependency("Task2", "Task1");
        executor.addDependency("Task3", "Task2");

        // Execute tasks
        executor.executeTasks();
    }
}

class NewTask {
    private String id;
    private Runnable runnable;
    private List<NewTask> dependencies = new ArrayList<>();

    public NewTask(String id, Runnable runnable) {
        this.id = id;
        this.runnable = runnable;
    }

    public String getId() {
        return id;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void addDependency(NewTask dependency) {
        dependencies.add(dependency);
    }

    public List<NewTask> getDependencies() {
        return dependencies;
    }
}
