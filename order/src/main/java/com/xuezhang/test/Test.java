package com.xuezhang.test;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @description:
 * @author: 学长
 * @date: 2021/3/17 10:02
 */
public class Test {
    public static void main(String[] args) {
        int[] prices = {10,7};
        Solution solution = new Solution();

        System.out.println(solution.maxProfit(prices));
    }
}

class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length >= 2 && prices.length <= 10_0000){
            int first = prices[0];

            int[] new_arr = Arrays.copyOfRange(prices, 1, prices.length);

            int max = Arrays.stream(new_arr).max().getAsInt();

            int min = Arrays.stream(new_arr).min().getAsInt();

            return first > min ? max - min : max - first;
        }
        return 0;
    }
}