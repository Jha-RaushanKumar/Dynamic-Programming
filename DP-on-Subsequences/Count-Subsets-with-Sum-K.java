/*
Perfect Sum Problem
https://practice.geeksforgeeks.org/problems/perfect-sum-problem5633/1

Given an array arr[] of non-negative integers and an integer sum, the task is to count all subsets of the given array with a sum equal to a given sum.
Note: Answer can be very large, so, output answer modulo 109+7

Example 1:
Input: N = 6, arr[] = {2, 3, 5, 6, 8, 10}
       sum = 10
Output: 3
Explanation: {2, 3, 5}, {2, 8}, {10}

Example 2:
Input: N = 5, arr[] = {1, 2, 3, 4, 5}
       sum = 10
Output: 3
Explanation: {1, 2, 3, 4}, {1, 4, 5}, {2, 3, 5}

Your Task:  
You don't need to read input or print anything. Complete the function perfectSum() which takes N, array arr[] and sum as input parameters and returns an integer value
Expected Time Complexity: O(N*sum)
Expected Auxiliary Space: O(N*sum)

Constraints:
1 ≤ N*sum ≤ 106
0<=arr[I]<=106
*/

class Solution{
    
    int mod = (int)1e9 + 7;
	public int perfectSum(int arr[],int n, int sum) 
	{ 
	    // Your code goes here
	    //return perfectSumByRecursion(n - 1, arr, sum);
	    /*
	    int[][] dp = new int[n][sum + 1];
	    for(int[] row : dp){
	        Arrays.fill(row, -1);
	    }
	    return perfectSumByMemoization(n - 1, arr, sum, dp);
	    */
	    int[][] dp = new int[n][sum + 1];
	    for(int[] row : dp){
	        Arrays.fill(row, 0);
	    }
	    return perfectSumByTabulation(n, arr, sum, dp);
	}
	
	public int perfectSumByRecursion(int i, int[] arr, int sum){
	    
	    if(i == 0){
	        if(sum == 0 && arr[i] == 0){
	            return 2;
	        }
	        if(sum == 0){
	            return 1;
	        }
	        if(sum == arr[i]){
	            return 1;
	        }
	        return 0;
	    }
	    int pick = 0;
	    if(sum >= arr[i]){
	        pick = perfectSumByRecursion(i - 1, arr, sum - arr[i]);
	    }
	    int notPick = perfectSumByRecursion(i - 1, arr, sum);
	    return (pick + notPick) % mod;
	}
	
	public int perfectSumByMemoization(int i, int[] arr, int sum, int[][] dp){
	    
	    if(i == 0){
	        if(sum == 0 && arr[i] == 0){
	            return 2;
	        }
	        if(sum == 0){
	            return 1;
	        }
	        if(sum == arr[i]){
	            return 1;
	        }
	        return 0;
	    }
	    if(dp[i][sum] != -1){
	        return dp[i][sum];
	    }
	    int pick = 0;
	    if(sum >= arr[i]){
	        pick = perfectSumByMemoization(i - 1, arr, sum - arr[i], dp);
	    }
	    int notPick = perfectSumByMemoization(i - 1, arr, sum, dp);
	    return dp[i][sum] = (pick + notPick) % mod;
	}
	
	public int perfectSumByTabulation(int n, int[] arr, int sum, int[][] dp){
	    
	    for(int j = 0; j <= sum; j++){
	        if(j == 0 && arr[0] == 0){
	            dp[0][j] = 2;
	        }
	        else if(j == 0){
	            dp[0][j] =  1;
	        }
	        else if(j == arr[0]){
	            dp[0][j] = 1;
	        }
	        else{
	            dp[0][j] = 0;
	        }
	    }
	    
	    for(int i = 1; i < n; i++){
	        for(int j = 0; j <= sum; j++){
	            int pick = 0;
        	    if(j >= arr[i]){
        	        pick = dp[i - 1][j - arr[i]];
        	    }
        	    int notPick = dp[i - 1][j];
        	    dp[i][j] = (pick + notPick) % mod;
	        }
	    }
	    return dp[n - 1][sum];
	}
}