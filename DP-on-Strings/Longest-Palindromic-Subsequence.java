/*
516. Longest Palindromic Subsequence
https://leetcode.com/problems/longest-palindromic-subsequence/

Given a string s, find the longest palindromic subsequence's length in s.
A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.

Example 1:
Input: s = "bbbab"
Output: 4
Explanation: One possible longest palindromic subsequence is "bbbb".

Example 2:
Input: s = "cbbd"
Output: 2
Explanation: One possible longest palindromic subsequence is "bb".
 
Constraints:
1 <= s.length <= 1000
s consists only of lowercase English letters.
*/

class Solution {
    public int longestPalindromeSubseq(String s) {
        
        int l = s.length();
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        int[][] dp = new int[l + 1][l + 1];
        for(int i = 0; i <= l; i++){
            Arrays.fill(dp[i], 0);
        }
        return longestPalindromeSubseqByTabulation(l, s, sb.toString(), dp);
    }

    public int longestPalindromeSubseqByTabulation(int l, String s1, String s2, int[][] dp){

        for(int i = 0; i <= l; i++){
            dp[i][0] = 0;
        }
        for(int j = 0; j <= l; j++){
            dp[0][j] = 0;
        }
        for(int i = 1; i <= l; i++){
            for(int j = 1; j <= l; j++){
                if(s1.charAt(i - 1) == s2.charAt(j - 1)){
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                }
                else{
                    int lcs1 = dp[i - 1][j];
                    int lcs2 = dp[i][j - 1];
                    dp[i][j] = Math.max(lcs1, lcs2);
                }
            }
        }
        return dp[l][l];       
    }
}