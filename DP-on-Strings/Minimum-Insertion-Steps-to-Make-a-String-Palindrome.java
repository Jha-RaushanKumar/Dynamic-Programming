/*
1312. Minimum Insertion Steps to Make a String Palindrome
https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/

Given a string s. In one step you can insert any character at any index of the string.
Return the minimum number of steps to make s palindrome.
A Palindrome String is one that reads the same backward as well as forward.

Example 1:
Input: s = "zzazz"
Output: 0
Explanation: The string "zzazz" is already palindrome we do not need any insertions.

Example 2:
Input: s = "mbadm"
Output: 2
Explanation: String can be "mbdadbm" or "mdbabdm".

Example 3:
Input: s = "leetcode"
Output: 5
Explanation: Inserting 5 characters the string becomes "leetcodocteel".
 
Constraints:
1 <= s.length <= 500
s consists of lowercase English letters.
*/

class Solution {
    public int minInsertions(String s) {
        
        int l = s.length();
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        int[][] dp = new int[l + 1][l + 1];
        for(int i = 0; i <= l; i++){
            Arrays.fill(dp[i], 0);
        }
        int lcs = longestPalindromeSubseqByTabulation(l , s, sb.toString(), dp);
        return l - lcs;
    }

    public int longestPalindromeSubseqByTabulation(int l, String s1, String s2, int[][] dp) {
        
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