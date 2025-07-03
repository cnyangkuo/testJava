package com.interview.sc;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程安全的银行账户类
 * 支持多线程环境下的并发转账操作
 */
public class ConcurrentBankAccount {
    private final String accountNumber;
    private BigDecimal balance;
    private final ReentrantLock lock = new ReentrantLock();

    public ConcurrentBankAccount(String accountNumber, BigDecimal initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    /**
     * 从当前账户转账到目标账户
     * @param target 目标账户
     * @param amount 转账金额
     * @return true 如果转账成功
     * @throws InterruptedException 
     */
    public boolean transferTo(ConcurrentBankAccount target, BigDecimal amount) throws InterruptedException {
        // 尝试获取两个账户的锁
        if (lock.tryLock() && target.lock.tryLock()) {
            try {
                if (balance.compareTo(amount) >= 0) {
                    balance = balance.subtract(amount);
                    target.balance = target.balance.add(amount);
                    return true;
                }
                return false;
            } finally {
                lock.unlock();
                target.lock.unlock();
            }
        } else {
            // 获取锁失败时等待随机时间后重试
            Thread.sleep((long) (Math.random() * 100));
            return transferTo(target, amount);
        }
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public static void main(String[] args) {
        ConcurrentBankAccount accountA = new ConcurrentBankAccount("ACC001", new BigDecimal("10000"));
        ConcurrentBankAccount accountB = new ConcurrentBankAccount("ACC002", new BigDecimal("5000"));
        
        // 创建多个转账线程
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    try {
                        if (accountA.transferTo(accountB, new BigDecimal("100"))) {
                            System.out.println(Thread.currentThread().getName() + ": 转账成功");
                        } else {
                            System.out.println(Thread.currentThread().getName() + ": 余额不足");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "TransferThread-" + i).start();
        }
        
        // 等待所有线程完成
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("最终账户A余额: " + accountA.getBalance());
        System.out.println("最终账户B余额: " + accountB.getBalance());
    }
}