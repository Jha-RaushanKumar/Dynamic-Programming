/*
931. Minimum Falling Path Sum
https://leetcode.com/problems/minimum-falling-path-sum/

Given an n x n array of integers matrix, return the minimum sum of any falling path through matrix.
A falling path starts at any element in the first row and chooses the element in the next row that is either directly below or diagonally left/right. Specifically, the next element from position (row, col) will be (row + 1, col - 1), (row + 1, col), or (row + 1, col + 1).

Example 1:
Input: matrix = [[2,1,3],[6,5,4],[7,8,9]]
Output: 13
Explanation: There are two falling paths with a minimum sum as shown.

Example 2:
Input: matrix = [[-19,57],[-40,-5]]
Output: -59
Explanation: The falling path with a minimum sum is shown.
 
Constraints:
n == matrix.length == matrix[i].length
1 <= n <= 100
-100 <= matrix[i][j] <= 100
*/

class Solution {
    public int minFallingPathSum(int[][] matrix) {
        
        int m = matrix.length;
        int n = matrix[0].length;
        /*
        int min = Integer.MAX_VALUE;
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            Arrays.fill(dp[i], -1);
        }
        for(int j = 0; j < n; j++){
            int ans = minFallingPathSumByMemoization(m - 1, j, matrix, dp);
            min = Math.min(min, ans);
        }
        return min;
        */
        /*
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            Arrays.fill(dp[i], 0);
        }
        return minFallingPathSumByTabulation(m, n, matrix, dp);
        */
        return minFallingPathSumByTabulationOptimized(m, n, matrix);
    }

    /*  
        Method 01:
        TC: O(2^(N*M)) SC: O(M+N)
    */
    public int minFallingPathSumByRecursion(int i, int j, int[][] matrix) {
        
        if(i < 0 || j < 0 || j >=matrix.length){
            return (int)1e9;
        }
        if(i == 0){
            return matrix[i][j];
        }
        int up = matrix[i][j] + minFallingPathSumByRecursion(i - 1, j, matrix);
        int diagleft = matrix[i][j] + minFallingPathSumByRecursion(i - 1, j - 1, matrix);
        int diagright = matrix[i][j] + minFallingPathSumByRecursion(i - 1, j + 1, matrix);
        return Math.min(up, Math.min(diagleft, diagright));
    }

    /*  
        Method 02:
        TC: O(N*M) SC: O(M+N)+O(M*N)[path length and stack space]
    */
    public int minFallingPathSumByMemoization(int i, int j, int[][] matrix, int[][] dp) {
        
        if(i < 0 || j < 0 || j >=matrix.length){
            return (int)1e9;
        }
        if(i == 0){
            return matrix[i][j];
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int up = matrix[i][j] + minFallingPathSumByMemoization(i - 1, j, matrix, dp);
        int diagleft = matrix[i][j] + minFallingPathSumByMemoization(i - 1, j - 1, matrix, dp);
        int diagright = matrix[i][j] + minFallingPathSumByMemoization(i - 1, j + 1, matrix, dp);
        return dp[i][j] = Math.min(up, Math.min(diagleft, diagright));
    }

    /*  
        Method 03:
        TC: O(N*M) SC: O(M*N)[no rec stack space, only dp array]
    */
    public int minFallingPathSumByTabulation(int m, int n, int[][] matrix, int[][] dp) {
        
        for(int j = 0; j < n; j++){
            dp[0][j] = matrix[0][j];
        }
        for(int i = 1; i < m; i++){
            for(int j = 0; j < n; j++){
                int up = matrix[i][j] + dp[i - 1][j];
                int diagleft = (int)1e9;
                if(j - 1 >= 0){
                    diagleft = matrix[i][j] + dp[i - 1][j - 1];
                }
                int diagright = (int)1e9;
                if(j + 1 < n){
                    diagright = matrix[i][j] + dp[i - 1][j + 1];
                }
                dp[i][j] = Math.min(up, Math.min(diagleft, diagright));
            }
        }
        int min = Integer.MAX_VALUE;
        for(int j = 0; j < n; j++){
            min = Math.min(min, dp[m - 1][j]);
        }
        return min;
    }

    /*  
        Method 04:
        TC: O(N*M) SC: O(N)[no path length, no dp array, just for new arr temp]
    */
    public int minFallingPathSumByTabulationOptimized(int m, int n, int[][] matrix) {
        
        int prev[] = new int[n];
        for(int j = 0; j < n; j++){
            prev[j] = matrix[0][j];
        }
        for(int i = 1; i < m; i++){
            int curr[] = new int[n];
            for(int j = 0; j < n; j++){
                int up = matrix[i][j] + prev[j];
                int diagleft = (int)1e9;
                if(j - 1 >= 0){
                    diagleft = matrix[i][j] + prev[j - 1];
                }
                int diagright = (int)1e9;
                if(j + 1 < n){
                    diagright = matrix[i][j] + prev[j + 1];
                }
                curr[j] = Math.min(up, Math.min(diagleft, diagright));
            }
            prev = curr;
        }
        int min = Integer.MAX_VALUE;
        for(int j = 0; j < n; j++){
            min = Math.min(min, prev[j]);
        }
        return min;
    }
}