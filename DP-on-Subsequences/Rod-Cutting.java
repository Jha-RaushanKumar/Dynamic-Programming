/*
Rod Cutting
https://practice.geeksforgeeks.org/problems/rod-cutting0840/1

Given a rod of length N inches and an array of prices, price[]. price[i] denotes the value of a piece of length i. Determine the maximum value obtainable by cutting up the rod and selling the pieces.
Note: Consider 1-based indexing.

Example 1:
Input:
N = 8
Price[] = {1, 5, 8, 9, 10, 17, 17, 20}
Output: 22
Explanation:
The maximum obtainable value is 22 by cutting in two pieces of lengths 2 and 6, i.e., 5+17=22.

Example 2:
Input:
N=8
Price[] = {3, 5, 8, 9, 10, 17, 17, 20}
Output: 24
Explanation: 
The maximum obtainable value is 24 by cutting the rod into 8 pieces of length 3, i.e, 8*3=24. 

Your Task:  
You don't need to read input or print anything. Your task is to complete the function cutRod() which takes the array A[] and its size N as inputs and returns the maximum price obtainable.
Expected Time Complexity: O(N2)
Expected Auxiliary Space: O(N)

Constraints:
1 ≤ N ≤ 1000
1 ≤ Ai ≤ 105
*/

class Solution{
    public int cutRod(int price[], int n) {
        //code here
        int rodLength = n;
        // return cutRodByRecursion(n - 1, price, rodLength);
        /*
        int[][] dp = new int[n][n + 1];
        for(int[] row : dp){
            Arrays.fill(row, -1);
        }
        return cutRodByMemoization(n - 1, price, rodLength, dp);
        */
        /*
        int[][] dp = new int[n][n + 1];
        for(int[] row : dp){
            Arrays.fill(row, 0);
        }
        return cutRodByTabulation(n, price, rodLength, dp);
        */
        return cutRodByTabulationOptimized(n, price, rodLength);
    }
    
    /*  
        Method 01:
        TC: O(2^(N*k)) SC: O(N+k)
    */
    public int cutRodByRecursion(int i, int price[], int rodLength) {
        //code here
        if(rodLength == 0){
            return 0;
        }
        if(i == 0){
            if(rodLength >= i + 1){
                return rodLength * price[i];
            }
            else{
                return 0;
            }
        }
        int pick = Integer.MIN_VALUE;
        if(rodLength >= i + 1){
            pick = price[i] + cutRodByRecursion(i, price, rodLength - (i + 1));
        }
        int notPick = cutRodByRecursion(i - 1, price, rodLength);
        return Math.max(pick, notPick);
    }
    
    /*  
        Method 02:
        TC: O(N*W) SC: O(N)+O(N*W)[path length and stack space]
    */
    public int cutRodByMemoization(int i, int price[], int rodLength, int[][] dp) {
        //code here
        if(rodLength == 0){
            return 0;
        }
        if(i == 0){
            if(rodLength >= i + 1){
                return rodLength * price[i];
            }
            else{
                return 0;
            }
        }
        if(dp[i][rodLength] != -1){
            return dp[i][rodLength];
        }
        int pick = Integer.MIN_VALUE;
        if(rodLength >= i + 1){
            pick = price[i] + cutRodByMemoization(i, price, rodLength - (i + 1), dp);
        }
        int notPick = cutRodByMemoization(i - 1, price, rodLength, dp);
        return dp[i][rodLength] = Math.max(pick, notPick);
    }
    
    /*  
        Method 03:
        TC: O(N*W) SC: O(N*W)[no rec stack space, only dp array]
    */
    public int cutRodByTabulation(int n, int price[], int rodLength, int[][] dp) {
        //code here
        for(int j = 0; j <= rodLength; j++){
            if(j >= 1){
                dp[0][j] = j * price[0];
            }
        }
        for(int i = 1; i < n; i++){
            for(int j = 1; j <= rodLength; j++){
                int pick = Integer.MIN_VALUE;
                if(j >= i + 1){
                    pick = price[i] + dp[i][j - (i + 1)];
                }
                int notPick = dp[i - 1][j];
                dp[i][j] = Math.max(pick, notPick);
            }
        }
        return dp[n - 1][rodLength];
    }
    
    /*  
        Method 04:
        TC: O(N*W) SC: O(W)[no rec stack space, only dp array]
    */
    public int cutRodByTabulationOptimized(int n, int price[], int rodLength) {
        //code here
        int[] prev = new int[rodLength + 1];
        for(int j = 0; j <= rodLength; j++){
            if(j >= 1){
                prev[j] = j * price[0];
            }
        }
        for(int i = 1; i < n; i++){
            int[] curr = new int[rodLength + 1];
            for(int j = 1; j <= rodLength; j++){
                int pick = Integer.MIN_VALUE;
                if(j >= i + 1){
                    pick = price[i] + curr[j - (i + 1)];
                }
                int notPick = prev[j];
                curr[j] = Math.max(pick, notPick);
            }
            prev = curr;
        }
        return prev[rodLength];
    }
}