/*
1092. Shortest Common Supersequence
https://leetcode.com/problems/shortest-common-supersequence/

Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences. If there are multiple valid strings, return any of them.
A string s is a subsequence of string t if deleting some number of characters from t (possibly 0) results in the string s.

Example 1:
Input: str1 = "abac", str2 = "cab"
Output: "cabac"
Explanation: 
str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
The answer provided is the shortest such string that satisfies these properties.

Example 2:
Input: str1 = "aaaaaaaa", str2 = "aaaaaaaa"
Output: "aaaaaaaa"
Constraints:
1 <= str1.length, str2.length <= 1000
str1 and str2 consist of lowercase English letters.
*/

class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        
        int l1 = str1.length();
        int l2 = str2.length();
        int[][] dp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            Arrays.fill(dp[i], 0);
        }
        longestCommonSubsequenceByTabulation(l1, str1, l2, str2, dp);

        int i = l1;
        int j = l2;
        StringBuilder sb = new StringBuilder();
        while(i > 0 && j > 0){
            if(str1.charAt(i - 1) == str2.charAt(j - 1)){
                sb.append(str1.charAt(i - 1));
                i--;
                j--;
            }
            else{
                if(dp[i - 1][j] > dp[i][j - 1]){
                    sb.append(str1.charAt(i - 1));
                    i--;
                }
                else{
                    sb.append(str2.charAt(j - 1));
                    j--;
                }
            }
        }
        while(i > 0){
            sb.append(str1.charAt(i - 1));
            i--;
        }
        while(j > 0){
            sb.append(str2.charAt(j - 1));
            j--;
        }
        return sb.reverse().toString();
    }
    
    public void longestCommonSubsequenceByTabulation(int l1, String text1, int l2, String text2, int[][] dp) {
        
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
    }
}