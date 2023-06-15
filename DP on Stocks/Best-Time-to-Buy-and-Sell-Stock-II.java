/*
122. Best Time to Buy and Sell Stock II
https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/

You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.
Find and return the maximum profit you can achieve.

Example 1:
Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
Total profit is 4 + 3 = 7.

Example 2:
Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Total profit is 4.

Example 3:
Input: prices = [7,6,4,3,1]
Output: 0
Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.
 
Constraints:
1 <= prices.length <= 3 * 104
0 <= prices[i] <= 104
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
        int[][] dp = new int[n + 1][2];
        for(int i = 0; i <= n; i++){
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
        
        if(i == prices.length){
            return 0;
        }
        if(buyAllowed == 1){
            int buy = -prices[i] + maxProfitByRecursion(i + 1, prices, 0);
            int notBuy = maxProfitByRecursion(i + 1, prices, 1);
            return Math.max(buy, notBuy);
        }
        else{
            int sell = prices[i] + maxProfitByRecursion(i + 1, prices, 1);
            int notSell = maxProfitByRecursion(i + 1, prices, 0);
            return Math.max(sell, notSell);
        }
    }

    /*  
        Method 02: Memoization
        TC: O(N*2) SC: O(N*2) + O(N) [DP array + recursionStackSpace]
    */
    public int maxProfitByMemoization(int i, int[] prices, int buyAllowed, int[][] dp) {
        
        if(i == prices.length){
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
            int sell = prices[i] + maxProfitByMemoization(i + 1, prices, 1, dp);
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
                    int sell = prices[i] + dp[i + 1][1];
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
                    int sell = prices[i] + front[1];
                    int notSell = front[0];
                    curr[j] = Math.max(sell, notSell);
                }
            }
            front = curr;
        }
        return front[1];
    }
}