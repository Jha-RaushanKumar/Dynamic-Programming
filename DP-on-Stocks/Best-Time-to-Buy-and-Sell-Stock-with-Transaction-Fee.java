/*
714. Best Time to Buy and Sell Stock with Transaction Fee
https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/

You are given an array prices where prices[i] is the price of a given stock on the ith day, and an integer fee representing a transaction fee.
Find the maximum profit you can achieve. You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction.
Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
Input: prices = [1,3,2,8,4,9], fee = 2
Output: 8
Explanation: The maximum profit can be achieved by:
- Buying at prices[0] = 1
- Selling at prices[3] = 8
- Buying at prices[4] = 4
- Selling at prices[5] = 9
The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.

Example 2:
Input: prices = [1,3,7,5,10,3], fee = 3
Output: 6
 
Constraints:
1 <= prices.length <= 5 * 104
1 <= prices[i] < 5 * 104
0 <= fee < 5 * 104
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