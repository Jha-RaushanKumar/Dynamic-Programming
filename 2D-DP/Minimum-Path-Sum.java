/*
64. Minimum Path Sum
https://leetcode.com/problems/minimum-path-sum/

Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.
Note: You can only move either down or right at any point in time.

Example 1:
Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
Output: 7
Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.

Example 2:
Input: grid = [[1,2,3],[4,5,6]]
Output: 12

Constraints:
m == grid.length
n == grid[i].length
1 <= m, n <= 200
0 <= grid[i][j] <= 200
*/

class Solution {
    public int minPathSum(int[][] grid) {
        
        int m = grid.length;
        int n = grid[0].length;
        //return minPathSumByRecursion(m - 1, n - 1, grid);
        /*
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            Arrays.fill(dp[i], -1);
        }
        return minPathSumByMemoization(m - 1, n - 1, grid, dp);
        */
        /*
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 0; i <= m; i++){
            Arrays.fill(dp[i], 0);
        }
        return minPathSumByTabulation(m , n, grid, dp);
        */
        return minPathSumByTabulationOptimized(m , n, grid);
    }

    /*  
        Method 01:
        TC: O(2^(N*M)) SC: O(M+N)
    */
    public int minPathSumByRecursion(int i, int j, int[][] grid) {
        
        if(i == 0 && j == 0){
            return grid[i][j];
        }
        if(i < 0 || j < 0){
            return (int)1e9;
        }
        int left = grid[i][j] + minPathSumByRecursion(i, j - 1, grid);
        int up = grid[i][j] + minPathSumByRecursion(i - 1, j, grid);
        return Math.min(left, up);
    }

    /*  
        Method 02:
        TC: O(N*M) SC: O(M+N)+O(M*N)[path length and stack space]
    */
    public int minPathSumByMemoization(int i, int j, int[][] grid, int[][] dp) {
        
        if(i == 0 && j == 0){
            return grid[i][j];
        }
        if(i < 0 || j < 0){
            return (int)1e9;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int left = grid[i][j] + minPathSumByMemoization(i, j - 1, grid, dp);
        int up = grid[i][j] + minPathSumByMemoization(i - 1, j, grid, dp);
        return dp[i][j] = Math.min(left, up);
    }

    /*  
        Method 03:
        TC: O(N*M) SC: O(M*N)[no rec stack space, only dp array]
    */
    public int minPathSumByTabulation(int m, int n, int[][] grid, int[][] dp) {
        
        for(int i = 0; i <= m; i++){
            dp[i][0] = (int)1e9;
        }
        for(int j = 0; j <= n; j++){
            dp[0][j] = (int)1e9;
        }
        dp[1][1] = grid[0][0];
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(i == 1 && j == 1){
                    continue;
                }
                else{
                    int left = grid[i - 1][j - 1] + dp[i][j - 1];
                    int up = grid[i - 1][j - 1] + dp[i - 1][j];
                    dp[i][j] = Math.min(left, up);
                }
            }
        }
        return dp[m][n];
    }

    /*  
        Method 04:
        TC: O(N*M) SC: O(N)[no path length, no dp array, just for new arr temp]
    */
    public int minPathSumByTabulationOptimized(int m, int n, int[][] grid) {
        
        int prev[] = new int[n + 1];
        int curr[] = new int[n + 1];
        for(int i = 0; i <= n; i++){
            curr[0] = (int)1e9;
        }
        for(int j = 0; j <= n; j++){
            prev[j] = (int)1e9;
        }
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(i == 1 && j == 1){
                    curr[j] = grid[i - 1][j - 1];
                }
                else{
                    int left = grid[i - 1][j - 1] + curr[j - 1];
                    int up = grid[i - 1][j - 1] + prev[j];
                    curr[j] = Math.min(left, up);
                }
            }
            prev = curr;
        }
        return prev[n];
    }
}