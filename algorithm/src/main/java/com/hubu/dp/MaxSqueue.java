package com.hubu.dp;
import java.util.Arrays;
/**
 *
 *
 * 数组的最长上升子序列
 */
public class MaxSqueue {
    public static void main(String[] args) {
        int []array=new int[]{0,1,0,3,2,3};
        System.out.println(getMax(array));
    }
    /**
     * dp[i]表示包含i的最长子序列
     * @param nums
     * @return
     */


    //todo 将最长的子序列求解返回
    public static int getMax(int []nums){
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = dp[0];
        for(int i = 1;i < nums.length;i++) {
            //先初始化dp[i]=1,表示不可以接上去
            dp[i] = 1;
            for(int j = 0;j <= i-1;j++) {
                if(nums[i]<=nums[j])continue;

                //如果可以接到后面，就直接接到后面
                if(nums[i]>nums[j]){
                    //获取当前的值
                    int maxValue=dp[j]+1;

                    //如果当前的值比dp[i]要大，更新dp[i]的值
                    if(maxValue>dp[i]){
                        dp[i]=maxValue;
                    }
                }
            }
            //更新max的值，最终返回max
            if(max < dp[i]){
                max = dp[i];
            }
        }
        System.out.println(Arrays.toString(dp));
        return max;
    }
}
