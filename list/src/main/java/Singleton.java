
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Singleton {
    private static Singleton singleton=null;
    private static  Object lock=new Object();
    public static Singleton getInstance(){
        synchronized (lock){
            if(singleton==null){
                singleton=new Singleton();
            }
        }
        return singleton;
    }
    public static int majorityElement(int[] nums) {
        Map<Integer,Integer> map=new HashMap<>();
        int count=0;
        int value=0;
        int max=-1;
        for(int i=0;i<nums.length;i++){
            if(map.get(nums[i])==null){
            }
            for(int j=0;j<nums.length;j++){
                 if(nums[i]==nums[j]){
                    count++;
                }
            }
            if(count>max){
                max=count;
                if(max>nums.length/2){
                    value=nums[i];
                    return value;
                }
                value=nums[i];
            }
            count=0;
        }
        return value;
    }
    public static int[] plusOne(int[] digits) {
        List<Integer> result=new ArrayList<>();
        int m=0;
        int r=1;//
        for(int i=digits.length-1;i>=0;i--){
            m=(digits[i]+r)%10;
            r=(digits[i]+r)/10;
            result.add(0,m);
        }
        if(r>0){
            result.add(0,r);
        }
        int array[]=new int[result.size()];
        for(int i=0;i<array.length;i++){
            array[i]=result.get(i);
        }
        return array;
    }


    /**
     * 合并两个有序数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        //输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
        //输出：[1,2,2,3,5,6]
        int l=m-1;
        int r=n-1;
        int k=m+n-1;
        while(l>=0&&r>=0){
            if(nums1[l]<=nums2[r]){
                nums1[k]=nums2[r];
                k--;
                r--;
            }
            else{
                nums1[k]=nums1[l];
                k--;
                l--;
            }
        }

        while(r>=0){
            nums1[k]=nums2[r];
            k--;
            r--;
        }
        while(l>=0){
            nums1[k]=nums1[l];
            k--;
            l--;
        }
    }

    public static int searchInsert(int[] nums, int target) {
        int l=0;
        int r=nums.length-1;
        int index=0;
        boolean equal=false;
        while(l<=r){
            int mid=(l+r)/2;
            if(nums[mid]>target){
                r=mid-1;
            }
            else if(nums[mid]==target){
                index=mid;
                equal=true;
                break;
            }
            else{
                l=mid+1;
            }
        }
        if(equal==false){
            index=l;
        }
        return index;
    }


    public static int[] twoSum(int[] nums, int target) {
        int array[] = new int[2];
        Map<Integer,Integer> map=new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            //将数据作为键 索引作为value
            int value=target-nums[i];
            Integer r=map.get(value);
            if(r!=null&&r!=i){
                array[0]=i;
                array[1]=r;
                return array;
            }
            else{
                map.put(nums[i],i);
            }
        }
        return null;
    }

    public static double test(int n) {
        double c = 0.00001;
        double left = 0;
        double right = n;
        double mid = 0;
        mid = (left + right) / 2;
        while (true) {
            if (Math.abs(mid * mid - n) <= c) {
                break;
            } else if (mid * mid < n) {
                left = mid;
                mid = (mid + right) / 2;
            } else if (mid * mid > n) {
                right = mid;
                mid = (left + mid) / 2;
            }
        }
        return mid;
    }
    public static void main(String[] args) {
        int []nums1=new int[]{8,8,7,7,7};
        System.out.println(majorityElement(nums1));
    }
}
