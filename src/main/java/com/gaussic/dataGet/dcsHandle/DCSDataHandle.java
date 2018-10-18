package com.gaussic.dataGet.dcsHandle;

public class DCSDataHandle {
    public static  short[] sendData(float v){

        Float resultF = (v + 5 ) * 100;

/*//        byte[] bytes = resultF.byteValue();
//        resultF.
        Integer integer = resultF.intValue();
        byte[] bytes = new byte[4];
        for(int i=0;i<bytes.length;i++){
            bytes[i] = new Integer(integer & 0xff).byteValue();
            integer = integer>>8;
        }
//        for(int i=0;i<bytes.length;i++){
//            System.out.print( " " + bytes[i]);
//        }
        return resultF.shortValue();
        */
        return sendIntValueToShortArray(resultF.intValue());
    }
    public static short[] sendIntValueToShortArray(Integer v){
        short[] shortArr = new short[2];
        int low = v&0xFF;
        int hight = (v>>8)&0xff;
        shortArr[0] = (short) hight;
        shortArr[1] = (short) low;
        return shortArr;
    }

    public static void main(String[] args) {
        sendData(128f);
        Integer a = 64;
        Integer b = a>>3;
        System.out.println();
        System.out.println(new Integer(1).byteValue());
        System.out.println(new Integer(2).byteValue());
        System.out.println(new Integer(4).byteValue());
        System.out.println(new Integer(5).byteValue());
        System.out.println(new Integer(6).byteValue());
        System.out.println(new Integer(7).byteValue());
        System.out.println(new Integer(8).byteValue());
        System.out.println(new Integer(9).byteValue());
        System.out.println(new Integer(10).byteValue());
        System.out.println(new Integer(20).byteValue());
        System.out.println(new Integer(100).byteValue());
        System.out.println(new Integer(127).byteValue());
        System.out.println("b:" + b);
//        System.out.println(sendData(1));

        System.out.println(Integer.MAX_VALUE);
    }

    public   static  Float getData(int v){
        Float result = 0f;
        Integer integer = v/100 - 5;
        result = integer.floatValue();
        return result;
    }

}
