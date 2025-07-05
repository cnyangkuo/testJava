package com.practice.thread;

import jodd.util.ThreadUtil;

import java.util.concurrent.*;

/**
 * CompletableFuture 核心知识点示例集
 * 涵盖异步任务创建、链式编排、组合操作、异常处理等核心功能
 * 面试考点：线程池配置、任务依赖关系、异常传播机制
 *
 *
 */
public class CompletableFutureExample {
    // 线程池配置 - 推荐显式声明
    private static final ExecutorService executor = Executors.newFixedThreadPool(2, r -> {
        Thread t = new Thread(r);
        t.setDaemon(false);
        return t;
    });

// 注释说明：
// 面试考点：
// 0. CompletableFuture vs Future  后者只能阻塞获取结果，前者支持回调和链式编排
//          任务取消与超时可通过completeExceptionally()主动中断任务流
// 1. 线程池配置：显式配置比默认线程池更可控
// 2. 任务状态转换：NEW → COMPLETING → DONE 的生命周期管理
// 3. 任务依赖关系：thenApply 传递结果 vs thenCompose 替换整个任务 vs thenCombine 合并结果 的区别
//                thenAccept 最终结果消费-无返回值
// 4. 异常传播机制：异常会跳过后续then步骤直接触发exceptionally
// 5. CompletionStage契约：11种组合方法的使用场景
// 6. ForkJoinPool实现原理：工作窃取算法在CompletableFuture中的应用
// 7. thenApply和thenAccept的内存可见性保障
// 8. 异步方法的双重变体：xxx和xxxAsync的区别

    public static void main(String[] args) throws Exception {
        // 1. 异步任务创建示例
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务1执行线程: " + Thread.currentThread().getName());
            return "Hello";
        }, executor);

        // 2. 任务链式编排（thenApply）
        CompletableFuture<String> future2 = future1.thenApply(result -> {
            System.out.println("任务2处理结果: " + result);
            return result + " World";
        });

        // 3. 任务消费（thenAccept）
        future2.thenAccept(finalResult -> {
            System.out.println("最终结果: " + finalResult);
        });

        // 4. 组合操作（thenCombine） 组合两个独立Future的结果
        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 100, executor);
        CompletableFuture<Integer> future4 = CompletableFuture.supplyAsync(() -> 200, executor);
        future3.thenCombine(future4, (a, b) -> {
            System.out.println("组合操作结果: " + (a + b));
            return a + b;
        });

        // 5. 异常处理（exceptionally）
        CompletableFuture<Integer> errorFuture = CompletableFuture.supplyAsync(() -> {
            if (Math.random() > 0.5) throw new RuntimeException("测试异常");
            return 100;
        }, executor).exceptionally(ex -> {
            System.out.println("捕获异常: " + ex.getMessage());
            return 0; // 默认值
        });

        // 6. 任务依赖（thenRun）
        CompletableFuture<Void> dependentFuture = future2.thenRunAsync(() -> {
            System.out.println("依赖任务执行");
        }, executor);

        // 7. 多任务编排（allOf） 或任一完成（anyOf）
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future2, dependentFuture);
        allFutures.thenRun(() -> System.out.println("所有任务完成"));

        // 等待所有任务完成
        allFutures.join();
        executor.shutdown();

        completableFutureDemo();
    }


    public static void completableFutureDemo() throws InterruptedException, ExecutionException {
        // thenCompose() 按顺序链接两个 CompletableFuture 对象，实现异步的任务链。
        // 它的作用是将前一个任务的返回结果作为下一个任务的输入参数，从而形成一个依赖关系。
        CompletableFuture<String> futureTask2DependsOnTask1
                = CompletableFuture.supplyAsync(() -> {return "Task1先执行hello!";})
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "上面任务完成后执行nice!"));
        assertEquals("Task1先执行hello!上面任务完成后执行nice!", futureTask2DependsOnTask1.get());

        // thenCombine() 会在两个并行任务都执行完成后，把两个任务的结果合并。
        // 两个任务是并行执行的，它们之间并没有先后依赖顺序。
        CompletableFuture<String> completableFuture
                = CompletableFuture.supplyAsync(() -> "Task1hello!")
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> "Task2world!"), (s1, s2) -> "并行执行："+ s1 + s2)
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "上面任务完成后执行nice!"));
        assertEquals("并行执行：Task1hello!Task2world!上面任务完成后执行nice!", completableFuture.get());


        CompletableFuture<String> future1 = new CompletableFuture<>();
        future1.complete("----completed!----");       // 手动完成 future
        future1.thenAccept( s -> System.out.println(s));    // 注册一个消费者打印结果

        CompletableFuture<String> future = CompletableFuture.completedFuture("hello!")
                .thenApply(s -> s + "world").thenApply(s -> s + "!");
        assertEquals("hello!world!", future.get());
        // 这次调用将被忽略， 关键在于：
        // thenApply() 是一个 转换操作（transformation），它会返回一个新的 CompletableFuture，但不会修改原来的 future。
        future.thenApply(s -> s + "nice!");
        assertEquals("hello!world!", future.get());
        assertEquals("hello!world!nice!", future.thenApply(s -> s + "nice!").get());

        // T1: 返回 String 结果
        CompletableFuture<String> futureT1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T1 is executing. Current time：" + new java.util.Date());
            // 模拟耗时操作
            ThreadUtil.sleep(2000);
            return "Result from T1";
        });

        // T2: 返回 String 结果
        CompletableFuture<String> futureT2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("T2 is executing. Current time：" + new java.util.Date());
            ThreadUtil.sleep(2000);
            return "Result from T2";
        });

        // 使用 allOf() 等待两者完成，并获取组合后的结果
        CompletableFuture<Void> bothCompleted = CompletableFuture.allOf(futureT1, futureT2);

        // 组合结果
        // thenApply() 结果转换器 实现流式调用，返回一个新 Future
        CompletableFuture<String> combinedFuture = bothCompleted.thenApply(v -> {
            try {
                // v 是什么，可以打印出来吗？
                System.out.println("v is: " + v);
                String resultT1 = futureT1.get(); // 获取 T1 的结果
                String resultT2 = futureT2.get(); // 获取 T2 的结果
                return resultT1 + " & " + resultT2; // 合并字符串
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // 输出组合后的结果
        // thenAccept() 结果消费者，适合仅需消费结果但不返回新值的场景（如日志记录）
        combinedFuture.thenAccept(finalResult -> {
            System.out.println("Combined Result: " + finalResult);
        });

//        // T1
//        CompletableFuture<Void> futureT1 = CompletableFuture.runAsync(() -> {
//            System.out.println("T1 is executing. Current time：" + new java.util.Date());
//            // 模拟耗时操作
//            ThreadUtil.sleep(2000);
//        });
//        // T2
//        CompletableFuture<Void> futureT2 = CompletableFuture.runAsync(() -> {
//            System.out.println("T2 is executing. Current time：" + new java.util.Date());
//            ThreadUtil.sleep(2000);
//        });
//
//        // 使用allOf()方法合并T1和T2的CompletableFuture，等待它们都完成
//        CompletableFuture<Void> bothCompleted = CompletableFuture.allOf(futureT1, futureT2);
//        // 当T1和T2都完成后，执行T3
//        System.out.println("T3 is waiting for T1 and T2 to complete.");
//        bothCompleted.thenRunAsync(() -> System.out.println("T3 is executing after T1 and T2 have completed.Current time：" + new java.util.Date()));

        // 等待所有任务完成，验证效果
        ThreadUtil.sleep(3000);
    }

    private static void assertEquals(String s, String s1) {
        if (!s.equals(s1)) {
            throw new AssertionError("Expected: " + s + ", but was: " + s1);
        }
    }

}