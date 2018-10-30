package com;

import java.util.Arrays;
import java.util.Date;

public class Test4 {
    public static void main(String[] args) {
//        int[] a = new int[]{1,2,3,5,6};
//        System.out.println(Arrays.toString(a));
//        String str = Arrays.toString(a);
//        System.out.println(str.substring(1,str.length()-1));


        String str = String.valueOf(new Date().getTime());
        int len = str.length();
        String resultStr = str.substring(len-10);
        Integer id = Integer.valueOf(resultStr);
        System.out.println(id);
        System.out.println(Integer.MAX_VALUE - id);

    }
}
