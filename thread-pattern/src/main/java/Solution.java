import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
class Solution {
    private List<List<Integer>> list=new ArrayList<>();
    private List<Integer> list2=new ArrayList<>();
    private boolean[] flag=null;
    public List<List<Integer>> permute(int[] nums) {
        flag=new boolean[nums.length];
        dfs(0,nums);
        return list;
    }
    public void dfs(int row,int array[]){
        //选择完毕
        if(row==array.length){
            List<Integer> ev=new ArrayList<>();
            for(Integer item:list2){
                ev.add(item);
            }
            list.add(ev);
            return ;
        }
        //枚举所有的情况
        for(int i=0;i<array.length;i++){
            //选择一种情况
            if(flag[i]){
                continue;
            }
            flag[i]=true;
            list2.add(array[i]);
            dfs(row+1,array);
            flag[i]=false;
            list2.remove(list2.size()-1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        int array[]={1,2,3,4,5,6,7,8};
        List<List<Integer>> permute = solution.permute(array);
        for(List<Integer> item:permute){
            for(Integer value:item){
                System.out.print(value);
            }
            System.out.println();
        }
    }
}