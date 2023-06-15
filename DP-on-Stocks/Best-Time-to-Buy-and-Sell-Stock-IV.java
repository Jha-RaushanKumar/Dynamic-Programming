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
    public int maxProfit(int k, int[] prices) {
        
        int n = prices.length;
        //return maxProfitByRecursion(0, prices, 1, k);
        /*
        int[][][] dp = new int[n][2][k + 1];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < 2; j++){
                Arrays.fill(dp[i][j], -1);
            }
        }
        return maxProfitByMemoization(0, prices, 1, k, dp);
        */
        /*
        int[][][] dp = new int[n + 1][2][k + 1];
        for(int i = 0; i <= n; i++){
            for(int j = 0; j < 2; j++){
                Arrays.fill(dp[i][j], 0);
            }
        }
        return maxProfitByTabulation(n, prices, dp, k);
        */
        return maxProfitByTabulationOptimized(n, prices, k);
    }

    /*  
        Method 01: Recursion
        TC: O(2^N) SC: O(N) [recursionStackSpace]
    */
    public int maxProfitByRecursion(int i, int[] prices, int buyAllowed, int k) {
        
        if(i == prices.length || k == 0){
            return 0;
        }
        if(buyAllowed == 1){
            int buy = -prices[i] + maxProfitByRecursion(i + 1, prices, 0, k);
            int notBuy = maxProfitByRecursion(i + 1, prices, 1, k);
            return Math.max(buy, notBuy);
        }
        else{
            int sell = prices[i] + maxProfitByRecursion(i + 1, prices, 1, k - 1);
            int notSell = maxProfitByRecursion(i + 1, prices, 0, k);
            return Math.max(sell, notSell);
        }
    }

    /*  
        Method 02: Memoization
        TC: O(N*2*k) SC: O(N*2*k) + O(N) [DP array + recursionStackSpace]
    */
    public int maxProfitByMemoization(int i, int[] prices, int buyAllowed, int k, int[][][] dp) {
        
        if(i == prices.length || k == 0){
            return 0;
        }
        if(dp[i][buyAllowed][k] != -1){
            return dp[i][buyAllowed][k];
        }
        if(buyAllowed == 1){
            int buy = -prices[i] + maxProfitByMemoization(i + 1, prices, 0, k, dp);
            int notBuy = maxProfitByMemoization(i + 1, prices, 1, k, dp);
            return dp[i][buyAllowed][k] = Math.max(buy, notBuy);
        }
        else{
            int sell = prices[i] + maxProfitByMemoization(i + 1, prices, 1, k - 1, dp);
            int notSell = maxProfitByMemoization(i + 1, prices, 0, k, dp);
            return dp[i][buyAllowed][k] = Math.max(sell, notSell);
        }
    }

    /*  
        Method 03: Tabulation
        TC: O(N*2*k) SC: O(N*2*k) [DP array]
    */
    public int maxProfitByTabulation(int n, int[] prices, int[][][] dp, int trans) {
        
        for(int j = 0; j < 2; j++){
            for(int k = 0; k <= trans; k++){
                dp[n][j][k] = 0;
            }
        }
        for(int i = 0; i <= n; i++){
            for(int j = 0; j < 2; j++){
                dp[i][j][0] = 0;
            }
        }
        
        for(int i = n - 1; i >= 0; i--){
            for(int j = 0; j < 2; j++){
                for(int k = 1; k <= trans; k++){
                    if(j == 1){
                        int buy = -prices[i] + dp[i + 1][0][k];
                        int notBuy = dp[i + 1][1][k];
                        dp[i][j][k] = Math.max(buy, notBuy);
                    }
                    else{
                        int sell = prices[i] + dp[i + 1][1][k - 1];
                        int notSell = dp[i + 1][0][k];
                        dp[i][j][k] = Math.max(sell, notSell);
                    }
                }
            }
        }
        return dp[0][1][trans];
    }

    /*  
        Method 04: Tabulation Optimized
        TC: O(N*2*k) SC: O(2*k) ~ O(k) [Just curr and front]
    */
    public int maxProfitByTabulationOptimized(int n, int[] prices, int trans) {
        
        int[][] ahead= new int[2][trans + 1];
        for(int j = 0; j < 2; j++){
            for(int k = 0; k <= trans; k++){
                ahead[j][k] = 0;
            }
        }
        int[][] curr= new int[2][trans + 1];
        for(int i = 0; i <= n; i++){
            for(int j = 0; j < 2; j++){
                curr[j][0] = 0;
            }
        }
        
        for(int i = n - 1; i >= 0; i--){
            for(int j = 0; j < 2; j++){
                for(int k = 1; k <= trans; k++){
                    if(j == 1){
                        int buy = -prices[i] + ahead[0][k];
                        int notBuy = ahead[1][k];
                        curr[j][k] = Math.max(buy, notBuy);
                    }
                    else{
                        int sell = prices[i] + ahead[1][k - 1];
                        int notSell = ahead[0][k];
                        curr[j][k] = Math.max(sell, notSell);
                    }
                }
            }
            ahead = curr;
        }
        return ahead[1][trans];
    }
}