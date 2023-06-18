/*
198. House Robber
https://leetcode.com/problems/house-robber/

You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.

Example 1:
Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
Total amount you can rob = 1 + 3 = 4.

Example 2:
Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
Total amount you can rob = 2 + 9 + 1 = 12.

Constraints:
1 <= nums.length <= 100
0 <= nums[i] <= 400
*/

class Solution {
    public int rob(int[] nums) {
        
        int n = nums.length;
        //return robByRecursion(n - 1, nums);
        /*
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return robByMemoization(n - 1, nums, dp);
        */
        /*
        int[] dp = new int[n + 1];
        Arrays.fill(dp, 0);
        return robByTabulation(n, nums, dp);
        */
        return robByTabulationOptimized(n, nums);
    }

    /*  
        Method 01:
        TC: O(2^N) SC: O(N)
    */
    public int robByRecursion(int i, int[] nums) {
        
        if(i < 0){
            return 0;
        }
        if(i == 0){
            return nums[i];
        }
        int take = nums[i] + robByRecursion(i - 2, nums);
        int notTake = robByRecursion(i - 1, nums);
        return Math.max(take, notTake);
    }

    /* 
        Method 02:
        TC: O(N) SC: O(2N) ~ O(N)[Rec stack space + dp array]
    */
    public int robByMemoization(int i, int[] nums, int[] dp) {
        
        if(i < 0){
            return 0;
        }
        if(i == 0){
            return nums[i];
        }
        if(dp[i] != -1){
            return dp[i];
        }
        int take = nums[i] + robByMemoization(i - 2, nums, dp);
        int notTake = robByMemoization(i - 1, nums, dp);
        return dp[i] = Math.max(take, notTake);
    }

    /*
        Method 03:
        TC: O(N) SC: O(N) [Only dp array, no stack space]
    */
    public int robByTabulation(int n, int[] nums, int[] dp) {
        
        dp[0] = 0;
        dp[1] = nums[0];
        for(int i = 2; i <= n; i++){
            int take = nums[i - 1] + dp[i - 2];
            int notTake = dp[i - 1];
            dp[i] = Math.max(take, notTake);
        }
        return dp[n];
    }

    /*
        Method 04:
        TC: O(N) SC: O(1) [no dp array, no stack space]
    */
    public int robByTabulationOptimized(int n, int[] nums) {
        
        int prev2 = 0;
        int prev1 = nums[0];
        for(int i = 2; i <= n; i++){
            int take = nums[i - 1] + prev2;
            int notTake = prev1;
            int curr = Math.max(take, notTake);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }
}