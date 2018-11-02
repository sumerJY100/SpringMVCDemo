package com.gaussic.util.DataHandleUtil;


import org.hibernate.boot.jaxb.SourceType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 功能 对音频数据进行滑动滤波，使其更好的识别  时间：2015/9/11
 */
//implements IAudioSignalFilter
public class PipeDensityHandler {
    private static final int WINDOWS = 1;
    private int[] mTemp = null; // 只声明暂时不初始化,用来记录最后得不到均值处理的点
    private int[] mBufout = null;
    private int mWindowSize = WINDOWS;

    public static void main(String[] args) {
       String str = UtilData.str01;
        String[] strArr = str.split("\\n");
        int[] intArr = new int[strArr.length];
        for(int i=0;i<strArr.length;i++){
            intArr[i] = Integer.parseInt(strArr[i]);
        }
        int[] originalArr = Arrays.copyOfRange(intArr,0,200);


        PipeDensityHandler pipeDensityHandler = new PipeDensityHandler(30);
        int[] result = pipeDensityHandler.movingAverageFilter(originalArr);
        List<Integer> list = new ArrayList<>();
        for (int aResult : result) {
            list.add(aResult);
        }
        for(int m=0;m<29;m++) {
            int[] arr01 = Arrays.copyOfRange(intArr, 200+30*m, 200+30*(m+1));
            changeData(arr01, originalArr);
            int[] result01 = pipeDensityHandler.movingAverageFilter(originalArr);
            for (int i = originalArr.length - arr01.length; i < result01.length; i++) {
                list.add(result01[i]);
            }
        }

        System.out.println(list.size());

        list.forEach((l)->{
            System.out.print(l + " ");
        });

    }

    public static void test2(){

        String a = "15061550\t14344850\t11521450\t14801900\t8786210\t15910950\t12780900\t20450150\t13718200\t11804650\t21814100\t14700350\t21660550\t14661500\t13140200\t13715000\t12946050\t9782900\t16450200\t11430850\t19461850\t18027600\t15556550\t13524550\t12919400\t11338300\t16318300";
        String[] strArr = a.split("\\t");
//        System.out.println(Arrays.toString(strArr));
        int[] arr = new int[strArr.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        PipeDensityHandler pipeDensityHandler = new PipeDensityHandler(10);

        String b = "22568150\n20869400\n12107500\n14511000\n14295250\n15598200\n7916130\n9907435\n6938670\n12055550\n";
        changeData(b,arr);
        String c = "6234810\n6927865\n22074200\n10702110\n14315900\n18433650\n16293600\n11753250\n14717950\n13724400\n";
        changeData(c,arr);
        String d = "14244800\n16502900\n13237600\n16331600\n11613300\n10027525\n14035100\n17039950\n8375275\n16007950\n";

        changeData(d,arr);
        int[] result = pipeDensityHandler.movingAverageFilter(arr);
        System.out.println(Arrays.toString(result));
    }
    public static void changeData(String b,int[] arr){
        String[] bArr = b.split("\\n");
        int[] bIntArr = new int[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bIntArr[i] = Integer.parseInt(bArr[i]);
        }

        //处理原来的数据

        changeData(bIntArr,arr);
    }
    public static void changeData(int[] newArr,int[] originalArr){
        for (int i = newArr.length; i < originalArr.length + newArr.length; i++) {
            if (i < originalArr.length) {
                originalArr[i - newArr.length] = originalArr[i ];
            } else  {
                originalArr[i - newArr.length] = newArr[i - originalArr.length];
            }

        }
    }


    public static void test1() {
        /*  PipeDensityHandler pipeDensityHandler = new PipeDensityHandler(200);
        int[] arr = new int[1000];
        for(int i=0;i<1000;i++){
            int random = (int) (Math.random() * 10);
            if(random > 5 && random <= 7 ){
                arr[i] = (int)(Math.random() * 30 + 100);
            }else if(random>7 && random <= 8){
                arr[i] = (int)(Math.random() * 50 + 100);
            }else if(random>8){
                arr[i] = (int)(Math.random() * -30 + 100);
            }else {
                arr[i] = (int) (Math.random() * -10 + 100);
            }
        }
        String s1 = Arrays.toString(arr);
        int[] resultArr = pipeDensityHandler.movingAverageFilter(arr);
        String s2 = Arrays.toString(resultArr);

        System.out.println(arr.length + "," + resultArr.length);

        System.out.println(s1);
        System.out.println(s2);*/
    }


    public PipeDensityHandler(int size) {
        mWindowSize = size;
    }

    // 均值滤波方法，输入一个buf数组，返回一个buf1数组，两者下表不一样，所以定义不同的下表，buf的下表为i，buf1的下表为buf1Sub.
    // 同理，临时的winArray数组下表为winArraySub
    public int[] movingAverageFilter(int[] buf) {
        int bufoutSub = 0;
        int winArraySub = 0;

        int[] winArray = new int[mWindowSize];

        if (mTemp == null) {
            mBufout = new int[buf.length - mWindowSize + 1];
            for (int i = 0; i < buf.length; i++) {
                if ((i + mWindowSize) > buf.length) {
                    break;
                } else {
                    for (int j = i; j < (mWindowSize + i); j++) {
                        winArray[winArraySub] = buf[j];
                        winArraySub = winArraySub + 1;
                    }

                    mBufout[bufoutSub] = mean(winArray);
                    bufoutSub = bufoutSub + 1;
                    winArraySub = 0;
                }
            }
            mTemp = new int[mWindowSize - 1];
            System.arraycopy(buf, buf.length - mWindowSize + 1, mTemp, 0,
                    mWindowSize - 1);
            return mBufout;
        } else {
            int[] bufadd = new int[buf.length + mTemp.length];
            mBufout = new int[bufadd.length - mWindowSize + 1];
            System.arraycopy(mTemp, 0, bufadd, 0, mTemp.length);
            System.arraycopy(buf, 0, bufadd, mTemp.length, buf.length); // 将temp和buf拼接到一块
            for (int i = 0; i < bufadd.length; i++) {
                if ((i + mWindowSize) > bufadd.length)
                    break;
                else {
                    for (int j = i; j < (mWindowSize + i); j++) {
                        winArray[winArraySub] = bufadd[j];
                        winArraySub = winArraySub + 1;
                    }
                    mBufout[bufoutSub] = mean(winArray);
                    bufoutSub = bufoutSub + 1;
                    winArraySub = 0;
                    System.arraycopy(bufadd, bufadd.length - mWindowSize + 1,
                            mTemp, 0, mWindowSize - 1);
                }
            }
            return mBufout;
        }
    }

    public int mean(int[] array) {
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return (int) (sum / array.length);
    }
}

/*作者：张家小2
        链接：https://www.jianshu.com/p/01bed900a1ee
        來源：简书
        简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。*/
