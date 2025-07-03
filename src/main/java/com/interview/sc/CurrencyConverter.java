package com.interview.sc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 货币转换工具类
 * 支持多种货币之间的精确转换和四舍五入处理
 */
public class CurrencyConverter {
    /**
     * 转换货币金额
     * @param amount 原始金额
     * @param exchangeRate 汇率
     * @param scale 小数位数
     * @return 转换后的金额
     */
    public static BigDecimal convert(BigDecimal amount, BigDecimal exchangeRate, int scale) {
        if (amount == null || exchangeRate == null) {
            throw new IllegalArgumentException("金额或汇率不能为空");
        }
        return amount.multiply(exchangeRate).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 计算两个货币对的交叉汇率
     * @param baseCurrency 主货币（如USD）
     * @param targetCurrency 目标货币（如CNY）
     * @param crossCurrency 交叉货币（如EUR）
     * @param directRate 主货币对交叉货币的汇率（如USD/EUR）
     * @param crossRate 交叉货币对目标货币的汇率（如EUR/CNY）
     * @param scale 小数位数
     * @return 计算后的交叉汇率
     */
    public static BigDecimal calculateCrossRate(String baseCurrency, String targetCurrency, 
                                                String crossCurrency, BigDecimal directRate, 
                                                BigDecimal crossRate, int scale) {
        if (directRate == null || crossRate == null) {
            throw new IllegalArgumentException("汇率数据不能为空");
        }
        
        // 计算交叉汇率：baseCurrency/targetCurrency = directRate * crossRate
        // 参数已经明确表示了货币对关系，无需额外验证字符串内容
        return directRate.multiply(crossRate).setScale(scale, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        // 测试货币转换
        BigDecimal usdAmount = new BigDecimal("100.50");
        BigDecimal usdToCnyRate = new BigDecimal("7.15");
        BigDecimal convertedAmount = convert(usdAmount, usdToCnyRate, 2);
        System.out.println(usdAmount + " USD = " + convertedAmount + " CNY");
        
        // 测试交叉汇率计算
        BigDecimal usdToEurRate = new BigDecimal("0.85");
        BigDecimal eurToCnyRate = new BigDecimal("7.85");
        BigDecimal crossRate = calculateCrossRate("USD", "CNY", "EUR", usdToEurRate, eurToCnyRate, 4);
        System.out.println("USD/CNY交叉汇率: " + crossRate);

        // 实现复合利率计算
        BigDecimal principal = new BigDecimal("10000");
        BigDecimal rate = new BigDecimal("0.05"); // 5%
        int years = 10;

        // 复利公式：principal * (1 + rate)^years
        BigDecimal finalAmount = principal.multiply(
                BigDecimal.ONE.add(rate).pow(years, MathContext.DECIMAL128)
        ).setScale(4, RoundingMode.HALF_UP);
        System.out.println("复利计算结果: " + finalAmount);
    }
}