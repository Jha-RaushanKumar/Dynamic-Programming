/*
494. Target Sum
https://leetcode.com/problems/target-sum/

You are given an integer array nums and an integer target.
You want to build an expression out of nums by adding one of the symbols '+' and '-' before each integer in nums and then concatenate all the integers.
For example, if nums = [2, 1], you can add a '+' before 2 and a '-' before 1 and concatenate them to build the expression "+2-1".
Return the number of different expressions that you can build, which evaluates to target.

Example 1:
Input: nums = [1,1,1,1,1], target = 3
Output: 5
Explanation: There are 5 ways to assign symbols to make the sum of nums be target 3.
-1 + 1 + 1 + 1 + 1 = 3
+1 - 1 + 1 + 1 + 1 = 3
+1 + 1 - 1 + 1 + 1 = 3
+1 + 1 + 1 - 1 + 1 = 3
+1 + 1 + 1 + 1 - 1 = 3

Example 2:
Input: nums = [1], target = 1
Output: 1
 
Constraints:
1 <= nums.length <= 20
0 <= nums[i] <= 1000
0 <= sum(nums[i]) <= 1000
-1000 <= target <= 1000
*/

class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        
        int n = nums.length;
        int sum = 0;
        for(int i = 0; i < n; i++){
            sum += nums[i];
        }
        /*
            s1 - s2 = target
            s1 + s2 = sum
            s2 = (sum - target) / 2;
        */
        if((sum - target) % 2 != 0 || (sum - target) < 0){
            return 0;
        }
        int t = (sum - target) / 2;
        //return findTargetSumWaysByRecursion(n - 1, nums, t);
        /*
        int[][] dp = new int[n][t + 1];
	    for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], -1);
        }
        return findTargetSumWaysByMemoization(n - 1, nums, t, dp);
        */
        /*
        int[][] dp = new int[n][t + 1];
	    for(int i = 0; i < n; i++){
            Arrays.fill(dp[i], 0);
        }
        return findTargetSumWaysByTabulation(n, nums, t, dp);
        */
        return findTargetSumWaysByTabulationOptimized(n, nums, t);
    }

    /*  
        Method 01:
        TC: O(2^(N*k)) SC: O(N+k)
    */
    public int findTargetSumWaysByRecursion(int i, int[] nums, int target) {
        
        if(i == 0){
            if(target == 0 && nums[i] == 0){
                return 2;
            }
            if(target == 0){
                return 1;
            }
            if(nums[i] == target){
                return 1;
            }
            return 0;
        }
        int pick = 0;
        if(target >= nums[i]){
            pick = findTargetSumWaysByRecursion(i- 1, nums, target - nums[i]);
        }
        int notPick = findTargetSumWaysByRecursion(i- 1, nums, target);
        return pick + notPick;
    }

    /*  
        Method 02:
        TC: O(N*k) SC: O(N)+O(M*k)[path length and stack space]
    */
    public int findTargetSumWaysByMemoization(int i, int[] nums, int target, int[][] dp) {
        
        if(i == 0){
            if(target == 0 && nums[i] == 0){
                return 2;
            }
            if(target == 0){
                return 1;
            }
            if(nums[i] == target){
                return 1;
            }
            return 0;
        }
        if(dp[i][target] != -1){
            return dp[i][target];
        }
        int pick = 0;
        if(target >= nums[i]){
            pick = findTargetSumWaysByMemoization(i- 1, nums, target - nums[i], dp);
        }
        int notPick = findTargetSumWaysByMemoization(i- 1, nums, target, dp);
        return dp[i][target] = pick + notPick;
    }

    /*  
        Method 03:
        TC: O(N*k) SC: O(N*k)[no rec stack space, only dp array]
    */
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
                dp[i][j] = pick + notPick;
            }
        }
        return dp[n - 1][target];
    }

    /*  
        Method 04:
        TC: O(N*k) SC: O(k)[no rec stack space, only dp array]
    */
    public int findTargetSumWaysByTabulationOptimized(int n, int[] nums, int target) {
        
        int[] prev = new int[target + 1];
        for(int j = 0; j <= target; j++){
            if(j == 0 && nums[0] == 0){
                prev[j] = 2;
            }
            else if(j == 0){
                prev[j] = 1;
            }
            else if(nums[0] == j){
                prev[j] = 1;
            }
            else{
                prev[j] = 0;
            }
        }
        for(int i = 1; i < n; i++){
            int[] curr = new int[target + 1];
            for(int j = 0; j <= target; j++){
                int pick = 0;
                if(j >= nums[i]){
                    pick = prev[j - nums[i]];
                }
                int notPick = prev[j];
                curr[j] = pick + notPick;
            }
            prev = curr;
        }
        return prev[target];
    }
}