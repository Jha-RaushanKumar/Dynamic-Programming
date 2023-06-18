/*
120. Triangle
https://leetcode.com/problems/triangle/

Given a triangle array, return the minimum path sum from top to bottom.
For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current row, you may move to either index i or index i + 1 on the next row.

Example 1:
Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
Output: 11
Explanation: The triangle looks like:
   2
  3 4
 6 5 7
4 1 8 3
The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).

Example 2:
Input: triangle = [[-10]]
Output: -10
 
Constraints:
1 <= triangle.length <= 200
triangle[0].length == 1
triangle[i].length == triangle[i - 1].length + 1
-104 <= triangle[i][j] <= 104
Follow up: Could you do this using only O(n) extra space, where n is the total number of rows in the triangle?
*/

class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        
        //return minimumTotalByRecursion(0, 0, triangle);
        int n = triangle.size();
        /*
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], -1);
        }
        return minimumTotalByMemoization(0, 0, triangle, dp);
        */
        /*
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], 0);
        }
        return minimumTotalByTabulation(n, triangle, dp);
        */
        return minimumTotalByTabulationOptimized(n, triangle);
    }

    /*  
        Method 01:
        TC: O(2^(N*N)) SC: O(N+N)
    */
    public int minimumTotalByRecursion(int i, int j, List<List<Integer>> triangle) {
        
        if(i == triangle.size() - 1){
            return triangle.get(i).get(j);
        }
        int down = triangle.get(i).get(j) + minimumTotalByRecursion(i + 1, j, triangle);
        int right = triangle.get(i).get(j) + minimumTotalByRecursion(i + 1, j + 1, triangle);
        return Math.min(down, right);
    }

    /*  
        Method 02:
        TC: O(N*N) SC: O(N+N)+O(N*N)[path length and stack space]
    */
    public int minimumTotalByMemoization(int i, int j, List<List<Integer>> triangle, int[][] dp) {
        
        if(i == triangle.size() - 1){
            return triangle.get(i).get(j);
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        int down = triangle.get(i).get(j) + minimumTotalByMemoization(i + 1, j, triangle, dp);
        int right = triangle.get(i).get(j) + minimumTotalByMemoization(i + 1, j + 1, triangle, dp);
        return dp[i][j] = Math.min(down, right);
    }

    /*  
        Method 03:
        TC: O(N*N) SC: O(N*N)[no rec stack space, only dp array]
    */
    public int minimumTotalByTabulation(int n, List<List<Integer>> triangle, int[][] dp) {
        
        for(int j = 0; j < n; j++){
            dp[n - 1][j] = triangle.get(n - 1).get(j);
        }
        for(int i = n - 2; i >= 0; i--){
            for(int j = i; j >= 0; j--){
                int down = triangle.get(i).get(j) + dp[i + 1][j];
                int right = triangle.get(i).get(j) + dp[i + 1][j + 1];
                dp[i][j] = Math.min(down, right);
            }
        }
        return dp[0][0];
    }

    /*  
        Method 04:
        TC: O(N*N) SC: O(N)[no path length, no dp array, just for new arr temp]
    */
    public int minimumTotalByTabulationOptimized(int n, List<List<Integer>> triangle) {
        
        int front[] = new int[n];
        
        for(int j = 0; j < n; j++){
            front[j] = triangle.get(n - 1).get(j);
        }
        for(int i = n - 2; i >= 0; i--){
            int curr[] = new int[n];
            for(int j = i; j >= 0; j--){
                int down = triangle.get(i).get(j) + front[j];
                int right = triangle.get(i).get(j) + front[j + 1];
                curr[j] = Math.min(down, right);
            }
            front= curr;
        }
        return front[0];
    }

}