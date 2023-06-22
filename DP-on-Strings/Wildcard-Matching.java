/*
44. Wildcard Matching
https://leetcode.com/problems/wildcard-matching/

Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:
'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).

Example 1:
Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".

Example 2:
Input: s = "aa", p = "*"
Output: true
Explanation: '*' matches any sequence.

Example 3:
Input: s = "cb", p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 
Constraints:
0 <= s.length, p.length <= 2000
s contains only lowercase English letters.
p contains only lowercase English letters, '?' or '*'.
*/

class Solution {
    public boolean isMatch(String s, String p) {
        
        int l1 = s.length();
        int l2 = p.length();
        //return isMatchByRecursion(l1 - 1, s, l2 - 1, p);
        /*
        int[][] dp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            Arrays.fill(dp[i], -1);
        }
        int ans = isMatchByMemoization(l1 - 1, s, l2 - 1, p, dp);
        if(ans == 1){
            return true;
        }
        return false;
        */
        int[][] dp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            Arrays.fill(dp[i], -1);
        }
        int ans = isMatchByTabulation(l1, s, l2, p, dp);
        if(ans == 1){
            return true;
        }
        return false;
    }

    /*  
        Method 01:
        TC: O(2^(N*M)) SC: O(M+N)
    */
    public boolean isMatchByRecursion(int i, String s, int j, String p) {
        
        if(i < 0 && j < 0){
            return true;
        }
        if(i < 0 && j >= 0){
            for(int k = 0; k <= j; k++){
                if(p.charAt(k) != '*'){
                    return false;
                }
            }
            return true;
        }
        if(i >= 0 && j < 0){
            return false;
        }
        if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '?'){
            return isMatchByRecursion(i - 1, s, j - 1, p);
        }
        else{
            if(p.charAt(j) == '*'){
                return isMatchByRecursion(i - 1, s, j, p) || isMatchByRecursion(i, s, j - 1, p);
            }
            else{
                return false;
            }
        }
    }

    /*  
        Method 02:
        TC: O(N*M) SC: O(M+N)+O(M*N)[path length and stack space]
    */
    public int isMatchByMemoization(int i, String s, int j, String p, int[][] dp) {
        
        if(i < 0 && j < 0){
            return 1;
        }
        if(i < 0 && j >= 0){
            for(int k = 0; k <= j; k++){
                if(p.charAt(k) != '*'){
                    return 0;
                }
            }
            return 1;
        }
        if(i >= 0 && j < 0){
            return 0;
        }
        if(dp[i][j] != -1){
            return dp[i][j];
        }
        if(s.charAt(i) == p.charAt(j) || p.charAt(j) == '?'){
            return dp[i][j] = isMatchByMemoization(i - 1, s, j - 1, p, dp);
        }
        else{
            if(p.charAt(j) == '*'){
                boolean ans = (isMatchByMemoization(i - 1, s, j, p, dp) == 1) || (isMatchByMemoization(i, s, j - 1, p, dp) == 1);
                if(ans){
                    return dp[i][j] = 1;
                }
                else{
                    return dp[i][j] = 0;
                }
            }
            else{
                return dp[i][j] = 0;
            }
        }
    }

    /*  
        Method 03:
        TC: O(N*M) SC: O(M*N)[no rec stack space, only dp array]
    */
    public int isMatchByTabulation(int l1, String s, int l2, String p, int[][] dp) {
        
        dp[0][0] = 1;
        for(int i = 1; i <= l1; i++){
            dp[i][0] = 0;
        }
        for(int j = 1; j <= l2; j++){
            boolean flag = false;
            for(int k = 0; k < j; k++){
                if(p.charAt(k) != '*'){
                    flag = true;
                    break;
                }
            }
            if(flag){
                dp[0][j] = 0;
            }
            else{
                dp[0][j] = 1;
            }
            
        }
        for(int i = 1; i <= l1; i++){
            for(int j = 1; j <= l2; j++){
                if(s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?'){
                    dp[i][j] = dp[i - 1][j - 1];
                }
                else{
                    if(p.charAt(j - 1) == '*'){
                        boolean ans = (dp[i - 1][j] == 1) || (dp[i][j - 1] == 1);
                        if(ans){
                            dp[i][j] = 1;
                        }
                        else{
                            dp[i][j] = 0;
                        }
                    }
                    else{
                        dp[i][j] = 0;
                    }
                }
            }
        }
        return dp[l1][l2];
    }
}