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