package com.ebercruz.myrlux.back.algorit;

public class MergeSortExample {

    public static void main (String [] args){
        int [] arr = {5,4,1,8,7,2,6,3};
        printArray(arr);
        mergeSort(arr, 0, arr.length - 1);

    }

    public static void printArray(int [] arr){

        for ( int value : arr){
            System.out.println("Pintando valor : " + value + " ");
        }
    }

    public static  void mergeSort(int [] arr, int left, int right){

        if(left<right){
            //encontrar el punto medio
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);

            mergeSort(arr, mid+1, right);

            // conbina las dos mitades
            // merge (arr, left, mid, right);


        }

    }

    public static void  merge(int [] arr, int left, int mid, int right){

        

    }
}
