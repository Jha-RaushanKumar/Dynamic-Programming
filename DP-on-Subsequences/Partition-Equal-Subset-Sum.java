/*
416. Partition Equal Subset Sum
https://leetcode.com/problems/partition-equal-subset-sum/

Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise.

Example 1:
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].

Example 2:
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.
 
Constraints:
1 <= nums.length <= 200
1 <= nums[i] <= 100
*/

class Solution {
    public boolean canPartition(int[] nums) {
        
        int n = nums.length;
        int sum = 0;
        for(int i = 0; i < n; i++){
            sum += nums[i];
        }
        if(sum % 2 != 0){
            return false;
        }
        int target = sum / 2;
        //return canPartitionByRecursion(n - 1, nums, target);
        boolean[][] dp = new boolean[n][target + 1];
        return canPartitionByTabulation(n, nums, target, dp);
    }

    /*  
        Method 01:
        TC: O(2^(N*k)) SC: O(M+k)
    */
    public boolean canPartitionByRecursion(int i, int[] nums, int target) {
        
        if(target == 0){
            return false;
        }
        if(i == 0){
            if(nums[i] == target){
                return true;
            }
            return false;
        }
        boolean pick = canPartitionByRecursion(i - 1, nums, target - nums[i]);
        boolean notPick = canPartitionByRecursion(i - 1, nums, target);
        return pick || notPick;
    }

    /*  
        Method 02:
        TC: O(N*k)+O(N) SC: O(N*k)[no rec stack space, only dp array]
    */
    public boolean canPartitionByTabulation(int n, int[] nums, int target, boolean[][] dp) {
        
        for(int i = 0; i < n; i++){
            dp[i][0] = true;
        }
        if(nums[0] == target){
            dp[0][nums[0]] = true;
        }
        for(int i = 1; i < n; i++){
            for(int j = 1; j <= target; j++){
                boolean pick = false;
                if(j >= nums[i]){
                    pick = dp[i - 1][j - nums[i]];
                }
                boolean notPick = dp[i - 1][j];
                dp[i][j] = pick || notPick;
            }
        }
        return dp[n - 1][target];
    }
}