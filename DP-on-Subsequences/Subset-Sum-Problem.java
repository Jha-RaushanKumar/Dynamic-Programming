/*
Subset Sum Problem
https://practice.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1

Given an array of non-negative integers, and a value sum, determine if there is a subset of the given set with sum equal to given sum. 

Example 1:
Input:
N = 6
arr[] = {3, 34, 4, 12, 5, 2}
sum = 9
Output: 1 
Explanation: Here there exists a subset with sum = 9, 4+3+2 = 9.

Example 2:
Input:
N = 6
arr[] = {3, 34, 4, 12, 5, 2}
sum = 30
Output: 0 
Explanation: There is no subset with sum 30.

Your Task:  
You don't need to read input or print anything. Your task is to complete the function isSubsetSum() which takes the array arr[], its size N and an integer sum as input parameters and returns boolean value true if there exists a subset with given sum and false otherwise.
The driver code itself prints 1, if returned value is true and prints 0 if returned value is false.

Expected Time Complexity: O(sum*N)
Expected Auxiliary Space: O(sum*N)
 
Constraints:
1 <= N <= 100
1<= arr[i] <= 100
1<= sum <= 105
*/

class Solution{

    static boolean isSubsetSum(int N, int arr[], int sum){
        // code here
        //return isSubsetSumByRecursion(N - 1, arr, sum);
        /*
        int[][] dp = new int[N][sum + 1];
        for(int i = 0; i < N; i++){
            Arrays.fill(dp[i], -1);
        }
        return isSubsetSumByMemoization(N - 1, arr, sum, dp);
        */
        boolean[][] dp = new boolean[N][sum + 1];
        return isSubsetSumByTabulation(N, arr, sum, dp);
    }
    
    static boolean isSubsetSumByRecursion(int i, int arr[], int sum){
        // code here
        if(sum == 0){
            return true;
        }
        if(i == 0){
            if(sum == arr[i]){
                return true;
            }
            return false;
            
        }
        boolean pick = isSubsetSumByRecursion(i - 1, arr, sum - arr[i]);
        boolean notPick = isSubsetSumByRecursion(i - 1, arr, sum);
        return pick || notPick;
    }
    
    static boolean isSubsetSumByMemoization(int i, int arr[], int sum, int[][] dp){
        // code here
        if(sum == 0){
            return true;
        }
        if(i == 0){
            if(sum == arr[i]){
                return true;
            }
            return false;
            
        }
        if(dp[i][sum] != -1){
            if(dp[i][sum] == 1){
                return true;
            }
            else{
                return false;
            }
        }
        boolean pick = false;
        if(sum >= arr[i]){
            pick = isSubsetSumByMemoization(i - 1, arr, sum - arr[i], dp);
        }
        boolean notPick = isSubsetSumByMemoization(i - 1, arr, sum, dp);
        if(pick || notPick){
            dp[i][sum] = 1;
            return true;
        }
        else{
            dp[i][sum] = 0;
            return false;
        }
    }
    
    static boolean isSubsetSumByTabulation(int n, int arr[], int sum, boolean[][] dp){
        // code here
        for(int i = 0; i < n; i++){
            dp[i][0] = true;
        }
        if(sum >= arr[0]){
            dp[0][arr[0]] = true;
        }
        
        for(int i = 1; i < n; i++){
            for(int j = 1; j <= sum; j++){
                boolean pick = false;
                if(j >= arr[i]){
                    pick = dp[i - 1][j - arr[i]];
                }
                boolean notPick = dp[i - 1][j];
                if(pick || notPick){
                    dp[i][j] = true;
                }
                else{
                    dp[i][j] = false;
                }
            }
        }
        return dp[n - 1][sum];
    }
}