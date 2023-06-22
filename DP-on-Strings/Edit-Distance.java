/*
72. Edit Distance
https://leetcode.com/problems/edit-distance/

Given two strings word1 and word2, return the minimum number of operations required to convert word1 to word2.
You have the following three operations permitted on a word:
Insert a character
Delete a character
Replace a character

Example 1:
Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')

Example 2:
Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')
 
Constraints:
0 <= word1.length, word2.length <= 500
word1 and word2 consist of lowercase English letters.
*/

class Solution {
    public int minDistance(String word1, String word2) {
        
        int l1 = word1.length();
        int l2 = word2.length();
        // return minDistanceByRecursion(l1 - 1, word1, l2 - 1, word2);
        /*
        int[][] dp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            Arrays.fill(dp[i], -1);
        }
        return minDistanceByMemoization(l1 - 1, word1, l2 - 1, word2, dp);
        */
        /*
        int[][] dp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            Arrays.fill(dp[i], 0);
        }
        return minDistanceByTabulation(l1, word1, l2, word2, dp);
        */
        return minDistanceByTabulationOptimized(l1, word1, l2, word2);
    }

    /*  
        Method 01:
        TC: O(2^(N*M)) SC: O(M+N)
    */
    public int minDistanceByRecursion(int i, String word1, int j, String word2) {
        
        if(i < 0){
            return j + 1;
        }
        if(j < 0){
            return i + 1;
        }
        if(word1.charAt(i) != word2.charAt(j)){
            int replace = 1 + minDistanceByRecursion(i - 1, word1, j - 1, word2);
            int delete = 1 + minDistanceByRecursion(i - 1, word1, j, word2);
            int insert = 1 + minDistanceByRecursion(i, word1, j - 1, word2);
            return Math.min(replace, Math.min(delete, insert));
        }
        else{
            return minDistanceByRecursion(i - 1, word1, j - 1, word2);
        }
    }

    /*  
        Method 02:
        TC: O(N*M) SC: O(M+N)+O(M*N)[path length and stack space]
    */
    public int minDistanceByMemoization(int i, String word1, int j, String word2, int[][] dp) {
        
        if(i < 0){
            return j + 1;
        }
        if(j < 0){
            return i + 1;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        if(word1.charAt(i) != word2.charAt(j)){
            int replace = 1 + minDistanceByMemoization(i - 1, word1, j - 1, word2, dp);
            int delete = 1 + minDistanceByMemoization(i - 1, word1, j, word2, dp);
            int insert = 1 + minDistanceByMemoization(i, word1, j - 1, word2, dp);
            return dp[i][j] = Math.min(replace, Math.min(delete, insert));
        }
        else{
            return dp[i][j] = minDistanceByMemoization(i - 1, word1, j - 1, word2, dp);
        }
    }

    /*  
        Method 03:
        TC: O(N*M) SC: O(M*N)[no rec stack space, only dp array]
    */
    public int minDistanceByTabulation(int l1, String word1, int l2, String word2, int[][] dp) {
        
        for(int i = 0; i <= l1; i++){
            dp[i][0] = i;
        }
        for(int j = 0; j <= l2; j++){
            dp[0][j] = j;
        }
        for(int i = 1; i <= l1; i++){
            for(int j = 1; j <= l2; j++){
                if(word1.charAt(i - 1) != word2.charAt(j - 1)){
                    int replace = 1 + dp[i - 1][j - 1];
                    int delete = 1 + dp[i - 1][j];
                    int insert = 1 + dp[i][j - 1];
                    dp[i][j] = Math.min(replace, Math.min(delete, insert));
                }
                else{
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }
        return dp[l1][l2];
    }

    /*  
        Method 04:
        TC: O(N*M) SC: O(N)[no path length, no dp array, just for new arr temp]
    */
    public int minDistanceByTabulationOptimized(int l1, String word1, int l2, String word2) {
        
        int[] prev = new int[l2 + 1];
        for(int j = 0; j <= l2; j++){
            prev[j] = j;
        }
        for(int i = 1; i <= l1; i++){
            int[] curr = new int[l2 + 1];
            curr[0] = i;
            for(int j = 1; j <= l2; j++){
                if(word1.charAt(i - 1) != word2.charAt(j - 1)){
                    int replace = 1 + prev[j - 1];
                    int delete = 1 + prev[j];
                    int insert = 1 + curr[j - 1];
                    curr[j] = Math.min(replace, Math.min(delete, insert));
                }
                else{
                    curr[j] = prev[j - 1];
                }
            }
            prev = curr;
        }
        return prev[l2];
    }
}