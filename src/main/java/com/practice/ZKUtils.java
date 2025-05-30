package com.practice;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZKUtils {

    private static final String ZK_ADDRESS = "localhost:2181"; // 修改为你的 Zookeeper 地址
    private static final int SESSION_TIMEOUT = 5000;
    private static final int CONNECTION_TIMEOUT = 5000;

    public static CuratorFramework getClient() {
        return CuratorFrameworkFactory.builder()
                .connectString(ZK_ADDRESS)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
    }
}
