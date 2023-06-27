/*
Minimum sum partition
https://practice.geeksforgeeks.org/problems/minimum-sum-partition3317/1

Given an array arr of size n containing non-negative integers, the task is to divide it into two sets S1 and S2 such that the absolute difference between their sums is minimum and find the minimum difference

Example 1:
Input: N = 4, arr[] = {1, 6, 11, 5} 
Output: 1
Explanation: 
Subset1 = {1, 5, 6}, sum of Subset1 = 12 
Subset2 = {11}, sum of Subset2 = 11   

Example 2:
Input: N = 2, arr[] = {1, 4}
Output: 3
Explanation: 
Subset1 = {1}, sum of Subset1 = 1
Subset2 = {4}, sum of Subset2 = 4

Your Task:  
You don't need to read input or print anything. Complete the function minDifference() which takes N and array arr as input parameters and returns the integer value
Expected Time Complexity: O(N*|sum of array elements|)
Expected Auxiliary Space: O(N*|sum of array elements|)

Constraints:
1 ≤ N*|sum of array elements| ≤ 106
0 < arr[i] <= 105
*/

class Solution
{
	public int minDifference(int arr[], int n) 
	{ 
	    // Your code goes here
	    int sum = 0;
	    for(int i = 0; i < n; i++){
	        sum += arr[i];
	    }
	    boolean[][] dp = new boolean[n][sum + 1];
	    
	    for(int i = 0; i < n; i++){
	        dp[i][0] = true;
	    }
	    if(arr[0] == sum){
	        dp[0][arr[0]] = true;
	    }
	    
	    for(int i = 1; i < n; i++){
	        for(int j = 1; j <= sum; j++){
	            boolean pick = false;
	            if(j >= arr[i]){
	                pick = dp[i - 1][j - arr[i]];
	            }
	            boolean notPick = dp[i - 1][j];
	            dp[i][j] = pick || notPick;
	        }
	    }
	    
	    int min = Integer.MAX_VALUE;
	    for(int j = 0; j <= sum; j++){
	        if(dp[n - 1][j]){
	            int diff = Math.abs(j - (sum - j));
	            min = Math.min(min, diff);
	        }
	    }
	    return min;
	} 
}