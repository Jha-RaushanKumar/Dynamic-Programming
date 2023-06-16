/*
62. Unique Paths
https://leetcode.com/problems/unique-paths/

There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner.
The test cases are generated so that the answer will be less than or equal to 2 * 109.

Example 1:
Input: m = 3, n = 7
Output: 28

Example 2:
Input: m = 3, n = 2
Output: 3

Explanation: From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
1. Right -> Down -> Down
2. Down -> Down -> Right
3. Down -> Right -> Down
 
Constraints:
1 <= m, n <= 100
*/

class Solution {
    public int uniquePaths(int m, int n) {
        
        //return uniquePathsByRecursion(m - 1, n - 1);
        /*
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            Arrays.fill(dp[i], -1);
        }
        return uniquePathsByMemoization(m - 1, n - 1, dp);
        */
        /*
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 0; i <= m; i++){
            Arrays.fill(dp[i], 0);
        }
        return uniquePathsByTabulation(m, n, dp);
        */
        return uniquePathsByTabulationOptimized(m, n);
    }

    /*  
        Method 01:
        TC: O(2^(N*M)) SC: O(M+N)
    */
    public int uniquePathsByRecursion(int i, int j) {
        if(i == 0 && j == 0){
            return 1;
        }
        if(i < 0 || j < 0){
            return 0;
        }
        int up = uniquePathsByRecursion(i - 1, j);
        int down = uniquePathsByRecursion(i, j - 1);
        return (up + down);
    }

    /*  
        Method 02:
        TC: O(N*M) SC: O(M+N)+O(M*N)[path length and stack space]
    */
    public int uniquePathsByMemoization(int i, int j, int[][] dp) {
        if(i == 0 && j == 0){
            return 1;
        }
        if(i < 0 || j < 0){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int up = uniquePathsByMemoization(i - 1, j, dp);
        int down = uniquePathsByMemoization(i, j - 1, dp);
        return dp[i][j] = (up + down);
    }

    /*  
        Method 03:
        TC: O(N*M) SC: O(M*N)[no rec stack space, only dp array]
    */
    public int uniquePathsByTabulation(int m, int n, int[][] dp) {
        dp[1][1] = 1;
        for(int i = 0; i < m; i++){
            dp[i][0] = 0;
        }
        for(int j = 0; j < n; j++){
            dp[0][j] = 0;
        }
        
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(i == 1 && j == 1){
                    continue;
                }
                else{
                    int up = dp[i - 1][j];
                    int down = dp[i][j - 1];
                    dp[i][j] = (up + down);
                }
            }
        }
        return dp[m][n];
    }

    /*  
        Method 04:
        TC: O(N*M) SC: O(N)[no path length, no dp array, just for new arr temp]
    */
    public int uniquePathsByTabulationOptimized(int m, int n) {
        
        int[] prev = new int[n + 1];
        int[] curr = new int[n + 1];
        for(int i = 0; i <= m; i++){
            curr[0] = 0;
        }
        for(int j = 0; j <= n; j++){
            prev[j] = 0;
        }
        
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(i == 1 && j == 1){
                    curr[j] = 1;
                }
                else{
                    int up = prev[j];
                    int down = curr[j - 1];
                    curr[j] = (up + down);
                }
            }
            prev = curr;
        }
        return prev[n];
    }
}