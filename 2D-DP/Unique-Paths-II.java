/*
63. Unique Paths II
https://leetcode.com/problems/unique-paths-ii/

You are given an m x n integer array grid. There is a robot initially located at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]). The robot can only move either down or right at any point in time.
An obstacle and space are marked as 1 or 0 respectively in grid. A path that the robot takes cannot include any square that is an obstacle.
Return the number of possible unique paths that the robot can take to reach the bottom-right corner.
The testcases are generated so that the answer will be less than or equal to 2 * 109.

Example 1:
Input: obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
Output: 2
Explanation: There is one obstacle in the middle of the 3x3 grid above. There are two ways to reach the bottom-right corner:
1. Right -> Right -> Down -> Down
2. Down -> Down -> Right -> Right

Example 2:
Input: obstacleGrid = [[0,1],[0,0]]
Output: 1
 
Constraints:
m == obstacleGrid.length
n == obstacleGrid[i].length
1 <= m, n <= 100
obstacleGrid[i][j] is 0 or 1.
*/

class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        //return uniquePathsByRecursion(m - 1, n - 1, obstacleGrid);
        /*
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            Arrays.fill(dp[i], -1);
        }
        return uniquePathsByMemoization(m - 1, n - 1, dp, obstacleGrid);
        */
        /*
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 0; i <= m; i++){
            Arrays.fill(dp[i], 0);
        }
        return uniquePathsByTabulation(m, n, dp, obstacleGrid);
        */
        return uniquePathsByTabulationOptimized(m, n, obstacleGrid);
    }

    /*  
        Method 01:
        TC: O(2^(N*M)) SC: O(M+N)
    */
    public int uniquePathsByRecursion(int i, int j, int[][] obstacleGrid) {
        
        if(i < 0 || j < 0){
            return 0;
        }
        if(obstacleGrid[i][j] == 1){
            return 0;
        }
        if(i == 0 && j == 0){
            return 1;
        }
        int up = uniquePathsByRecursion(i - 1, j, obstacleGrid);
        int down = uniquePathsByRecursion(i, j - 1, obstacleGrid);
        return (up + down);
    }

    /*  
        Method 02:
        TC: O(N*M) SC: O(M+N)+O(M*N)[path length and stack space]
    */
    public int uniquePathsByMemoization(int i, int j, int[][] dp, int[][] obstacleGrid) {
        
        if(i < 0 || j < 0){
            return 0;
        }
        if(obstacleGrid[i][j] == 1){
            return 0;
        }
        if(i == 0 && j == 0){
            return 1;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int up = uniquePathsByMemoization(i - 1, j, dp, obstacleGrid);
        int down = uniquePathsByMemoization(i, j - 1, dp, obstacleGrid);
        return dp[i][j] = (up + down);
    }

    /*  
        Method 03:
        TC: O(N*M) SC: O(M*N)[no rec stack space, only dp array]
    */
    public int uniquePathsByTabulation(int m, int n, int[][] dp, int[][] obstacleGrid) {
        dp[1][1] = 1;
        for(int i = 0; i < m; i++){
            dp[i][0] = 0;
        }
        for(int j = 0; j < n; j++){
            dp[0][j] = 0;
        }
        
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(obstacleGrid[i - 1][j - 1] == 1){
                    dp[i][j] = 0;
                    
                }
                else if(i == 1 && j == 1){
                    dp[i][j] = 1;
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
    public int uniquePathsByTabulationOptimized(int m, int n, int[][] obstacleGrid) {
        
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
                if(obstacleGrid[i - 1][j - 1] == 1){
                    curr[j] = 0;
                    
                }
                else if(i == 1 && j == 1){
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