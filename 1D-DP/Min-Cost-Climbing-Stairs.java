/*
746. Min Cost Climbing Stairs
https://leetcode.com/problems/min-cost-climbing-stairs/

You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost, you can either climb one or two steps.
You can either start from the step with index 0, or the step with index 1.
Return the minimum cost to reach the top of the floor.

Example 1:
Input: cost = [10,15,20]
Output: 15
Explanation: You will start at index 1.
- Pay 15 and climb two steps to reach the top.
The total cost is 15.

Example 2:
Input: cost = [1,100,1,1,1,100,1,1,100,1]
Output: 6
Explanation: You will start at index 0.
- Pay 1 and climb two steps to reach index 2.
- Pay 1 and climb two steps to reach index 4.
- Pay 1 and climb two steps to reach index 6.
- Pay 1 and climb one step to reach index 7.
- Pay 1 and climb two steps to reach index 9.
- Pay 1 and climb one step to reach the top.
The total cost is 6.
 
Constraints:
2 <= cost.length <= 1000
0 <= cost[i] <= 999
*/

class Solution {
    public int minCostClimbingStairs(int[] cost) {
        
        int n = cost.length;
        //return Math.min(minCostClimbingStairsByRecursion(n - 1, cost), minCostClimbingStairsByRecursion(n - 2, cost));
        /*
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return Math.min(minCostClimbingStairsByMemoization(n - 1, cost, dp), minCostClimbingStairsByMemoization(n - 2, cost, dp));
        */
        /*
        int[] dp1 = new int[n + 1];
        Arrays.fill(dp1, 0);
        int[] dp2 = new int[n];
        Arrays.fill(dp2, 0);
        return Math.min(minCostClimbingStairsByTabulation(n, cost, dp1), minCostClimbingStairsByTabulation(n - 1, cost, dp2));
        */
        return Math.min(minCostClimbingStairsByTabulationOptimized(n, cost), minCostClimbingStairsByTabulationOptimized(n - 1, cost));
    }

    /*  
        Method 01:
        TC: O(2^N) SC: O(N)
    */
    public int minCostClimbingStairsByRecursion(int i, int[] cost) {
        
        if(i < 0){
            return 0;
        }
        if(i == 0){
            return cost[i];
        }
        int onestep =  minCostClimbingStairsByRecursion(i - 1, cost);
        int twostep =  minCostClimbingStairsByRecursion(i - 2, cost);
        return cost[i] + Math.min(onestep, twostep);
    }

    /* 
        Method 02:
        TC: O(N) SC: O(2N) ~ O(N)[Rec stack space + dp array]
    */
    public int minCostClimbingStairsByMemoization(int i, int[] cost, int[] dp) {
        
        if(i < 0){
            return 0;
        }
        if(i == 0){
            return cost[i];
        }
        if(dp[i] != -1){
            return dp[i];
        }
        int onestep =  minCostClimbingStairsByMemoization(i - 1, cost, dp);
        int twostep =  minCostClimbingStairsByMemoization(i - 2, cost, dp);
        return dp[i] = cost[i] + Math.min(onestep, twostep);
    }

    /*
        Method 03:
        TC: O(N) SC: O(N) [Only dp array, no stack space]
    */
    public int minCostClimbingStairsByTabulation(int n, int[] cost, int[] dp) {
        
        dp[0] = 0;
        dp[1] = cost[0];
        for(int i = 2; i <= n; i++){
            int onestep =  dp[i - 1];
            int twostep =  dp[i - 2];
            dp[i] = cost[i - 1] + Math.min(onestep, twostep);
        }
        return dp[n];
    }

    /*
        Method 04:
        TC: O(N) SC: O(1) [no dp array, no stack space]
    */
    public int minCostClimbingStairsByTabulationOptimized(int n, int[] cost) {
        
        int prev2 = 0;
        int prev = cost[0];
        for(int i = 2; i <= n; i++){
            int onestep =  prev;
            int twostep =  prev2;
            int curr = cost[i - 1] + Math.min(onestep, twostep);
            prev2 = prev;
            prev = curr;
        }
        return prev;
    }
}