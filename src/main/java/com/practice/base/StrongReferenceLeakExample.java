package com.practice.base;

import java.lang.reflect.Field;

public class StrongReferenceLeakExample {
    // 模拟一个 ThreadLocalMap，假设 Entry 的 key 是强引用
    static class ThreadLocalMap {
        static class Entry {
            Object key;   // 强引用
            Object value;
            Entry(Object key, Object value) {
                this.key = key;
                this.value = value;
            }
        }

        private Entry[] table = new Entry[16];
        private int size = 0;

        void set(Object key, Object value) {
            table[size++] = new Entry(key, value);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        weakReferenceNoLeakExample2();
        weakReferenceNoLeakExample();
        strongReferenceNoLeakExample();
    }

    public static void weakReferenceNoLeakExample2() throws InterruptedException {
        Thread t = new Thread(()->test("abc",false));
        t.start();
        t.join();
        System.out.println("--with gc后--");
        Thread t2 = new Thread(() -> test("def", true));
        t2.start();
        t2.join();
        System.out.println("----");
    }

    private static void test(String s,boolean isGC)  {
        try {
            // 方式1 -- 方式1的匿名实例ThreadLocal 实例无引用，更容易被回收
            new ThreadLocal<>().set(s);  //打印出   弱引用key:null,值:def

            // 方式2 -- 创建了一个局部变量threadLocal引用该实例。
            // 虽然之后没有显式地置为null，但在test方法结束后，
            // threadLocal变量的作用域结束，局部变量表中的引用会被释放。
//            ThreadLocal<Object> threadLocal = new ThreadLocal<>();
//            threadLocal.set(s);
                                        //打印出    弱引用key:java.lang.ThreadLocal@3cb67b59,值:def
            if (isGC) {
                System.gc();
            }
            Thread t = Thread.currentThread();
            Class<? extends Thread> clz = t.getClass();
            Field field = clz.getDeclaredField("threadLocals");
            field.setAccessible(true);
            Object ThreadLocalMap = field.get(t);
            Class<?> tlmClass = ThreadLocalMap.getClass();
            Field tableField = tlmClass.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] arr = (Object[]) tableField.get(ThreadLocalMap);
            for (Object o : arr) {
                if (o != null) {
                    Class<?> entryClass = o.getClass();
                    Field valueField = entryClass.getDeclaredField("value");
                    Field referenceField = entryClass.getSuperclass().getSuperclass().getDeclaredField("referent");
                    valueField.setAccessible(true);
                    referenceField.setAccessible(true);
                    System.out.println(String.format("弱引用key:%s,值:%s", referenceField.get(o), valueField.get(o)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void strongReferenceNoLeakExample() throws InterruptedException {
        ThreadLocalMap map = new ThreadLocalMap();

        // 创建一个 ThreadLocal 实例并设置大对象
        Object threadLocal = new Object();
        map.set(threadLocal, new byte[10 * 1024 * 1024]); // 10MB 的 value

        // 将 threadLocal 置为 null，试图释放它
        threadLocal = null;

        // 强制触发 GC
        System.gc();
        Thread.sleep(5000);

        // 检查 Entry 的 key 是否被回收
        for (ThreadLocalMap.Entry entry : map.table) {
            if (entry != null) {
                System.out.println("strongReferenceNoLeakExample Entry 的 key 是否存活: " + (entry.key != null));
                // 输出：Entry 的 key 是否存活: true（强引用导致无法回收）
            }
        }
    }

    public static void weakReferenceNoLeakExample() throws InterruptedException {
        ThreadLocal<byte[]> threadLocal = new ThreadLocal<>();

        // 设置大对象
        threadLocal.set(new byte[10 * 1024 * 1024]); // 10MB

        // 将 threadLocal 置为 null，试图释放它
        threadLocal = null;

        // 强制触发 GC
        System.gc();
        Thread.sleep(5000);

        // 检查 ThreadLocalMap 是否已清理（需通过反射查看）
        try {
            Thread thread = Thread.currentThread();
            Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
            threadLocalsField.setAccessible(true);
            Object threadLocals = threadLocalsField.get(thread);

            if (threadLocals != null) {
                Class<?> tlmClass = threadLocals.getClass();
                Field tableField = tlmClass.getDeclaredField("table");
                tableField.setAccessible(true);
                Object[] entries = (Object[]) tableField.get(threadLocals);

                for (Object entry : entries) {
                    if (entry != null) {
                        Field valueField = entry.getClass().getDeclaredField("value");
                        valueField.setAccessible(true);
                        Object value = valueField.get(entry);
                        System.out.println("weakReferenceNoLeakExample Entry 的 value 是否存活: " + (value != null));
                        // 输出：Entry 的 value 是否存活: false（key 被回收后，value 被清理）
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}