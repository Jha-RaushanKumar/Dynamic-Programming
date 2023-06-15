/*
188. Best Time to Buy and Sell Stock IV
https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/

You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
Find the maximum profit you can achieve. You may complete at most k transactions: i.e. you may buy at most k times and sell at most k times.
Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
Input: k = 2, prices = [2,4,1]
Output: 2
Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.

Example 2:
Input: k = 2, prices = [3,2,6,5,0,3]
Output: 7
Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
 
Constraints:
1 <= k <= 100
1 <= prices.length <= 1000
0 <= prices[i] <= 1000
*/

class Solution {
    public int maxProfit(int[] prices, int fee) {
        
        int n = prices.length;
        /*
        int[][] dp = new int[n][2];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], -1);
        }
        return maxProfitByMemoization(0, prices, 1, dp, fee);
        */
        /*
        int[][] dp = new int[n + 1][2];
        for(int i = 0; i <= n; i++){
            Arrays.fill(dp[i], 0);
        }
        return maxProfitByTabulation(n, prices, dp, fee);
        */
        return maxProfitByTabulationOptimized(n, prices, fee);
    }

    /*  
        Method 01: Recursion
        TC: O(2^N) SC: O(N) [recursionStackSpace]
    */
    public int maxProfitByRecursion(int i, int[] prices, int buyAllowed, int fee) {
        
        if(i == prices.length){
            return 0;
        }
        if(buyAllowed == 1){
            int buy = -prices[i] + maxProfitByRecursion(i + 1, prices, 0, fee);
            int notBuy = maxProfitByRecursion(i + 1, prices, 1, fee);
            return Math.max(buy, notBuy);
        }
        else{
            int sell = prices[i] - fee + maxProfitByRecursion(i + 1, prices, 1, fee);
            int notSell = maxProfitByRecursion(i + 1, prices, 0, fee);
            return Math.max(sell, notSell);
        }
    }

    /*  
        Method 02: Memoization
        TC: O(N*2) SC: O(N*2) + O(N) [DP array + recursionStackSpace]
    */
    public int maxProfitByMemoization(int i, int[] prices, int buyAllowed, int[][] dp, int fee) {
        
        if(i == prices.length){
            return 0;
        }
        if(dp[i][buyAllowed] != -1){
            return dp[i][buyAllowed];
        }
        if(buyAllowed == 1){
            int buy = -prices[i] + maxProfitByMemoization(i + 1, prices, 0, dp, fee);
            int notBuy = maxProfitByMemoization(i + 1, prices, 1, dp, fee);
            dp[i][buyAllowed] = Math.max(buy, notBuy);
        }
        else{
            int sell = prices[i] - fee + maxProfitByMemoization(i + 1, prices, 1, dp, fee);
            int notSell = maxProfitByMemoization(i + 1, prices, 0, dp, fee);
            dp[i][buyAllowed] = Math.max(sell, notSell);
        }
        return dp[i][buyAllowed];
    }

    /*  
        Method 03: Tabulation
        TC: O(N*2) SC: O(N*2) [DP array]
    */
    public int maxProfitByTabulation(int n, int[] prices, int[][] dp, int fee) {
        
        dp[n][0] = dp[n][1] = 0;
        for(int i = n - 1; i >= 0; i--){
            for(int j = 1; j >= 0; j--){
                if(j == 1){
                    int buy = -prices[i] + dp[i + 1][0];
                    int notBuy = dp[i + 1][1];
                    dp[i][j] = Math.max(buy, notBuy);
                }
                else{
                    int sell = prices[i] - fee + dp[i + 1][1];
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
    public int maxProfitByTabulationOptimized(int n, int[] prices, int fee) {
        
        int[] front = new int[2];
        front[0] = front[1] = 0;
        for(int i = n - 1; i >= 0; i--){
            int[] curr = new int[2];
            for(int j = 1; j >= 0; j--){
                if(j == 1){
                    int buy = -prices[i] + front[0];
                    int notBuy = front[1];
                    curr[j] = Math.max(buy, notBuy);
                }
                else{
                    int sell = prices[i] - fee + front[1];
                    int notSell = front[0];
                    curr[j] = Math.max(sell, notSell);
                }
            }
            front = curr;
        }
        return front[1];
    }
}