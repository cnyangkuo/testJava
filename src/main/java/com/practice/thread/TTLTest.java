package com.practice.thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;
import jodd.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author yangkuo
 * @date 2025/5/5
 * @description transmittable-thread-local
 */
public class TTLTest {
    // 声明一个线程池
    private static final ExecutorService pool = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));
    // 声明一个TransmittableThreadLocal全局变量
    private static final ThreadLocal<String> tl = new TransmittableThreadLocal<>();
    private static final Logger log = LoggerFactory.getLogger(TTLTest.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        String s3 = new String("xyz");
        String s4 = new String("xyz");
        System.out.println(s3.hashCode()==s4.hashCode());
        System.out.println(s3.equals(s4));
        // new关键字会强制创建新的对象，即使字符串池中已经有相同的字符串。因此，s3和s4指向不同的内存地址，导致"=="比较为false。
        System.out.println(s3==s4);

        // 调用 intern() 后，JVM 会检查常量池是否有相同内容的字符串：
        //如果有，返回常量池中的引用。
        //如果没有，将当前字符串加入常量池并返回引用。
        String s31 = new String("xyz").intern();
        String s41 = new String("xyz").intern();
        System.out.println(s31 == s41);  // true

        /**
         * 编译期优化：Java 编译器会将字面量 "abc" 存入 字符串常量池（String Pool）。
         * 运行期复用：当再次遇到相同的字面量时，JVM 会直接复用常量池中的对象，而不是创建新对象。
         */
        String s1 = "abc";
        String s2 = "abc";
        System.out.println(s1==s2);

//        ThreadLocal<String> tl1 = new ThreadLocal<>();
//        tl1.set("hello1");
//        ThreadLocal<String> tl2 = new ThreadLocal<>();
//        tl2.set("hello2");
//        tl1.set("hello2");
//        tl1.remove();
//        tl1.set("hello3");
//        ThreadLocal<Integer> tl3 = new ThreadLocal<>();
//        tl3.set(new Integer(3210));
//        System.out.println(tl3.get());
////        System.out.println(new BigDecimal("102341241234123411340.23"));
    }

    public static void mainCompletableFuture(String[] args) throws InterruptedException, ExecutionException {
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

    public static void main1(String[] args) throws InterruptedException {
        // 创建一个线程池，核心线程数为1，最大线程数为2
        // 当线程数大于核心线程数时，多余的空闲线程存活的最长时间为60秒，
        // 任务队列为容量为1的ArrayBlockingQueue，饱和策略为CallerRunsPolicy。
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
            2,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(1),
            new ThreadPoolExecutor.CallerRunsPolicy());

        // 提交第一个任务，由核心线程执行
        threadPoolExecutor.execute(() -> {
            System.out.println(new java.util.Date().toString() + "核心线程执行第一个任务");
            ThreadUtil.sleep(8000);
        });

        // 提交第二个任务，由于核心线程被占用，任务将进入队列等待
        threadPoolExecutor.execute(() -> {
            System.out.println(new java.util.Date().toString() + "非核心线程处理入队的第二个任务");
            ThreadUtil.sleep(10000);
        });

        // 提交第三个任务，由于核心线程被占用且队列已满，创建非核心线程处理
        threadPoolExecutor.execute(() -> {
            System.out.println(new java.util.Date().toString() + "非核心线程处理第三个任务");
            ThreadUtil.sleep(11000);
        });

        // 提交第四个任务，由于核心线程和非核心线程都被占用，队列也满了，根据CallerRunsPolicy策略，任务将由提交任务的线程（即主线程）来执行
        threadPoolExecutor.execute(() -> {
            System.out.println(new java.util.Date().toString() + "主线程处理第四个任务");
            ThreadUtil.sleep(15000);
        });
        System.out.println(new java.util.Date().toString() + "waiting...?");

        // 提交第五个任务，主线程被第四个任务卡住，该任务必须等到主线程执行完才能提交
        threadPoolExecutor.execute(() -> {
            System.out.println(new java.util.Date().toString() + "核心线程执行第五个任务");
        });

        System.out.println(new java.util.Date().toString() + "waiting...7");
        Thread.sleep(7000);

        // 关闭线程池
        threadPoolExecutor.shutdown();
        if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
            threadPoolExecutor.shutdownNow(); // 强制关闭
        }
    }

    public static void main2(String[] args) {
        // 设置一个值
        tl.set("张三");

        System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
        // 声明一个Runnable对象
        Runnable r1 = () -> {
            System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("after 2 seconds " + Thread.currentThread().getName() + "--->" + tl.get());
        };
        // 向线程池提交
        pool.submit(r1);
        // 声明一个Callable对象
        Callable<String> c1 = () -> {
            System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("after 2 seconds " + Thread.currentThread().getName() + "--->" + tl.get());
            return "success";
        };
        // 向线程池提交
        pool.submit(c1);

        tl.set("李四"); // 更新tl中的值
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "--->" + tl.get());

        // 声明一个Runnable对象
        Runnable r2 = () -> {
            System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
        };
        // 向线程池提交
        pool.submit(r2);
        // 声明一个Callable对象
        Callable<String> c2 = () -> {
            System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
            return "success";
        };
        // 向线程池提交
        pool.submit(c2);

        // 关闭线程池
        pool.shutdown();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    // 声明一个线程池
//    private static final ExecutorService pool = Executors.newFixedThreadPool(2);
//    // 声明一个InheritableThreadLocal全局变量
//    private static final ThreadLocal<String> tl = new InheritableThreadLocal<>();
//
//    public static void main(String[] args) {
//        // 设置一个值
//        tl.set("张三");
//
//        System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
//        // 声明一个Runnable对象
//        Runnable r1 = () -> {
//            System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
//        };
//        // 向线程池提交
//        pool.submit(r1);
//        // 声明一个Callable对象
//        Callable<String> c1 = () -> {
//            System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
//            return "success";
//        };
//        // 向线程池提交
//        pool.submit(c1);
//
//        tl.set("李四"); // 更新tl中的值
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        /**
//         * main--->张三
//         * pool-1-thread-1--->张三
//         * pool-1-thread-2--->张三
//         * main--->李四
//         * pool-1-thread-1--->张三
//         * pool-1-thread-2--->张三
//         */
//
//        System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
//
//        // 声明一个Runnable对象
//        Runnable r2 = () -> {
//            System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
//        };
//        // 向线程池提交
//        pool.submit(r2);
//        // 声明一个Callable对象
//        Callable<String> c2 = () -> {
//            System.out.println(Thread.currentThread().getName() + "--->" + tl.get());
//            return "success";
//        };
//        // 向线程池提交
//        pool.submit(c2);
//
//        // 关闭线程池
//        pool.shutdown();
//    }
}