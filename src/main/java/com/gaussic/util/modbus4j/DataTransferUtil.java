package com.gaussic.util.modbus4j;

/**
 * @ClassName DataTransferUtil
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/10/22 15:42
 * @Version 1.0
 */
public class DataTransferUtil {
    /**
     * @Description 将一个有符号byte输出为一个无符号的byte数据，例如：-128输出为128，-1输出为255
     * @Author jiangyong xia
     * @Date 10:34 18/10/23
     * @Param
     * @return
     **/
    public static Short converByteToShortFromHex(byte b){
        Short s = null;
        //1、将byte转换为16进制
        //2、将16进制数据转换为short数据
        String hexString = String.format("%x",b);
        s = Short.valueOf(hexString,16);
        System.out.println("byte数据：" + b + ",16进制数据：" +hexString + ",short数据:" + s);
        return s;
    }


    public static Integer converByteArraytoIntegerFromHex(byte heigh,byte low){
        return converByteArraytoIntegerFromHex(new byte[]{heigh,low});
    }
    /**
     * @Description 将2个有符号的byte数据转换为一个无符号的short数据，例如 -1 -1 输出为65535
     * @Author jiangyong xia
     * @Date 10:35 18/10/23
     * @Param
     * @return
     **/
    public static Integer converByteArraytoIntegerFromHex(byte[] byteArr){
        Integer i = null;
        String formatForHex = "%02x";
        if(null != byteArr && byteArr.length == 2){
            String hexString1 = String.format(formatForHex,byteArr[0]);
            String hexString2 = String.format(formatForHex,byteArr[1]);
            String hexString = hexString1 + hexString2;
            i = Integer.valueOf(hexString,16);
            System.out.println("byte数据:" + byteArr[0] + " "+byteArr[1] +",16进制数据：" + hexString +",int数据" + i);
        }
        return i;
    }

    /**
     * @Description 将一个整形数据，输出为两个无符号的short数据，例如65535，输出为 ff ff，short数组为 255,255
     * @Author jiangyong xia
     * @Date 10:36 18/10/23
     * @Param
     * @return
     **/
    public static short[] convertIntToShortArrayFormHex(int i){
        short[] shorts = new short[2];
        String hexString = String.format("%04x",i);
        String hexStr_1 = hexString.substring(0,2);
        String hexStr_2 = hexString.substring(2,4);
        shorts[0] = Short.valueOf(hexStr_1,16);
        shorts[1] = Short.valueOf(hexStr_2,16);
        System.out.println("int数据：" + i + ",十六进制数据：" +hexString + ",shorts：" + shorts[0] + " " + shorts[1]);
        return shorts;
    }
    /**
     * @Description 将一个整数数据，输出为两个byte数据，例如65535,16进制为 ff ff，byte数组为 -1 ，-1
     * @Author jiangyong xia
     * @Date 10:38 18/10/23
     * @Param
     * @return
     **/
    public static byte[] converIntToByteArrayFromHex(int i){
        byte[] bytes = new byte[2];
        String hexString = String.format("%04x",i);
        String hexStr_1 = hexString.substring(0,2);
        String hexStr_2 = hexString.substring(2,4);
        bytes[0] = Short.valueOf(hexStr_1,16).byteValue();
        bytes[1] = Short.valueOf(hexStr_2,16).byteValue();
        System.out.println("int数据：" + i + ",十六进制数据：" +hexString + ",bytes：" + bytes[0] + " " + bytes[1]);
        return bytes;
    }

    public static void testConverByteToShortFromHex(){
        //byte数据：0,16进制数据：0,short数据:0
        converByteToShortFromHex((byte) 0);
        //byte数据：5,16进制数据：5,short数据:5
        converByteToShortFromHex((byte) 5);
//        byte数据：126,16进制数据：7e,short数据:126
        converByteToShortFromHex((byte) 126);
//        byte数据：127,16进制数据：7f,short数据:127
        converByteToShortFromHex((byte) 127);

//        byte数据：-128,16进制数据：80,short数据:128
        converByteToShortFromHex((byte) -128);
//        byte数据：-5,16进制数据：fb,short数据:251
        converByteToShortFromHex((byte) -5);
//        byte数据：-1,16进制数据：ff,short数据:255
        converByteToShortFromHex((byte) -1);
    }

    public static void main(String[] args) {

//        testConverByteToShortFromHex();


/*        //byte数据：-56,16进制数据：c8,short数据:200
        converByteToShortFromHex((byte) 200);

        //byte数据:2 2,16进制数据：0202,int数据514
        converByteArraytoIntegerFromHex(new byte[]{(byte) 2, (byte) 2});
        //byte数据:-1 -1,16进制数据：ffff,int数据65535
        converByteArraytoIntegerFromHex(new byte[]{(byte) 255, (byte) 255});
        //byte数据:0 1,16进制数据：0001,int数据1
        converByteArraytoIntegerFromHex(new byte[]{(byte) 0, (byte) 257});
        //int数据：65535,十六进制数据：ffff,bytes：-1 -1
        converByteArraytoIntegerFromHex((byte)-1,(byte)-1);
        */


        //int数据：1,十六进制数据：0001,shorts：0 1
        convertIntToShortArrayFormHex(1);
        //int数据：129,十六进制数据：0081,shorts：0 129
        convertIntToShortArrayFormHex(129);
        //int数据：65535,十六进制数据：ffff,shorts：255 255
        convertIntToShortArrayFormHex(65535);


        //int数据：1,十六进制数据：0001,bytes：0 1
        converIntToByteArrayFromHex(1);
        //int数据：129,十六进制数据：0081,bytes：0 -127
        converIntToByteArrayFromHex(129);
        //int数据：65535,十六进制数据：ffff,bytes：-1 -1
        converIntToByteArrayFromHex(65535);


    }
}
