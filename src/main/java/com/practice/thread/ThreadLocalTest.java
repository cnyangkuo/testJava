package com.practice.thread;

/**
 * @author yangkuo
 * @date 2025/6/14
 * @description
 */
public class ThreadLocalTest {
    // 测试一下线程局部变量，看看针对同一个threadlocal对象，多次设置value后，threadlocalMap的entry对象是否后增加多个staleEntry
    public static void main(String[] args) {
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//        threadLocal.set("hello");
//        threadLocal.set("world2");
//        threadLocal.set("world3");
//        threadLocal.set("world4");
//        threadLocal.set("world5");
//        // 判断是否有staleEntry
//        System.out.println(threadLocal.get().equals("world5"));

        ThreadLocalTest test = new ThreadLocalTest();
        for(int i = 0; i < 10; i++) {
            test.processRequest();
        }
        test.processRequest();
    }
    public void processRequest() {
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>(); // 局部变量
        stringThreadLocal.set("currentUser");
        // ...业务逻辑...
    }
}
