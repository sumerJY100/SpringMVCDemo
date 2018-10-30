package com.dcs;

import com.gaussic.dataGet.dcsHandle.DCSDataHandle;
import com.gaussic.util.modbus4j.RtuSerialPortWrapper;
import com.serotonin.modbus4j.*;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;
import com.serotonin.modbus4j.exception.ModbusInitException;

/**
 * @ClassName Slave1Test
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/9/20 9:26
 * @Version 1.0
 */
public class Slave2Test extends SlaveTest{


    /**
     * 主站读取与写入的数据，需要全部初始化
     */
    private static final int slaveId = 1,
            startOffset = 0,
            dataSize = 1000;


    public static void main(String[] args) {

        RtuSerialPortWrapper wrapper = getWrapper();
        ModbusSlaveSet rtuSlave = new ModbusFactory().createRtuSlave(wrapper);

        BasicProcessImage processImage2 = generatorProcessImage(startOffset, dataSize);

//        rtuSlave.addProcessImage(processImage1);
        rtuSlave.addProcessImage(processImage2);

        new Thread(() -> {
            try {
                rtuSlave.start();
            } catch (ModbusInitException e) {
                e.printStackTrace();
            }
        }).start();

        /*while (true) {
            try {
                synchronized (rtuSlave) {
                    rtuSlave.wait(500);
                }
                for (ProcessImage processImg : rtuSlave.getProcessImages()) {
                    updateProcessImage((BasicProcessImage) processImg);
                }
            } catch (IllegalDataAddressException | InterruptedException e) {
//            } catch ( InterruptedException e) {
                e.printStackTrace();
            }
        }*/

    }

    private static BasicProcessImage generatorProcessImage(int startOffset, int dataSize) {
        BasicProcessImage processImage = new BasicProcessImage(slaveId);
        short[] readDataArray = generatorDCSReadData(dataSize);
        //AO

//        processImage.setNumeric(startOffset, DataType.FOUR_BYTE_INT_SIGNED,65536);
//        processImage.setNumeric(RegisterRange.INPUT_REGISTER,startOffset,DataType.FOUR_BYTE_INT_SIGNED,65537);
//        processImage.setHoldingRegister(startOffset, readDataArray);
        for(int i=0;i<dataSize;i++){
//            processImage.setHoldingRegister(startOffset + i, DCSDataHandle.sendData(1));
//            processImage.setNumeric(startOffset, DataType.FOUR_BYTE_INT_SIGNED,65536);
            processImage.setNumeric(RegisterRange.INPUT_REGISTER,startOffset + i,DataType.FOUR_BYTE_INT_SIGNED,12);
            processImage.setNumeric(RegisterRange.HOLDING_REGISTER,startOffset + i,DataType.FOUR_BYTE_INT_SIGNED,
                    15);
        }
        //AI
//        processImage.setInputRegister(startOffsetForImage,readDataArray);
//        for(int i=0;i<dataSize;i++){
//            processImage.setInputRegister(startOffset + i, DCSDataHandle.sendData(-10));
//        }
        ProcessImageListener processImageListener = generatoerProcessImageListener();
        processImage.addListener(processImageListener);
        return processImage;
    }

    private static short[] generatorDCSReadData(int dataSizeForImage) {
        short[] shortArray = new short[dataSizeForImage];
        for (int i = 0; i < dataSizeForImage; i++) {
            //TODO 所有的数据初始化为55
            shortArray[i] = 5500;
        }
        return shortArray;
    }


    private static ProcessImageListener generatorProcessImageListener(){
        return new ProcessImageListener() {
            @Override
            public void coilWrite(int offset, boolean oldValue, boolean newValue) {

            }

            @Override
            public void holdingRegisterWrite(int offset, short oldValue, short newValue) {
                Float.intBitsToFloat(123);
                Float.floatToIntBits(3f);
                Float.toHexString(3f);
//                Float.floatToRawIntBits()
            }

            @Override
            public void holdingRegisterWrite(int offset, short[] value) {

            }
        };
    }



    private static ProcessImageListener generatoerProcessImageListener() {
        return new ProcessImageListener() {
            @Override
            public void coilWrite(int offset, boolean oldValue, boolean newValue) {
                System.out.println("offset" + offset + ",oldValue:" + oldValue + ",newValue:" + newValue);
            }
            @Override
            public void holdingRegisterWrite(int offset, short oldValue, short newValue) {
                System.out.println("offset" + offset + ",oldValue:" + oldValue + ",newValue:" + newValue);
            }

            @Override
            public void holdingRegisterWrite(int offset, short[] value) {

            }
        };
    }

    /**
     * @return
     * @Description 验证数据，是否存在错误，如果有抛出异常
     * @Author jiangyong xia
     * @Date 10:39 18/9/20
     * @Param
     **/
    static void updateProcessImage(BasicProcessImage processImage) throws IllegalDataAddressException {

//        int hr16Value = processImage.getNumeric(RegisterRange.HOLDING_REGISTER, 16, DataType.TWO_BYTE_INT_UNSIGNED_SWAPPED).intValue();
//        if(hr16Value != twoByteIntUnsignedSwapped){
//            throw new RuntimeException("Test failed on TWO_BYTE_INT_UNSIGNED_SWAPPED. Expected " + twoByteIntUnsignedSwapped + " but was: " + hr16Value);
//        }
        processImage.setHoldingRegister(startOffset,(short)(Math.random() * 100));
        for(int i = 0; i< dataSize; i++){
            processImage.setHoldingRegister(startOffset +i,(short)(Math.random() * 100));
            processImage.setInputRegister(startOffset + i,(short)(Math.random() * 100));
//            processImage.setHoldingRegister(startOffset+i, (short) 0);
//            processImage.setInputRegister(startOffset + i, (short) 0);
        }
//        System.out.println("更新数据" + (i++));
    }
    static int i = 0;

}
