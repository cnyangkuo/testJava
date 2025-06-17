package com.practice.algorithm;

import java.util.*;

/**
 * 花色枚举定义
 */
enum Suit {
    // 黑桃	Spades	/speɪdz/
    // 红桃	Hearts	/hɑːrts/ 或 /hɝːts/
    // 梅花	Clubs	/klʌbz/
    // 方片	Diamonds	/ˈdaɪəməndz/
    SPADES, HEARTS, CLUBS, DIAMONDS;
}

/**
 * 牌面值枚举定义
 */
enum Rank {
    THREE(0), FOUR(1), FIVE(2), SIX(3), SEVEN(4), EIGHT(5), NINE(6), TEN(7), JACK(8), QUEEN(9), KING(10), ACE(11), TWO(12);
    private final int priority;
    Rank(int priority) {
        this.priority = priority;
    }
    public int getPriority() {
        return priority;
    }
}

/**
 * 扑克牌类，支持动态排序规则
 */
class Card {
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return String.format("[%s-%s]", rank, suit);
    }
}

/**
 * 动态排序器，支持传入排序规则
 */
public class PlayCardSort {
    /**
     * 动态排序方法
     * @param cards 需要排序的牌
     * @param rankOrder 牌面值排序规则（+1升序/-1降序）
     * @param suitOrder 花色优先级映射
     * @return 排序后的牌
     */
    public static List<Card> sortCards(List<Card> cards, int rankOrder, Map<Suit, Integer> suitOrder) {
        // 创建副本以避免修改原始列表
        List<Card> sortedList = new ArrayList<>(cards);
        
        // 获取最大花色优先级
        int maxSuitPriority = Collections.max(suitOrder.values());
        
        // 实现复合排序：
        // 1. 先按花色优先级
        // 2. 再按牌面值优先级
        sortedList.sort((c1, c2) -> {
            // 比较花色
            int suitCompare = Integer.compare(suitOrder.getOrDefault(c1.getSuit(), maxSuitPriority + 1),
                                           suitOrder.getOrDefault(c2.getSuit(), maxSuitPriority + 1)) * rankOrder;
            if (suitCompare != 0) return suitCompare;

            // 比较牌面值
            return Integer.compare(c1.getRank().getPriority(), c2.getRank().getPriority()) * rankOrder;
        });
        
        return sortedList;
    }

    /**
     * 性能优化版排序方法
     * @param cards 需要排序的牌
     * @param rankOrder 牌面值排序规则（+1升序/-1降序）
     * @param suitOrder 花色优先级映射
     * @param useCompositeKey 是否使用复合排序键
     * @return 排序后的牌
     */
    public static List<Card> sortCards(List<Card> cards, int rankOrder, Map<Suit, Integer> suitOrder, boolean useCompositeKey) {
        if (!useCompositeKey) {
            return sortCards(cards, rankOrder, suitOrder);
        }

        // 使用复合排序键优化
        int suitMultiplier = Rank.values().length; // 每个花色间隔

        List<Card> sortedList = new ArrayList<>(cards);
        sortedList.sort(Comparator.comparingInt(c -> {
            int rankKey = c.getRank().getPriority() * rankOrder;
            int suitKey = suitOrder.getOrDefault(c.getSuit(), Integer.MAX_VALUE);
            return rankKey * suitMultiplier + suitKey;
        }));

        return sortedList;
    }

    public static void main(String[] args) {
        // 测试牌组
        List<Card> cards = new ArrayList<>();
        cards.add(new Card(Rank.FIVE, Suit.HEARTS));
        cards.add(new Card(Rank.TWO, Suit.SPADES));
        cards.add(new Card(Rank.THREE, Suit.HEARTS));
        cards.add(new Card(Rank.FIVE, Suit.CLUBS));
        cards.add(new Card(Rank.THREE, Suit.DIAMONDS));
        
        // 自定义花色优先级：黑桃 梅花 方片 红桃
        Map<Suit, Integer> suitPriority = new HashMap<>();
        suitPriority.put(Suit.SPADES, 0);
        suitPriority.put(Suit.CLUBS, 1);
        suitPriority.put(Suit.DIAMONDS, 2);
        suitPriority.put(Suit.HEARTS, 3);
        
        // 测试不同排序规则
        System.out.println("按牌面升序+自定义花色优先级:");
        List<Card> result1 = sortCards(cards, 1, suitPriority);
        for (Card card : result1) {
            System.out.println(card);
        }
        
//        System.out.println("\n按牌面降序+自定义花色优先级（性能优化版）:");
//        List<Card> result2 = sortCards(cards, -1, suitPriority, true);
//        for (Card card : result2) {
//            System.out.println(card);
//        }
    }
}