package com.gaussic.util.DataHandleUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PipeHandlerUtil {
    private static final int WINDOWS = 1;
    private float[] mTemp = null; // 只声明暂时不初始化,用来记录最后得不到均值处理的点
    private float[] mBufout = null;
    private int mWindowSize = WINDOWS;


    public PipeHandlerUtil(int size) {
        mWindowSize = size;
    }

    public PipeHandlerUtil() {

    }


    public static void main(String[] args) {
        float[] arr = new float[231];
        for(int i=0;i<231;i++){
            arr[i] = (float) (Math.random()*100f);
        }
        PipeHandlerUtil pipeHandlerUtil = new PipeHandlerUtil();
        float[] resultArr = pipeHandlerUtil.getHandleData(arr);
        System.out.println(resultArr.length);
    }
    public void filterData(float[] floats,float top,float low){
        //浓度数据
        float totalForDensity = 0f;
        long countForDensity = 0L;
        float avgForDensity = 0f;
        for(int i=0;i<floats.length;i++){
//            System.out.println("float:" +Arrays.toString(floats));
            if(floats[i]<low || floats[i] > top){
                //异常数据点
            }else{
                countForDensity ++;
                totalForDensity += floats[i];
            }
        }
        avgForDensity = totalForDensity / countForDensity;
//        System.out.println("百分比："+(float)countForDensity/(float)floats.length +","+avgForDensity);
        if(countForDensity > 0){

            if((float)countForDensity/(float)floats.length > 0.7) {
                //正常点数量超过80%
                avgForDensity = totalForDensity / countForDensity;
                for(int i=0;i<floats.length;i++){
                    if(floats[i]<low || floats[i] > top){
//                        System.out.println(floats[i] +", " + avgForDensity);
//                        floats[i] = avgForDensity;
                        if(i-1>0) {
                            floats[i] = floats[i - 1];
                        }else{
                            floats[i] = avgForDensity;
                        }
                    }else{

                    }
                }
            }
        }
    }
    /**
     * 处理一个浮点数组
     * @param floats
     * @return
     */
    public float[] getHandleData(float[] floats) {

        float[] resultArr = floats;
        try {
            if(floats.length > 230) {

                /*************************过滤数据，如果超出平均值50%，则为平均值*****************************************/
                float avg = 0f;
                float total = 0f;
                for(int i=0;i<floats.length;i++){
                    total += floats[i];
                }
                avg = total/floats.length;
                if(avg > 1000){
                    //浓度数据过滤
                    filterData(floats,50000000,5000);
                }else{
                    //风速数据过滤
//                    System.out.println("风速过滤" +",floats:"+floats.length);
                    filterData(floats,30,8);
                }
//        List<Float> floatList = new ArrayList<>();
//        for(int i=0;i<floats.length;i++){
//            float temp = floats[i] - avg;
//            float persent = Math.abs(temp/avg);
//            if(persent > 0.5){
//
//            }else{
//                floatList.add(floats[i]);
//            }
//        }
//        floats = new float[floatList.size()];
//        for(int i=0;i<floatList.size();i++){
//            floats[i] = floatList.get(i);
//        }
                /************************过滤数据，如果超出平均值50%，则为平均值*****************************************/

                float[] originalArr = Arrays.copyOfRange(floats, 0, 200);


                //PipeHandlerUtil pipeHandlerUtil = new PipeHandlerUtil(30);
                mWindowSize = 30;
                float[] result = this.movingAverageFilter(originalArr);
                List<Float> list = new ArrayList<>();
                for (float aResult : result) {
                    list.add(aResult);
                }
                int length = floats.length;
                int count = (length - 200) / 30;
//            count = 260;

                for (int m = 0; m < count; m++) {
                    float[] arr01 = Arrays.copyOfRange(floats, 200 + 30 * m, 200 + 30 * (m + 1));
                    changeData(arr01, originalArr);
                    float[] result01 = this.movingAverageFilter(originalArr);
                    for (int i = originalArr.length - arr01.length; i < result01.length; i++) {
                        list.add(result01[i]);
                    }
                }
                resultArr = new float[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    resultArr[i] = list.get(i);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultArr;

    }

    public static void changeData(float[] newArr, float[] originalArr) {
        for (int i = newArr.length; i < originalArr.length + newArr.length; i++) {
            if (i < originalArr.length) {
                originalArr[i - newArr.length] = originalArr[i];
            } else {
                originalArr[i - newArr.length] = newArr[i - originalArr.length];
            }

        }
    }

    public float mean(float[] array) {
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return ((float)sum) / array.length;
    }


    public float[] movingAverageFilter(float[] buf) {
        int bufoutSub = 0;
        int winArraySub = 0;

        float[] winArray = new float[mWindowSize];

        if (mTemp == null) {
            mBufout = new float[buf.length - mWindowSize + 1];
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
            mTemp = new float[mWindowSize - 1];
            System.arraycopy(buf, buf.length - mWindowSize + 1, mTemp, 0,
                    mWindowSize - 1);
            return mBufout;
        } else {
            float[] bufadd = new float[buf.length + mTemp.length];
            mBufout = new float[bufadd.length - mWindowSize + 1];
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
}
