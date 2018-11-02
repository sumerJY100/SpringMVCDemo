package com.gaussic.util.DataHandleUtil;

import java.util.Arrays;

public class Util2 {


    public static void main(String[] args) {
        double[] doubles = new double[100];
        for(int i=0;i<doubles.length;i++){
            if(i%15 == 0){
                doubles[i] = Math.random() * 10 + 200;
            }else{
                doubles[i] = Math.random() * 10 + 100;
            }
        }
        double[] results = IIRFilter(doubles);
        System.out.println(Arrays.toString(doubles));
        System.out.println(Arrays.toString(results));
    }


    private static double[]             b; // {使用MATLAB生成的参数};

    private static double[]             in;
    private static double[]             outData;



    public static double[] IIRFilter (double[] signal) {
        in=new double[b.length];

        outData=new double[signal.length];
        for (int i = 0; i < signal.length; i++) {

            System.arraycopy(in, 0, in, 1, in.length - 1);  //in[1]=in[0],in[2]=in[1]...
            in[0] = signal[i];

            //calculate y based on a and b coefficients
            //and in and out.
            double y = 0;
            for(int j = 0 ; j < b.length ; j++){
                y += b[j] * in[j];

            }

            outData[i] = y;

        }
        return outData;
    }

/*---------------------
    作者：mmmmayi
    来源：CSDN
    原文：https://blog.csdn.net/m0_37648273/article/details/75808829
    版权声明：本文为博主原创文章，转载请附上博文链接！*/
}
