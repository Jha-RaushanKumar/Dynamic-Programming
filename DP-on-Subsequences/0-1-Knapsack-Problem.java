/*
0 - 1 Knapsack Problem
https://practice.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1

You are given weights and values of N items, put these items in a knapsack of capacity W to get the maximum total value in the knapsack. Note that we have only one quantity of each item.
In other words, given two integer arrays val[0..N-1] and wt[0..N-1] which represent values and weights associated with N items respectively. Also given an integer W which represents knapsack capacity, find out the maximum value subset of val[] such that sum of the weights of this subset is smaller than or equal to W. You cannot break an item, either pick the complete item or dont pick it (0-1 property).

Example 1:
Input:
N = 3
W = 4
values[] = {1,2,3}
weight[] = {4,5,1}
Output: 3

Example 2:
Input:
N = 3
W = 3
values[] = {1,2,3}
weight[] = {4,5,6}
Output: 0

Your Task:
Complete the function knapSack() which takes maximum capacity W, weight array wt[], value array val[], and the number of items n as a parameter and returns the maximum possible value you can get.
Expected Time Complexity: O(N*W).
Expected Auxiliary Space: O(N*W)

Constraints:
1 ≤ N ≤ 1000
1 ≤ W ≤ 1000
1 ≤ wt[i] ≤ 1000
1 ≤ v[i] ≤ 1000
*/

class Solution 
{ 
    //Function to return max value that can be put in knapsack of capacity W.
    static int knapSack(int W, int wt[], int val[], int n) 
    { 
         // your code here
        //return knapSackByRecursion(n - 1, W, wt, val);
        /*
        int[][] dp = new int[n][W + 1];
        for(int[] row : dp){
            Arrays.fill(row, -1);
        }
        return knapSackByMemoization(n - 1, W, wt, val, dp);
        */
        /*
        int[][] dp = new int[n][W + 1];
        for(int[] row : dp){
            Arrays.fill(row, 0);
        }
        return knapSackByTabulation(n, W, wt, val, dp);
        */
        return knapSackByTabulationOptimized(n, W, wt, val);
    }
    
    /*  
        Method 01:
        TC: O(2^(N*k)) SC: O(N+k)
    */
    static int knapSackByRecursion(int i, int W, int[] wt, int[] val){
        
        if(W <= 0){
            return 0;
        }
        if(i == 0){
            if(W >= wt[i]){
                return val[i];
            }
            else{
                return 0;
            }
        }
        int pick = Integer.MIN_VALUE;
        if(W >= wt[i]){
            pick = val[i] + knapSackByRecursion(i - 1, W - wt[i], wt, val);
        }
        int notPick = knapSackByRecursion(i - 1, W, wt, val);
        return Math.max(pick, notPick);
    }
    
    /*  
        Method 02:
        TC: O(N*W) SC: O(N)+O(N*W)[path length and stack space]
    */
    static int knapSackByMemoization(int i, int W, int[] wt, int[] val, int[][] dp){
        
        if(W <= 0){
            return 0;
        }
        if(i == 0){
            if(W >= wt[i]){
                return val[i];
            }
            else{
                return 0;
            }
        }
        if(dp[i][W] != -1){
            return dp[i][W];
        }
        int pick = Integer.MIN_VALUE;
        if(W >= wt[i]){
            pick = val[i] + knapSackByMemoization(i - 1, W - wt[i], wt, val, dp);
        }
        int notPick = knapSackByMemoization(i - 1, W, wt, val, dp);
        return dp[i][W] = Math.max(pick, notPick);
    }
    
    /*  
        Method 03:
        TC: O(N*W) SC: O(N*W)[no rec stack space, only dp array]
    */
    static int knapSackByTabulation(int n, int W, int[] wt, int[] val, int[][] dp){
        
        for(int j = 0; j <= W; j++){
            if(j >= wt[0]){
                dp[0][j] = val[0];
            }
        }
        for(int i = 1; i < n; i++){
            for(int j = 1; j <= W; j++){
                int pick = Integer.MIN_VALUE;
                if(j >= wt[i]){
                    pick = val[i] + dp[i - 1][j - wt[i]];
                }
                int notPick = dp[i - 1][j];
                dp[i][j] = Math.max(pick, notPick);
            }
        }
        return dp[n - 1][W];
    }
    
    /*  
        Method 04:
        TC: O(N*W) SC: O(W)[no rec stack space, only dp array]
    */
    static int knapSackByTabulationOptimized(int n, int W, int[] wt, int[] val){
        
        int[] prev = new int[W + 1];
        for(int j = 0; j <= W; j++){
            if(j >= wt[0]){
                prev[j] = val[0];
            }
        }
        for(int i = 1; i < n; i++){
            int[] curr = new int[W + 1];
            for(int j = 1; j <= W; j++){
                int pick = Integer.MIN_VALUE;
                if(j >= wt[i]){
                    pick = val[i] + prev[j - wt[i]];
                }
                int notPick = prev[j];
                curr[j] = Math.max(pick, notPick);
            }
            prev = curr;
        }
        return prev[W];
    }
}