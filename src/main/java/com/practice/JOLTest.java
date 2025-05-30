package com.practice;

import com.lmax.disruptor.Sequence;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author yangkuo 查看对象的内存布局 JOL（Java Object Layout）
 * @date 2025/5/9
 * @description
 */
public class JOLTest {
    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(16);

        System.out.println("打印对象内存布局1=====");
        // 打印对象内存布局
        System.out.println(ClassLayout.parseInstance(queue).toPrintable());
        /**
         java.util.concurrent.ArrayBlockingQueue object internals:
         OFF  SZ                                           TYPE DESCRIPTION                    VALUE
         0   8                                                (object header: mark)          0x0000000000000001 (non-biasable; age: 0)
         8   4                                                (object header: class)         0xf80090ff
         12   4                                            int ArrayBlockingQueue.takeIndex   0
         16   4                                            int ArrayBlockingQueue.putIndex    0
         20   4                                            int ArrayBlockingQueue.count       0
         24   4                             java.lang.Object[] ArrayBlockingQueue.items       [null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]
         28   4       java.util.concurrent.locks.ReentrantLock ArrayBlockingQueue.lock        (object)
         32   4           java.util.concurrent.locks.Condition ArrayBlockingQueue.notEmpty    (object)
         36   4           java.util.concurrent.locks.Condition ArrayBlockingQueue.notFull     (object)
         40   4   java.util.concurrent.ArrayBlockingQueue.Itrs ArrayBlockingQueue.itrs        null
         44   4                                                (object alignment gap)
         Instance size: 48 bytes
         Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
         */

        Sequence sequence = new Sequence(100);
        System.out.println("打印对象内存布局2=====");
        System.out.println(ClassLayout.parseInstance(sequence).toPrintable());

        System.out.println(VM.current().details());
    }
}
