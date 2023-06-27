/*
518. Coin Change II
https://leetcode.com/problems/coin-change-ii/

You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
Return the number of combinations that make up that amount. If that amount of money cannot be made up by any combination of the coins, return 0.
You may assume that you have an infinite number of each kind of coin.
The answer is guaranteed to fit into a signed 32-bit integer.

Example 1:
Input: amount = 5, coins = [1,2,5]
Output: 4
Explanation: there are four ways to make up the amount:
5=5
5=2+2+1
5=2+1+1+1
5=1+1+1+1+1

Example 2:
Input: amount = 3, coins = [2]
Output: 0
Explanation: the amount of 3 cannot be made up just with coins of 2.

Example 3:
Input: amount = 10, coins = [10]
Output: 1
 
Constraints:
1 <= coins.length <= 300
1 <= coins[i] <= 5000
All the values of coins are unique.
0 <= amount <= 5000
*/

class Solution {
    public int change(int amount, int[] coins) {
        
        int n = coins.length;
        // return changeByRecursion(n - 1, coins, amount);
        /*
        int[][] dp = new int[n][amount +1];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], -1);
        }
        return changeByMemoization(n - 1, coins, amount, dp);
        */
        /*
        int[][] dp = new int[n][amount +1];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], 0);
        }
        return changeByTaulation(n, coins, amount, dp);
        */
        return changeByTaulationOptimized(n, coins, amount);
    }

    /*  
        Method 01:
        TC: O(2^(N*k)) SC: O(N+k)
    */
    public int changeByRecursion(int i, int[] coins, int amount){

        if(amount == 0){
            return 1;
        }
        if(i == 0){
            if(amount % coins[i] == 0){
                return 1;
            }
            return 0;
        }
        int pick = 0;
        if(amount >= coins[i]){
            pick = changeByRecursion(i, coins, amount - coins[i]);
        }
        int notPick = changeByRecursion(i - 1, coins, amount);
        return pick + notPick;
    }

    /*  
        Method 02:
        TC: O(N*k) SC: O(N)+O(M*k)[path length and stack space]
    */
    public int changeByMemoization(int i, int[] coins, int amount, int[][] dp){

        if(amount == 0){
            return 1;
        }
        if(i == 0){
            if(amount % coins[i] == 0){
                return 1;
            }
            return 0;
        }
        if(dp[i][amount] != -1){
            return dp[i][amount];
        }
        int pick = 0;
        if(amount >= coins[i]){
            pick = changeByMemoization(i, coins, amount - coins[i], dp);
        }
        int notPick = changeByMemoization(i - 1, coins, amount, dp);
        return dp[i][amount] = pick + notPick;
    }

    /*  
        Method 03:
        TC: O(N*k) SC: O(N*k)[no rec stack space, only dp array]
    */
    public int changeByTaulation(int n, int[] coins, int amount, int[][] dp){

        for(int i = 0; i < n; i++){
            dp[i][0] = 1;
        }
        for(int j = 0; j <= amount; j++){
            if(j % coins[0] == 0){
                dp[0][j] = 1;
            }
        }
        for(int i = 1; i < n; i++){
            for(int j = 1; j <= amount; j++){
                int pick = 0;
                if(j >= coins[i]){
                    pick = dp[i][j - coins[i]];
                }
                int notPick = dp[i - 1][j];
                dp[i][j] = pick + notPick;
            }
        }
        return dp[n - 1][amount];
    }
    /*  
        Method 04:
        TC: O(N*k) SC: O(k)[no rec stack space, only dp array]
    */
    public int changeByTaulationOptimized(int n, int[] coins, int amount){

        int[] prev = new int[amount + 1];
        for(int j = 0; j <= amount; j++){
            if(j % coins[0] == 0){
                prev[j] = 1;
            }
        }
        for(int i = 1; i < n; i++){
            int[] curr = new int[amount + 1];
            curr[0] = 1;
            for(int j = 1; j <= amount; j++){
                int pick = 0;
                if(j >= coins[i]){
                    pick = curr[j - coins[i]];
                }
                int notPick = prev[j];
                curr[j] = pick + notPick;
            }
            prev = curr;
        }
        return prev[amount];
    }
}