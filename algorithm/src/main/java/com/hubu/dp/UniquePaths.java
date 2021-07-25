package com.hubu.dp;
/**
 *
 *
 *
 * 求解从(0,0)走到(m,n)的路径总数
 */
public class UniquePaths {


    public static void main(String[] args) {
        int result=uniquePaths(1,10);
        System.out.println(result);


        int [][]array=new int [][]{{0},{1}};//{0,1,0},{0,0,0}

        int result2=uniquePaths2(array);
        System.out.println(result2);
    }
    /**
     *
     * @param m 第m行
     * @param n 第n列
     * @return
     */
    public static int uniquePaths(int m,int n){
        if(m==1&&n==1){
            return 1;
        }
        int [][]array=new int[m][n];
        for(int i=0;i<m;i++){
            array[i][0]=1;
        }
        for(int j=0;j<n;j++){
            array[0][j]=1;
        }
        array[0][0]=0;
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                array[i][j]=array[i-1][j]+array[i][j-1];
            }
        }
        int res=array[m-1][n-1];
        return res;
    }


    /**
     * 有障碍物的版本
     * @return
     */
    public static int uniquePaths2(int[][] obstacleGrid){
        //处理边界情况
        // obstacleGrid[i][j]==1表示第i行第j列有障碍物 ==0表示可以走
        int row=obstacleGrid.length;
        int column=obstacleGrid[0].length;
        int [][]dp=new int [row][column];
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                if(obstacleGrid[i][j]==1){
                    dp[i][j]=-1;//表示当前位置不可走
                }
            }
        }
        for(int i=0;i<dp.length;i++){
            if(dp[i][0]!=-1) dp[i][0]=1;
        }
        for(int j=0;j<dp[0].length;j++){

            if(dp[0][j]!=-1) dp[0][j]=1;
        }

        //处理边界情况
        //如果只有一行
        if(row==1) {
            for (int i = 0; i < column; i++) {
                //只要有障碍物，就返回0
                if (dp[row - 1][i] == -1) {
                    return 0;
                }

            }
            return 1;
        }
        //如果只有一列
        else if(column==1){
            //如果只有一列
            for (int i = 0; i < row; i++) {
                //只要有障碍物，就返回0
                if (dp[i][column-1] == -1) {
                    return 0;
                }

            }
            return 1;
        }
        else if(dp[0][0]==-1){
            return 0;
        }
        for(int i=1;i<row;i++){
            for(int j=1;j<column;j++){
                //如果当前格子可以周
                if(dp[i][j]!=-1){
                    int preRow=dp[i-1][j]==-1?0:dp[i-1][j];
                    int prwColumn=dp[i][j-1]==-1?0:dp[i][j-1];
                    dp[i][j]=preRow+prwColumn;
                }

                //如果最后一个位置为-1，也表示可以走到
                else{
                    if(i==row-1&&j==column-1){
                        int preRow=dp[i-1][j]==-1?0:dp[i-1][j];
                        int prwColumn=dp[i][j-1]==-1?0:dp[i][j-1];
                        dp[i][j]=0;
                    }
                }
            }
        }
        return dp[row-1][column-1];
    }
}
