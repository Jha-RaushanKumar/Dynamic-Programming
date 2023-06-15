/*
309. Best Time to Buy and Sell Stock with Cooldown
https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/

You are given an array prices where prices[i] is the price of a given stock on the ith day.
Find the maximum profit you can achieve. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times) with the following restrictions:
After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).
Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
Input: prices = [1,2,3,0,2]
Output: 3
Explanation: transactions = [buy, sell, cooldown, buy, sell]

Example 2:
Input: prices = [1]
Output: 0
 
Constraints:
1 <= prices.length <= 5000
0 <= prices[i] <= 1000
*/

class Solution {
    public int maxProfit(int[] prices) {
        
        int n = prices.length;
        /*
        int[][] dp = new int[n][2];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], -1);
        }
        return maxProfitByMemoization(0, prices, 1, dp);
        */
        /*
        int[][] dp = new int[n + 2][2];
        for(int i = 0; i <= n + 1; i++){
            Arrays.fill(dp[i], 0);
        }
        return maxProfitByTabulation(n, prices, dp);
        */
        return maxProfitByTabulationOptimized(n, prices);
    }

    /*  
        Method 01: Recursion
        TC: O(2^N) SC: O(N) [recursionStackSpace]
    */
    public int maxProfitByRecursion(int i, int[] prices, int buyAllowed) {
        
        if(i >= prices.length){
            return 0;
        }
        if(buyAllowed == 1){
            int buy = -prices[i] + maxProfitByRecursion(i + 1, prices, 0);
            int notBuy = maxProfitByRecursion(i + 1, prices, 1);
            return Math.max(buy, notBuy);
        }
        else{
            int sell = prices[i] + maxProfitByRecursion(i + 2, prices, 1);
            int notSell = maxProfitByRecursion(i + 1, prices, 0);
            return Math.max(sell, notSell);
        }
    }

    /*  
        Method 02: Memoization
        TC: O(N*2) SC: O(N*2) + O(N) [DP array + recursionStackSpace]
    */
    public int maxProfitByMemoization(int i, int[] prices, int buyAllowed, int[][] dp) {
        
        if(i >= prices.length){
            return 0;
        }
        if(dp[i][buyAllowed] != -1){
            return dp[i][buyAllowed];
        }
        if(buyAllowed == 1){
            int buy = -prices[i] + maxProfitByMemoization(i + 1, prices, 0, dp);
            int notBuy = maxProfitByMemoization(i + 1, prices, 1, dp);
            dp[i][buyAllowed] = Math.max(buy, notBuy);
        }
        else{
            int sell = prices[i] + maxProfitByMemoization(i + 2, prices, 1, dp);
            int notSell = maxProfitByMemoization(i + 1, prices, 0, dp);
            dp[i][buyAllowed] = Math.max(sell, notSell);
        }
        return dp[i][buyAllowed];
    }

    /*  
        Method 03: Tabulation
        TC: O(N*2) SC: O(N*2) [DP array]
    */
    public int maxProfitByTabulation(int n, int[] prices, int[][] dp) {
        
        dp[n][0] = dp[n][1] = 0;
        for(int i = n - 1; i >= 0; i--){
            for(int j = 1; j >= 0; j--){
                if(j == 1){
                    int buy = -prices[i] + dp[i + 1][0];
                    int notBuy = dp[i + 1][1];
                    dp[i][j] = Math.max(buy, notBuy);
                }
                else{
                    int sell = prices[i] + dp[i + 2][1];
                    int notSell = dp[i + 1][0];
                    dp[i][j] = Math.max(sell, notSell);
                }
            }
        }
        return dp[0][1];
    }

    /*  
        Method 04: Tabulation Optimized
        TC: O(N*2) SC: O(N) [Just curr and front]
    */
    public int maxProfitByTabulationOptimized(int n, int[] prices) {
        
        int[] front1 = new int[2];
        int[] front2 = new int[2];
        front1[0] = front1[1] = 0;
        front2[0] = front2[1] = 0;
        for(int i = n - 1; i >= 0; i--){
            int[] curr = new int[2];
            for(int j = 1; j >= 0; j--){
                if(j == 1){
                    int buy = -prices[i] + front1[0];
                    int notBuy = front1[1];
                    curr[j] = Math.max(buy, notBuy);
                }
                else{
                    int sell = prices[i] + front2[1];
                    int notSell = front1[0];
                    curr[j] = Math.max(sell, notSell);
                }
            }
            front2 = front1;
            front1 = curr;
        }
        return front1[1];
    }
}