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

    public float[] getHandleData(float[] floats) {
        System.out.println(floats.length);
        float[] resultArr = null;
        try {
            float[] originalArr = Arrays.copyOfRange(floats, 0, 200);


            //PipeHandlerUtil pipeHandlerUtil = new PipeHandlerUtil(30);
            mWindowSize = 30;
            float[] result = this.movingAverageFilter(originalArr);
            List<Float> list = new ArrayList<>();
            for (float aResult : result) {
                list.add(aResult);
            }
            int length = floats.length;
            int count = (length - 200)/30 -2    ;
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
        return sum / array.length;
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
