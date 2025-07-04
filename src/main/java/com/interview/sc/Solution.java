package com.interview.sc;

/**
 * @author yangkuo
 * @date 2025/7/4
 * @description
 */
public class Solution {
    public static void main(String[] args) {
        while(true) {
            System.out.println("hello world time:" + new java.util.Date().toString());
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
