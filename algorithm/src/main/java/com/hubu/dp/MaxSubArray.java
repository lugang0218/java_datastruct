package com.hubu.dp;


/**
 *
 *
 *
 * 对打连续子序列的和
 */
/**
 * 示例 1：
 *
 * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出：6
 * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
 */
public class MaxSubArray {
    public static void main(String[] args) {
        int numbers[]=new int[]{-2,1,-3,4,-1,2,1,-5,4};
        int maxValue=getMax(numbers);
        System.out.println("最大值是:"+maxValue);
    }

    public static int getMax(int numbers[]){
        int []dp=new int[numbers.length];
        /**
         *
         *
         * 原问题:求最大连续子序列的和
         * 求解思路:设dp[i]表示以i结尾的最大连续子序列的和
         * 如dp[0]表示以0结尾的最大和
         * dp[1]表示以1结尾的最大和,
         * 只需要求解出所有最大和中的最大值
         *
         * 状态转移方程 dp[i]=max(dp[i-1]+numbers[i],numbers[i])
         */

        dp[0]=numbers[0];

        for(int i=1;i<dp.length;i++){
            dp[i]=Math.max(dp[i-1]+numbers[i],numbers[i]);
        }

        int maxValue=dp[0];
        for(int i=1;i<dp.length;i++){
            if(dp[i]>maxValue){
                maxValue=dp[i];
            }
        }
        return maxValue;
    }
}
