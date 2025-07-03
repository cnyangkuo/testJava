package com.interview.sc;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 线程安全的汇率服务类
 * 支持实时汇率更新和多线程环境下的高效查询
 */
public class ThreadSafeExchangeRate {
    private final ConcurrentHashMap<String, BigDecimal> rates = new ConcurrentHashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 更新指定货币对的汇率
     * @param currencyPair 货币对（如USD/CNY）
     * @param rate 汇率值
     */
    public void updateRate(String currencyPair, BigDecimal rate) {
        lock.writeLock().lock();
        try {
            rates.put(currencyPair, rate);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 获取指定货币对的汇率
     * @param currencyPair 货币对
     * @return 汇率值
     */
    public BigDecimal getRate(String currencyPair) {
        lock.readLock().lock();
        try {
            return rates.get(currencyPair);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 计算交叉汇率
     * @param baseRate 主汇率（如USD/CNY）
     * @param crossRate 交叉汇率（如EUR/CNY）
     * @return 交叉计算后的汇率（如EUR/USD）
     */
    public BigDecimal calculateCrossRate(String baseRate, String crossRate) {
        BigDecimal base = getRate(baseRate);
        BigDecimal cross = getRate(crossRate);
        if (base == null || cross == null) {
            throw new IllegalArgumentException("汇率数据不存在");
        }
        return cross.divide(base, 4, BigDecimal.ROUND_HALF_UP);
    }

    public static void main(String[] args) {
        ThreadSafeExchangeRate exchangeService = new ThreadSafeExchangeRate();
        
        // 初始化基础汇率
        exchangeService.updateRate("USD/CNY", new BigDecimal("7.15"));
        exchangeService.updateRate("EUR/CNY", new BigDecimal("7.85"));
        
        // 测试汇率查询
        System.out.println("USD/CNY: " + exchangeService.getRate("USD/CNY"));
        System.out.println("EUR/CNY: " + exchangeService.getRate("EUR/CNY"));
        
        // 测试交叉汇率计算
        System.out.println("EUR/USD: " + exchangeService.calculateCrossRate("USD/CNY", "EUR/CNY"));
    }
}