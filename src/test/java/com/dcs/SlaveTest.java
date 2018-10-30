package com.dcs;

import com.gaussic.dataGet.dcsHandle.DCSDataHandle;
import com.gaussic.util.modbus4j.RtuSerialPortWrapper;
import com.serotonin.modbus4j.BasicProcessImage;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusSlaveSet;
import com.serotonin.modbus4j.ProcessImageListener;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;
import com.serotonin.modbus4j.exception.ModbusInitException;

/**
 * @ClassName SlaveTest
 * @Description TODO
 * @Author jiangyong xia
 * @Date 18/10/23 12:32
 * @Version 1.0
 */
public class SlaveTest {


    private static final String commPortId = "COM1";
    private static final int baudRate = 9600,flowControlIn = 0,
            flowControlOut = 0,
            dataBits = 8,
            stopBits = 1,
            parity = 1;


    /**
     * 主站读取与写入的数据，需要全部初始化
     */
    private static final int slaveId = 1,
            startOffsetForImage1 = 0,
            dataSizeForImage1 = 1000,
            startOffsetForImage2 = 299,
            dataSizeForImage2 = 100;


    public static void main(String[] args) {

        RtuSerialPortWrapper wrapper = getWrapper();

        ModbusSlaveSet rtuSlave = new ModbusFactory().createRtuSlave(wrapper);
//        BasicProcessImage processImage1 = generatorProcessImage();
        BasicProcessImage processImage2 = generatorProcessImage(startOffsetForImage1,dataSizeForImage1);

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

    private static BasicProcessImage generatorProcessImage(int startOffsetForImage, int dataSizeForImage) {
        BasicProcessImage processImage = new BasicProcessImage(slaveId);
        short[] readDataArray = generatorDCSReadData(dataSizeForImage);
        //AO

        processImage.setHoldingRegister(startOffsetForImage, readDataArray);
        for(int i=0;i<dataSizeForImage;i++){
            processImage.setHoldingRegister(startOffsetForImage + i, DCSDataHandle.sendData(1));
        }
        //AI
//        processImage.setInputRegister(startOffsetForImage,readDataArray);
        for(int i=0;i<dataSizeForImage;i++){
            processImage.setInputRegister(startOffsetForImage + i, DCSDataHandle.sendData(-10));
        }
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

    private static BasicProcessImage generatorProcessImage() {
        BasicProcessImage processImage = new BasicProcessImage(slaveId);
        short[] readDataArray = generatorDCSReadData();
//        processImage.set

        //AO
        processImage.setHoldingRegister(startOffsetForImage1, readDataArray);
        //AI
        processImage.setInputRegister(startOffsetForImage1,readDataArray);
        ProcessImageListener processImageListener = generatoerProcessImageListener();
        processImage.addListener(processImageListener);
        return processImage;
    }

    private static short[] generatorDCSReadData() {
        short[] shortArray = new short[dataSizeForImage1];
        for (int i = 0; i < dataSizeForImage1; i++) {
            //TODO 所有的数据初始化为55
            if(i>50){
                shortArray[i] = 1;
            }else{
                shortArray[i] = 1;
            }
        }
        return shortArray;
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
        processImage.setHoldingRegister(startOffsetForImage1,(short)(Math.random() * 100));
        for(int i=0;i<dataSizeForImage1;i++){
            processImage.setHoldingRegister(startOffsetForImage1+i,(short)(Math.random() * 100));
            processImage.setInputRegister(startOffsetForImage1 + i,(short)(Math.random() * 100));
//            processImage.setHoldingRegister(startOffsetForImage1+i, (short) 0);
//            processImage.setInputRegister(startOffsetForImage1 + i, (short) 0);
        }
//        System.out.println("更新数据" + (i++));
    }
    static int i = 0;
    protected static RtuSerialPortWrapper getWrapper() {

        return new RtuSerialPortWrapper(commPortId, baudRate, flowControlIn, flowControlOut, dataBits, stopBits, parity);
    }
}
