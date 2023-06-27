/*
Partitions with Given Difference
https://practice.geeksforgeeks.org/problems/partitions-with-given-difference/1

Given an array arr, partition it into two subsets(possibly empty) such that their union is the original array. Let the sum of the element of these two subsets be S1 and S2. 
Given a difference d, count the number of partitions in which S1 is greater than or equal to S2 and the difference S1 and S2 is equal to d. since the answer may be large return it modulo 109 + 7.

Example 1:
Input:
n = 4, d = 3
arr[] =  { 5, 2, 6, 4}
Output: 1
Explanation:
There is only one possible partition of this array. Partition : {6, 4}, {5, 2}. The subset difference between subset sum is: (6 + 4) - (5 + 2) = 3.

Example 2:
Input:
n = 4, d = 0 arr[] = {1, 1, 1, 1} 
Output: 6 

Your Task:
You don't have to read input or print anything. Your task is to complete the function countPartitions() which takes the integer n and d and array arr and returns the count of partitions.

Constraint:
1 <= n <= 500
0 <= d  <= 2500
0 <= arr[i] <= 50
Expected Time Complexity: O( N * MAX_SUM), where N and MAX_SUM denote the number of elements in the array and the maximum possible sum of array elements.
Expected Space Complexity: O( N * MAX_SUM), where N and MAX_SUM denote the number of elements in the array and the maximum possible sum of array elements.
*/

class Solution{
    
    int mod = (int)1e9 + 7;
    public int countPartitions(int n, int d, int arr[]){
        // Code here
        int sum = 0;
        for(int i = 0; i < n; i++){
            sum += arr[i];
        }
        /*
            s1 - s2 = target
            s1 + s2 = sum
            s2 = (sum - target) / 2;
        */
        if((sum - d) % 2 != 0 || (sum - d) < 0){
            return 0;
        }
        int t = (sum - d) / 2;
        int[][] dp = new int[n][t + 1];
	    for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], 0);
        }
        return findTargetSumWaysByTabulation(n, arr, t, dp);
    }
    
    public int findTargetSumWaysByTabulation(int n, int[] nums, int target, int[][] dp) {
        
        for(int j = 0; j <= target; j++){
            if(j == 0 && nums[0] == 0){
                dp[0][j] = 2;
            }
            else if(j == 0){
                dp[0][j] = 1;
            }
            else if(nums[0] == j){
                dp[0][j] = 1;
            }
            else{
                dp[0][j] = 0;
            }
        }
        for(int i = 1; i < n; i++){
            for(int j = 0; j <= target; j++){
                int pick = 0;
                if(j >= nums[i]){
                    pick = dp[i - 1][j - nums[i]];
                }
                int notPick = dp[i - 1][j];
                dp[i][j] = (pick + notPick) % mod;
            }
        }
        return dp[n - 1][target];
    }
}