package com.wxy.wjl.testspringboot2.sort;

/**
 * 快速排序
 */

public class QuickSort {

    public static void main(String[] args) {
        int[] arr=new int[]{3,2,5,9,2,1,0,4};
        quicksort(arr,0,arr.length-1);
        for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }



    public static void quicksort(int[] arr, int low, int high) {
        int i,j,temp,t;

        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];
        while (i<j){
            //先看右边，依次往左递减
            while (temp<=arr[j]&&i<j){
                j--;
            }
            //再看左边，依次往右递增
            while (temp>=arr[i]&&i<j){
                i++;
            }
            //如果满足条件则交换
            if (i<j){
                t=arr[j];
                arr[j]=arr[i];
                arr[i]=t;
            }
        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quicksort(arr, low, j-1);
        //递归调用右半数组
        quicksort(arr, j+1, high);

    }
}
