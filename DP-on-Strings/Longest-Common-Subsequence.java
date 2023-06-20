/*
1143. Longest Common Subsequence
https://leetcode.com/problems/longest-common-subsequence/

Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0.
A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters.
For example, "ace" is a subsequence of "abcde".
A common subsequence of two strings is a subsequence that is common to both strings.

Example 1:
Input: text1 = "abcde", text2 = "ace" 
Output: 3  
Explanation: The longest common subsequence is "ace" and its length is 3.

Example 2:
Input: text1 = "abc", text2 = "abc"
Output: 3
Explanation: The longest common subsequence is "abc" and its length is 3.

Example 3:
Input: text1 = "abc", text2 = "def"
Output: 0
Explanation: There is no such common subsequence, so the result is 0.
 
Constraints:
1 <= text1.length, text2.length <= 1000
text1 and text2 consist of only lowercase English characters.
*/

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        
        int l1 = text1.length();
        int l2 = text2.length();
        //return longestCommonSubsequenceByRecursion(l1 - 1, text1, l2 - 1, text2);
        /*
        int[][] dp = new int[l1][l2];
        for(int i = 0; i < l1; i++){
            Arrays.fill(dp[i], -1);
        }
        return longestCommonSubsequenceByMemoization(l1 - 1, text1, l2 - 1, text2, dp);
        */
        /*
        int[][] dp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            Arrays.fill(dp[i], 0);
        }
        return longestCommonSubsequenceByTabulation(l1, text1, l2, text2, dp);
        */
        return longestCommonSubsequenceByTabulationOptimized(l1, text1, l2, text2);
    }

    /*  
        Method 01:
        TC: O(2^(N*M)) SC: O(M+N)
    */
    public int longestCommonSubsequenceByRecursion(int i, String text1, int j, String text2) {
        
        if(i < 0 || j < 0){
            return 0;
        }
        if(text1.charAt(i) == text2.charAt(j)){
            return 1 + longestCommonSubsequenceByRecursion(i - 1, text1, j - 1, text2);
        }
        else{
            int lcs1 = longestCommonSubsequenceByRecursion(i - 1, text1, j, text2);
            int lcs2 = longestCommonSubsequenceByRecursion(i, text1, j - 1, text2);
            return Math.max(lcs1, lcs2);
        }
    }

    /*  
        Method 02:
        TC: O(N*M) SC: O(M+N)+O(M*N)[path length and stack space]
    */
    public int longestCommonSubsequenceByMemoization(int i, String text1, int j, String text2, int[][] dp) {
        
        if(i < 0 || j < 0){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        if(text1.charAt(i) == text2.charAt(j)){
            return 1 + longestCommonSubsequenceByMemoization(i - 1, text1, j - 1, text2, dp);
        }
        else{
            int lcs1 = longestCommonSubsequenceByMemoization(i - 1, text1, j, text2, dp);
            int lcs2 = longestCommonSubsequenceByMemoization(i, text1, j - 1, text2, dp);
            return dp[i][j] = Math.max(lcs1, lcs2);
        }
    }

    /*  
        Method 03:
        TC: O(N*M) SC: O(M*N)[no rec stack space, only dp array]
    */
    public int longestCommonSubsequenceByTabulation(int l1, String text1, int l2, String text2, int[][] dp) {
        
        for(int i = 0; i <= l1; i++){
            dp[i][0] = 0;
        }
        for(int j = 0; j <= l2; j++){
            dp[0][j] = 0;
        }
        for(int i = 1; i <= l1; i++){
            for(int j = 1; j <= l2; j++){
                if(text1.charAt(i - 1) == text2.charAt(j - 1)){
                    dp[i][j] =  1 + dp[i - 1][j - 1];
                }
                else{
                    int lcs1 = dp[i - 1][j];
                    int lcs2 = dp[i][j - 1];
                    dp[i][j] = Math.max(lcs1, lcs2);
                }
            }
        }
        return dp[l1][l2];
    }

    /*  
        Method 04:
        TC: O(N*M) SC: O(N)[no path length, no dp array, just for new arr temp]
    */
    public int longestCommonSubsequenceByTabulationOptimized(int l1, String text1, int l2, String text2) {
        
        int[] prev = new int[l2 + 1];
        for(int j = 0; j <= l2; j++){
            prev[j] = 0;
        }
        for(int i = 1; i <= l1; i++){
            int[] curr = new int[l2 + 1];
            curr[0] = 0;
            for(int j = 1; j <= l2; j++){
                if(text1.charAt(i - 1) == text2.charAt(j - 1)){
                    curr[j] =  1 +prev[j - 1];
                }
                else{
                    int lcs1 = prev[j];
                    int lcs2 = curr[j - 1];
                    curr[j] = Math.max(lcs1, lcs2);
                }
            }
            prev = curr;
        }
        return prev[l2];
    }
}