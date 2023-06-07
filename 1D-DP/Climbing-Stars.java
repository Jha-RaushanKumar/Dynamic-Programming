/*
70. Climbing Stairs
https://leetcode.com/problems/climbing-stairs/

You are climbing a staircase. It takes n steps to reach the top.
Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Example 1:
Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps

Example 2:
Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
 
Constraints:
1 <= n <= 45
*/

class Solution {
    public int climbStairs(int n) {
        
        //return climbStairsByRecursion(n);
        //int[] dp = new int[n + 1];
        //Arrays.fill(dp, -1);
        //return climbStairsByMemoization(n, dp);
        //return climbStairsByTabulation(n, dp);
        return climbStairsByTabulationOptimized(n);
    }

    /*  
        Method 01:
        TC: O(2^N) SC: O(N)
    */
    public int climbStairsByRecursion(int n){
        
        if(n == 0 || n == 1){
            return 1;
        }

        int left = climbStairsByRecursion(n - 1);
        int right = climbStairsByRecursion(n - 2);
        return (left + right);
    }

    /* 
        Method 02:
        TC: O(N) SC: O(2N) ~ O(N)[Rec stack space + dp array]
    */
    public int climbStairsByMemoization(int i, int[] dp){
        
        if(i == 0 || i == 1){
            return 1;
        }
        if(dp[i] != -1){
            return dp[i];
        }
        int left = climbStairsByMemoization(i - 1, dp);
        int right = climbStairsByMemoization(i - 2, dp);
        return dp[i] = (left + right);
    }

    /*
        Method 03:
        TC: O(N) SC: O(N) [Only dp array, no stack space]
    */
    public int climbStairsByTabulation(int n, int[] dp){
        
        dp[0] = 1;
        dp[1] = 1;
        
        for(int i = 2; i <= n; i++){
            int left = dp[i - 1];
            int right = dp[i - 2];
            dp[i] = (left + right);
        }
        return dp[n];       
    }

    /*
        Method 04:
        TC: O(N) SC: O(1) [no dp array, no stack space]
    */
    public int climbStairsByTabulationOptimized(int n){
        
        int prev2 = 1;
        int prev = 1;
        
        for(int i = 2; i <= n; i++){
            int curr = prev + prev2;
            prev2 = prev;
            prev = curr;
        }
        return prev;       
    }
}