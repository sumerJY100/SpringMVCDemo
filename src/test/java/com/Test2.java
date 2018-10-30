package com;

public class Test2 {
    public static void main(String[] args) {
        /*String hex = "ffdd";
        Integer i = Integer.valueOf(hex,16);
        System.out.println(i);


        hex = "fdd";

        Short s = Short.valueOf(hex,16);

//        Short.parseShort()
        System.out.println(s);

        String hexStr = String.format("%x",(short)-35);
        System.out.println(hexStr);


        hex = "ffdd";
        Integer i2 = Integer.valueOf(hex,16);
        Integer i3 = i2 - Short.MAX_VALUE;
        String hexStr2 = String.format("%x",i3.shortValue());
        System.out.println(hexStr2);
        System.out.println(Integer.valueOf(hexStr2,16));
        Integer i4 = Integer.valueOf(hexStr2,16);
        Integer i5 = i4* -1;
        System.out.println(String.format("%x",i5.shortValue()));*/


     /*   String hex = "ffdd";
        Integer i = Integer.valueOf(hex,16);
        Integer.toBinaryString(i);
        int s = (Short.MAX_VALUE +1) ^ Short.MAX_VALUE;
        System.out.println(s);

        int s1 = (Short.MAX_VALUE +1) ^ Short.MAX_VALUE;
        System.out.println(s1);*/


       /* String hex = "ffff";
        Integer i = Integer.valueOf(hex,16);
        String binaryString = Integer.toBinaryString(i);
        System.out.println(binaryString);

        if(binaryString.length() >=16){
            String leftBinaryString = binaryString.substring(1);
            Short a = Short.valueOf(leftBinaryString,2);
            System.out.println(a - Short.MAX_VALUE -1);
        }else{
            Short a = Short.valueOf(binaryString,2);
            System.out.println(a);
        }


        System.out.println((short)65535);*/



        String str = "01 16 0 0 0 100 200 0 0 1 36 1 105 0 0 0 190 0 40 0 116 0 245 0 35 0 109 0 6 0 0 0 5 1 114 0 1 1 72 0 0 0 0 1 52 0 0 1 58 0 0 0 0 0 0 0 0 0 0 0 0 1 54 0 1 1 76 0 170 0 0 1 78 0 0 0 10 0 10 1 4 1 47 0 222 0 64 0 226 0 75 0 84 0 84 0 61 0 64 0 33 0 34 0 179 0 189 0 8 0 8 0 8 0 8 0 19 0 69 0 22 0 68 0 33 0 34 0 179 0 179 0 10 3 229 3 231 3 193 38 107 38 179 13 249 14 176 3 24 0 163 0 149 2 145 0 0 2 144 0 0 0 0 2 50 10 151 1 60 1 198 40 180 40 91 38 252 38 186 19 186 19 173 20 4 20 30 1 195 1 77 0 253 0 0 0 100 0 0 0 0 0 0 39 80 38 187 68 124";
        String[] arr = str.split(" ");
        System.out.println(arr.length);
        for(int i=0;i<arr.length;i++){
            Integer integer = Integer.valueOf(arr[i]);
            String hex = String.format("%02x",integer);
            System.out.print(hex + " ");
        }
    }
}
