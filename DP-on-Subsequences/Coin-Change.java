/*
322. Coin Change
https://leetcode.com/problems/coin-change/

You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
You may assume that you have an infinite number of each kind of coin.

Example 1:
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1

Example 2:
Input: coins = [2], amount = 3
Output: -1

Example 3:
Input: coins = [1], amount = 0
Output: 0
 
Constraints:
1 <= coins.length <= 12
1 <= coins[i] <= 231 - 1
0 <= amount <= 104
*/

class Solution {
    public int coinChange(int[] coins, int amount) {
        
        int n = coins.length;
        //int res = coinChangeByRecursion(n - 1, coins, amount);
        /*
        int[][] dp = new int[n][amount + 1];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], -1);
        }
        int res = coinChangeByMemoization(n - 1, coins, amount, dp);
        */
        /*
        int[][] dp = new int[n][amount + 1];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], 0);
        }
        int res = coinChangeByTabulation(n, coins, amount, dp);
        */
        int res = coinChangeByTabulationOptimized(n, coins, amount);
        if(res == (int)1e9){
            return -1;
        }
        return res;
    }

    /*  
        Method 01:
        TC: O(2^(N*k)) SC: O(N+k)
    */
    public int coinChangeByRecursion(int i, int[] coins, int amount) {
        
        if(amount == 0){
            return 0;
        }
        if(i == 0){
            if(amount % coins[i] == 0){
                return amount / coins[i];
            }
            return (int)1e9;
        }
        int pick = (int)1e9;
        if(amount >= coins[i]){
            pick = 1 + coinChangeByRecursion(i, coins, amount - coins[i]);
        }
        int notPick = coinChangeByRecursion(i - 1, coins, amount);
        return Math.min(pick, notPick);
    }

    /*  
        Method 02:
        TC: O(N*k) SC: O(N)+O(M*k)[path length and stack space]
    */
    public int coinChangeByMemoization(int i, int[] coins, int amount, int[][] dp) {
        
        if(amount == 0){
            return 0;
        }
        if(i == 0){
            if(amount % coins[i] == 0){
                return amount / coins[i];
            }
            return (int)1e9;
        }
        if(dp[i][amount] != -1){
            return dp[i][amount];
        }
        int pick = (int)1e9;
        if(amount >= coins[i]){
            pick = 1 + coinChangeByMemoization(i, coins, amount - coins[i], dp);
        }
        int notPick = coinChangeByMemoization(i - 1, coins, amount, dp);
        return dp[i][amount] = Math.min(pick, notPick);
    }

    /*  
        Method 03:
        TC: O(N*k) SC: O(N*k)[no rec stack space, only dp array]
    */
    public int coinChangeByTabulation(int n, int[] coins, int amount, int[][] dp) {
        
        for(int i = 0; i < n; i++){
            dp[i][0] = 0;
        }
        for(int j = 0; j <= amount; j++){
            if(j % coins[0] == 0){
                dp[0][j] = j / coins[0];
            }
            else{
                dp[0][j] = (int)1e9;
            }
        }
        for(int i = 1; i < n; i++){
            for(int j = 1; j <= amount; j++){
                int pick = (int)1e9;
                if(j >= coins[i]){
                    pick = 1 + dp[i][j - coins[i]];
                }
                int notPick = dp[i - 1][j];
                dp[i][j] = Math.min(pick, notPick);
            }
        }
        return dp[n - 1][amount];
    }

    /*  
        Method 04:
        TC: O(N*k) SC: O(k)[no rec stack space, only dp array]
    */
    public int coinChangeByTabulationOptimized(int n, int[] coins, int amount) {
        
        int[] prev = new int[amount + 1];
        for(int j = 0; j <= amount; j++){
            if(j % coins[0] == 0){
                prev[j] = j / coins[0];
            }
            else{
                prev[j] = (int)1e9;
            }
        }
        for(int i = 1; i < n; i++){
            int curr[] = new int[amount + 1];
            curr[0] = 0;
            for(int j = 1; j <= amount; j++){
                int pick = (int)1e9;
                if(j >= coins[i]){
                    pick = 1 + curr[j - coins[i]];
                }
                int notPick = prev[j];
                curr[j] = Math.min(pick, notPick);
            }
            prev = curr;
        }
        return prev[amount];
    }
}