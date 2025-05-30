package com.practice;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.locks.*;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 *      ZooKeeper 分布式锁是基于 临时顺序节点 和 Watcher（事件监听器） 实现的。
 *      获取锁：首先我们要有一个持久节点/locks，客户端获取锁就是在locks下创建临时顺序节点。
 *      假设客户端 1 创建了/locks/lock1节点，创建成功之后，会判断 lock1是否是 /locks 下最小的子节点。
 *      如果 lock1是最小的子节点，则获取锁成功。否则，获取锁失败。如果获取锁失败，则说明有其他的客户端已经成功获取锁。
 *      客户端 1 并不会不停地循环去尝试加锁，而是在前一个节点比如/locks/lock0上注册一个事件监听器。
 *      这个监听器的作用是当前一个节点释放锁之后通知客户端 1（避免无效自旋），这样客户端 1 就加锁成功了。
 *      释放锁：成功获取锁的客户端在执行完业务流程之后，会将对应的子节点删除。成功获取锁的客户端在出现故障之后，
 *      对应的子节点由于是临时顺序节点，
 *      也会被自动删除，避免了锁无法被释放。我们前面说的事件监听器其实监听的就是这个子节点删除事件，子节点删除就意味着锁被释放。
 * @author yangkuo
 * @date 2025/5/9
 * @description 使用 Curator 实现分布式多锁的测试类
 */

public class ZKCuratorTest {

    private static final String PARENT_PATH = "/locks";
    private static final String EPHEMERAL_SEQUENTIAL_PREFIX = "lock_";
    private static final String ZK_ADDRESS = "localhost:2181";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDRESS)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        client.start();

        // 创建持久父节点 /locks（若不存在）
        if (client.checkExists().forPath(PARENT_PATH) == null) {
            client.create()
                  .creatingParentsIfNeeded()
                  .withMode(CreateMode.PERSISTENT)
                  .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                  .forPath(PARENT_PATH);
        }

        // 客户端创建自己的临时顺序节点
//        String myNode0 = client.create()
//            .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
//            .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
//            .forPath(PARENT_PATH + "/" + EPHEMERAL_SEQUENTIAL_PREFIX);
//        System.out.println("我创建的节点为0：" + myNode0);

        String myNode = client.create()
                              .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                              .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                              .forPath(PARENT_PATH + "/" + EPHEMERAL_SEQUENTIAL_PREFIX);

        System.out.println("我创建的节点为1：" + myNode);

        // 获取锁
        boolean isAcquired = acquireLockWithRetry(client, myNode);
        if (!isAcquired) {
            System.out.println("获取锁失败");
            return;
        }

        try {
            // 模拟业务操作
            System.out.println("正在处理业务...");
            Thread.sleep(3000);
        } finally {
            System.out.println("释放锁（节点将自动删除）");
        }

        client.close();
    }

    private static boolean acquireLockWithRetry(CuratorFramework client, String myNode) throws Exception {
    while (true) {
        // 获取所有子节点并排序
        List<String> nodes = client.getChildren().forPath(PARENT_PATH);
        nodes.sort(String::compareTo);

        String shortMyNode = myNode.substring(myNode.lastIndexOf("/") + 1);
        int myIndex = nodes.indexOf(shortMyNode);

        if (myIndex == 0) {
            System.out.println("获取锁成功！");
            return true;
        }

        String prevNode = nodes.get(myIndex - 1);
        String watchPath = PARENT_PATH + "/" + prevNode;
        CountDownLatch latch = new CountDownLatch(1);

        // 注册 Watcher
        client.getData().usingWatcher((CuratorWatcher) (event) -> {
            if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
                latch.countDown();
            }
        }).forPath(watchPath);

        System.out.println("等待前一个节点释放锁: " + watchPath);
        boolean acquired = latch.await(100, TimeUnit.SECONDS);

        if (acquired) {
            // Double check
            nodes = client.getChildren().forPath(PARENT_PATH);
            nodes.sort(String::compareTo);
            myIndex = nodes.indexOf(shortMyNode);
            if (myIndex == 0) {
                System.out.println("获取锁成功（通过监听）！");
                return true;
            }
            // 否则继续循环重试
        } else {
            System.out.println("获取锁超时");
            return false;
        }
    }
}


    // 锁路径
    private static final String lockPath1 = "/lock1";
    private static final String lockPath2 = "/lock2";

    public static void main1(String[] args) throws Exception {
        CuratorFramework client = ZKUtils.getClient();
        client.start();

        // 分布式可重入排它锁
        InterProcessLock lock1 = new InterProcessMutex(client, lockPath1);
        // 分布式不可重入排它锁
        InterProcessLock lock2 = new InterProcessSemaphoreMutex(client, lockPath2);

        // 将多个锁作为一个整体
        InterProcessMultiLock lock = new InterProcessMultiLock(Arrays.asList(lock1, lock2));

        if (!lock.acquire(10, TimeUnit.SECONDS)) {
            throw new IllegalStateException("不能获取多锁");
        }
        System.out.println("已获取多锁");
        System.out.println("是否有第一个锁: " + lock1.isAcquiredInThisProcess());
        System.out.println("是否有第二个锁: " + lock2.isAcquiredInThisProcess());

        try {
            // 模拟资源操作
            System.out.println("正在操作共享资源...");
        } finally {
            System.out.println("释放多个锁");
            lock.release();
        }

        System.out.println("是否有第一个锁: " + lock1.isAcquiredInThisProcess());
        System.out.println("是否有第二个锁: " + lock2.isAcquiredInThisProcess());

        client.close();
    }
}
