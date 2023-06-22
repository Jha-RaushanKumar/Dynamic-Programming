/*
115. Distinct Subsequences
https://leetcode.com/problems/distinct-subsequences/

Given two strings s and t, return the number of distinct 
subsequences of s which equals t.
The test cases are generated so that the answer fits on a 32-bit signed integer.

Example 1:
Input: s = "rabbbit", t = "rabbit"
Output: 3
Explanation:
As shown below, there are 3 ways you can generate "rabbit" from s.
rabbbit
rabbbit
rabbbit

Example 2:
Input: s = "babgbag", t = "bag"
Output: 5
Explanation:
As shown below, there are 5 ways you can generate "bag" from s.
babgbag
babgbag
babgbag
babgbag
babgbag
 
Constraints:
1 <= s.length, t.length <= 1000
s and t consist of English letters.
*/

class Solution {
    public int numDistinct(String s, String t) {
        
        int l1 = s.length();
        int l2 = t.length();
        //return numDistinctByRecursion(l1 - 1, s, l2 - 1, t);
        /*
        int[][] dp = new int[l1][l2];
        for(int i = 0; i < l1; i++){
            Arrays.fill(dp[i], -1);
        }
        return numDistinctByMemoization(l1 - 1, s, l2 - 1, t, dp);
        */
        int[][] dp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            Arrays.fill(dp[i], 0);
        }
        return numDistinctByTabulation(l1, s, l2, t, dp);
    }

    public int numDistinctByRecursion(int i, String s, int j, String t) {
        
        if(i < 0 && j >= 0){
            return 0;
        }
        if(j < 0){
            return 1;
        }
        if(s.charAt(i) == t.charAt(j)){
            int take = numDistinctByRecursion(i - 1, s, j - 1, t);
            int notTake = numDistinctByRecursion(i - 1, s, j, t);
            return (take + notTake);
        }
        else{
            return numDistinctByRecursion(i - 1, s, j, t);
        }
    }

    public int numDistinctByMemoization(int i, String s, int j, String t, int[][] dp) {
        
        if(i < 0 && j >= 0){
            return 0;
        }
        if(j < 0){
            return 1;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        if(s.charAt(i) == t.charAt(j)){
            int take = numDistinctByMemoization(i - 1, s, j - 1, t, dp);
            int notTake = numDistinctByMemoization(i - 1, s, j, t, dp);
            return (take + notTake);
        }
        else{
            return dp[i][j] = numDistinctByMemoization(i - 1, s, j, t, dp);
        }
    }

    public int numDistinctByTabulation(int l1, String s, int l2, String t, int[][] dp) {
        
        for(int i = 0; i <= l1; i++){
            dp[i][0] = 1;
        }
        for(int i = 1; i <= l1; i++){
            for(int j = 1; j <= l2; j++){
                if(s.charAt(i - 1) == t.charAt(j - 1)){
                    int take = dp[i - 1][j - 1];
                    int notTake = dp[i - 1][j];
                    dp[i][j] = (take + notTake);
                }
                else{
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[l1][l2];
    }
}