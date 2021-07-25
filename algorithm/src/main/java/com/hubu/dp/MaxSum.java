package com.hubu.dp;
/**
 * 最小矩阵和
 */
public class MaxSum {
    public static void main(String[] args) {
        int [][]array=new int [][]{{1,2,3},{3,4,5},{2342,3,5}};
        System.out.println(getMinValue(array));
    }
    public static int getMinValue(int grid[][]){
        int [][]dataArray=new int [grid.length][grid[0].length];
        dataArray[0][0]=grid[0][0];
        for(int i=1;i<grid.length;i++){
            dataArray[i][0]=grid[i][0];
            dataArray[i][dataArray[0].length-1]=grid[i][grid[0].length-1];
        }
        for(int j=1;j<grid[0].length;j++){
            dataArray[0][j]=grid[0][j];
            dataArray[dataArray.length-1][j]=grid[grid.length-1][j];
        }
        for(int i=1;i<grid.length;i++){
            dataArray[i][0]=dataArray[i-1][0]+dataArray[i][0];
        }
        for(int j=1;j<grid[0].length;j++){
            dataArray[0][j]=dataArray[0][j-1]+dataArray[0][j];
        }
        for(int i=1;i<dataArray.length;i++){
            for(int j=1;j<dataArray[0].length;j++){
                dataArray[i][j]=Math.min(dataArray[i-1][j],dataArray[i][j-1])+grid[i][j];
            }
        }
        return dataArray[dataArray.length-1][dataArray[0].length-1];
    }
}
