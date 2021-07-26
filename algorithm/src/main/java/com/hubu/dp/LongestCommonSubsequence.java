package com.hubu.dp;
/**
 *
 *
 * 最长公共子序列
 */
public class LongestCommonSubsequence {
    public static void main(String[] args) {
        String text1 = "abc", text2 = "abc";


        System.out.println(longestCommonSubsequence(text1, text2));
    }
    public static int longestCommonSubsequence(String text1, String text2) {

        if(text1==null||text2==null||text1.isEmpty()||text2.isEmpty()){
            return 0;
        }
        byte[] t1 = text1.getBytes();
        byte[] t2 = text2.getBytes();
        int row=text1.length()+1;
        int column=text2.length()+1;
        int [][]dp=new int[row][column];
        for(int i=0;i<row;i++){
            dp[i][0]=0;
        }
        for(int j=0;j<column;j++){
            dp[0][j]=0;
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < column; j++) {
                if(t1[i-1]==t2[j-1]){
                    dp[i][j]=dp[i-1][j-1]+1;
                }
                else{
                    dp[i][j]=Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        return dp[row-1][column-1];
    }
}
